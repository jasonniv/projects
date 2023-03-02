import "./App.css";
import { BrowserRouter, Redirect, Route, Switch } from "react-router-dom";
import NavigationBar from "./NavigationBar/NavigationBar";
import Homepage from "./Homepage/Homepage";
import CreateAccount from "./CreateAccount/CreateAccount";
import Login from "./Login/Login";
import { useEffect, useState } from "react";
import AuthContext from "./AuthContext";
import ViewResume from "./ViewResume/ViewResume";
import jwtDecode from "jwt-decode";
import NotFound from "./NotFound/NotFound";
import AddResume from "./AddResume/AddResume";
import ViewFullResume from "./ViewFullResume/ViewFullResume";
import EditResume from "./EditResume/EditResume";

const LOCAL_STORAGE_TOKEN_KEY = "resumeToken";

function App() {
  const [loginInfo, setLoginInfo] = useState(null);

  const [restoreLoginAttemptCompleted, setRestoreLoginAttemptCompleted] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem(LOCAL_STORAGE_TOKEN_KEY);
    if (token) {
      login(token);
    }
    setRestoreLoginAttemptCompleted(true);
  }, []);

  const login = (token) => {
    localStorage.setItem(LOCAL_STORAGE_TOKEN_KEY, token);

    // Decode the token
    const { sub: username, authorities: authoritiesString } = jwtDecode(token);
  
    // Split the authorities string into an array of roles
    const roles = authoritiesString.split(',');
  
    // Create the "user" object
    const loginInfo = {
      username,
      roles,
      token,
      hasRole(role) {
        return this.roles.includes(role);
      }
    };
  
    console.log(loginInfo);  
    setLoginInfo(loginInfo);  
    return loginInfo;
  };
  
  const logout = () => {
    setLoginInfo(null);
    localStorage.removeItem(LOCAL_STORAGE_TOKEN_KEY);
  };

  const auth = {
    loginInfo: loginInfo ? { ...loginInfo } : null,
    login,
    logout
  };


  if (!restoreLoginAttemptCompleted) {
    return null;
  }

  return (
    <div className="App">
      <AuthContext.Provider value={loginInfo}>
        <BrowserRouter>
          <NavigationBar setLoginInfo={setLoginInfo} />
          <Switch>
            <Route exact path="/">
              <Homepage />
            </Route>
            <Route path="/create_account">
              <CreateAccount />
            </Route>
            <Route exact path="/login">
              {!loginInfo ? <Login setLoginInfo={setLoginInfo} /> : <Redirect to="viewResume"/>}
            </Route>
            <Route exact path="/api/resume">
              {loginInfo ? <ViewResume/> : <Redirect to="/login"/>}
            </Route>
            <Route exact path="/api/addResume">
              {loginInfo ? <AddResume/> : <Redirect to="/login"/>}
            </Route>
            <Route exact path="/edit/:id">
              {loginInfo ? <EditResume/> : <Redirect to="/login"/>}
            </Route>
            <Route exact path="/api/resume/:id">
              {loginInfo ? <ViewFullResume/> : <Redirect to="/api/resume/1"/>}
            </Route>
            <Route >
              <NotFound />
            </Route>
          </Switch>
        </BrowserRouter>
      </AuthContext.Provider>
    </div>
  );
}

export default App;
