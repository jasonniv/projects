import AuthContext from "../AuthContext";
import {useContext, useState, useEffect} from "react";
import { useHistory } from "react-router-dom";
import Widget from "../Widget/Widget";

function PersonalWidgetDashboard(){

    const [personalWidgets, setPersonalWidgets] = useState([]);

    const userData = useContext(AuthContext);
    const history = useHistory();

    //if the user is not logged in, don't try to display anything
    //instead route them to the homepage
    useEffect( () => {
        if( userData === null ){
            history.push( "/login" );
        } else {
            const userId = userData.claims.jti;
            const jwt = userData.jwt;

            fetch( "http://localhost:8080/api/widget/personal/" + userId,
            {
                headers: {
                    Authorization: `Bearer ${jwt}`
                }
            })
            .then( response => {
                if( response.status === 200 ){
                    return response.json();
                } else if( response.status === 403 ) {
                    //attempted to look at another user's widgets...
                    //TODO: create Error component
                    console.log( "Cannot read another user's personal widgets");
                } else {
                    //something else bad happened...
                    console.log( response );
                }
            })
            .then( personalWidgetList => {

                setPersonalWidgets( personalWidgetList );
            });
            


         }
        }, []);

    return (
        <div className="container">
            {
                personalWidgets.map(w => <Widget widgetData={w} />)
            }
        </div>
        
    );

}

export default PersonalWidgetDashboard;