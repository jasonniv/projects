import { motion } from "framer-motion";
import { useContext, useState } from "react";
import { Button, Colors } from "react-foundation";
import { Link, useHistory } from "react-router-dom";
import AuthContext from "../AuthContext";
import './Resume.css'

function Resume(props) {

  const [isOpen, setIsOpen] = useState(false);
  const history = useHistory();
  const [template, setTemplate] = useState("");
  const userData = useContext(AuthContext);
  
  
  function chooseTemplate(){
  switch(props.templateId){
    case 1: {
      setTemplate("Basic Resume Template");
      break;
    }
    case 2: {
      setTemplate("Fancy Resume Template");
      break;
    }
    case 3: {
      setTemplate("Professional Resume Template");
      break;
    }
  }
}

  function deleteClicked() {
    if (
      window.confirm(
        "Are you sure you want to delete resume " + props.resumeId + "?"
      )
    ) {
      const userId = userData.claims.jti;
      const jwt = userData.jwt;
      fetch("http://localhost:8080/api/resume/" + props.resumeId, {
        method: "DELETE",
      headers: {
        Authorization: `Bearer ${jwt}`,
      },
      }).then(async (response) => {
        if (response.status === 204) {
          // props.onResumeDeleted();
        } else {
          console.log(props.resumeId)
          console.log(await response.json());
        }
      });
    }
  }

  const handleClick = event => {
    console.log(event.detail);
    chooseTemplate();
    switch (event.detail) {
      case 1: {
        console.log('single click');
        setIsOpen(!isOpen)
        break;
      }
      case 2: {
        console.log('double click');
        history.push("/api/resume/" + props.resumeId); //placeholder
        break;
      }
      default: {
        break;
      }
    }
  };

  return (
    <div>
        <motion.div onClick={handleClick} className="card" style={{borderRadius: '3rem'}}>
          <motion.h2>Resume:  {props.resumeName}</motion.h2>
          {isOpen &&
          <motion.div>
            <p>{template}</p>
            <div className="buttonGroup">
            <Link  to={"/edit/" + props.resumeId} className="edit" color={Colors.PRIMARY}>Edit</Link> 
            <Button onClick={deleteClicked} color={Colors.ALERT}>Delete</Button> 
            </div>
          </motion.div>
          }
  
        </motion.div>
      </div>
  )

}

export default Resume;
