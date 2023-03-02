import { Link } from "react-router-dom";
import { useContext } from "react";
import './Homepage.css';
import {
  Button,
  Grid,
  GridContainer,
  MediaObject,
  MediaObjectSection,
  Thumbnail,
  Sizes
} from "react-foundation";
import AuthContext from "../AuthContext";

function Homepage() {
  const loginInfo = useContext(AuthContext);
  
    return (
      <div  className="homepage">
        <div className='createAccount'>
          <GridContainer>
            {!loginInfo ? 
            <Link className="login" size={Sizes.LARGE} to="/create_account" >Create Account </Link>
              :
              <Link className="login" size={Sizes.LARGE} to="api/addResume" >Add Resume </Link>
              }
          </GridContainer>

        </div>
      </div>
    );
  
}

export default Homepage;
