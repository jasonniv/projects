import { createContext, useState } from "react";
import { Button, Cell, Colors, Grid, GridContainer, InputTypes, Label } from "react-foundation";
import { Link, useHistory } from "react-router-dom";
import ErrorMessages from "../ErrorMessages/ErrorMessages";
import './CreateAccount.css';


function CreateAccount() {
    const history = useHistory();
    const [account, setAccount] = useState({});
    const [errors, setErrors] = useState([]);

    //add second fetch request to seperate user info and login info

    function handleSubmit(event) {
        event.preventDefault();

        fetch("http://localhost:8080/auth/create", {
            method: "POST",
            body: JSON.stringify(account)
            ,
            headers: {
                "Content-Type": "application/json"
            }
        }).then(async response => {
            if (response.status === 201) {

                history.push("/");
                console.log(account);


            } else {
                setErrors(await response.json());
                console.log(errors);
                //Display error messages
                console.log(account);
            }
        });
    }

    function inputChangeHandler(inputChangedEvent) {


        const propertyName = inputChangedEvent.target.name;
        const newValue = inputChangedEvent.target.value;

        const accountCopy = { ...account };


        accountCopy[propertyName] = newValue;


        setAccount(accountCopy);
    }



    return (
        <div>
           
            <h4 className="header">Create Account</h4>
            
            <GridContainer className="form-group">
                <form onSubmit={handleSubmit}>
                    <Grid >
                        <Cell medium={6}>
                            <label> Username
                                <input type="text" placeholder="Username" name="username" onChange={inputChangeHandler} />
                            </label>
                            <label> Password
                                <input type="password" placeholder="Password" name="password" onChange={inputChangeHandler} />
                            </label>
                            <label> Confirm Password
                                <input type="password" placeholder="Confirm Password" name="confirmPassword" onChange={inputChangeHandler} />
                            </label>
                        </Cell>
                    </Grid>
                    <div>
                        <Button className="button">Submit</Button>
                        <Link to="/">
                            <Button color={Colors.ALERT} className="button">Cancel</Button>
                        </Link>
                        <Link to="/login"> Already Have an account? Click here to login</Link>
                    </div>
                </form>
            </GridContainer>
         {errors.length > 0 ? <ErrorMessages id="createAccountErrors" errorList={errors}/> : null }
            
        </div>
        
    );
}

export default CreateAccount;
