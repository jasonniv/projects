import { Link, useHistory } from 'react-router-dom';
import jwtDecode from 'jwt-decode';
import { Button, Colors } from 'react-foundation';
import { useState } from 'react';
import ErrorMessages from '../ErrorMessages/ErrorMessages';
import "./Login.css";

function Login(props) {

    const history = useHistory();
    const [errors, setErrors] = useState([]);

    function loginHandler(e) {

        e.preventDefault();

        //TODO: make these active inputs that update an object
        const username = document.getElementById("username").value;
        const password = document.getElementById("password").value;
        const loginRequest = { username, password };

        fetch("http://localhost:8080/auth/authenticate", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(loginRequest)
        })
            .then(async response => {
                if (response.status === 200) {
                    return response.json();
                } else if (response.status === 403) {
                    setErrors(["Username/Password incorrect"]);
                } else {

                    console.log("fail")
                    setErrors(await response.json());
                }
            })
            .then(jwtContainer => {


                const jwt = jwtContainer.jwt_token;
                const claimsObject = jwtDecode(jwt);

                console.log(jwt);
                console.log(claimsObject);

                props.setLoginInfo({ jwt, claims: claimsObject });
                history.push("/");

            })
            .catch(error => {
                console.log(error);
            });
    }

    return (

        <div className='body'>
            
            <div className="container">

                <form onSubmit={loginHandler} className="form-group">

                    <div >
                        <label htmlFor="username">User Name</label>
                        <input id="username" name="username" type="text" className="form-control" />
                    </div>

                    <div >
                        <label htmlFor="password">Password</label>
                        <input id="password" name="password" type="password" className="form-control" />
                    </div>

                    <div >
                        <Button className='login'>Log In</Button>
                        <Link to="/">
                            <Button className='login' color={Colors.ALERT}>Cancel</Button>
                        </Link>
                    </div>

                    <Link to="/create_account"> Create an account</Link>

                </form>
{errors.length > 0 ? <ErrorMessages  errorList={errors}/> : null }
            </div>
           
        </div>
    );
}

export default Login;