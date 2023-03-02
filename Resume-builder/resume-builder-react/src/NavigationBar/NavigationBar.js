
import { useContext } from "react";
import {
  TopBar,
  Menu,
  TopBarLeft,
  TopBarRight,
  MenuItem,
  Button,
} from "react-foundation";
import { Link, useHistory } from "react-router-dom";
import AuthContext from "../AuthContext";
import "./NavigationBar.css";

function NavigationBar(props) {
  const loginInfo = useContext(AuthContext);
  const history = useHistory();

  function logoutHandler(){
        props.setLoginInfo(null);
        history.push( "/" );
      }

  return (
    <TopBar>
      <TopBarLeft className="my-top-bar-right">
        <Menu>
          {/* make it have state so it can say log in or log out maybe*/}
          <Link to="/" > Home </Link>

           {loginInfo ? 
          <MenuItem >
            <Link to="/api/resume">View Resume</Link>
          </MenuItem>
          : null
          }


        </Menu>
      </TopBarLeft>

      <TopBarRight className="my-top-bar-right">
      
        <Menu>
        {loginInfo ? <Button className="login" onClick={logoutHandler} >Log Out {loginInfo.claims.sub}</Button> :
                    <Link  className="login" to="/login" >Log In</Link>}
          
          <MenuItem>
            <a className="links" href="https://www.linkedin.com/company/genesis10/" >LinkedIn</a>
          </MenuItem>
          <MenuItem>
            <a className="links" href="https://www.facebook.com/Genesis10Page">FB</a>
          </MenuItem>
          <MenuItem>
            <a className="links" href="https://twitter.com/Genesis10Corp">Twitter</a>
          </MenuItem>
         
        </Menu>
      </TopBarRight>
    </TopBar>
  );
}

export default NavigationBar;
