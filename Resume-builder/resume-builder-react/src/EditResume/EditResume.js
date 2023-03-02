import { useContext, useState, useEffect } from "react";
import { Button } from "react-foundation";
import { useHistory, useParams } from "react-router-dom";
import AddEducationForm from "../AddEducationForm/AddEducationForm";
import AuthContext from "../AuthContext";
import "./EditResume.css";
import AddWorkHistoryForm from "../AddWorkHistoryForm/AddWorkHistoryForm";
import AddAppUserInfoForm from "../AddAppUserInfoForm/AddAppUserInfoForm";
import ErrorMessages from "../ErrorMessages/ErrorMessages.js";

function EditResume(props) {
  const [addedEducation, setAddedEducation] = useState([]);
  const [addedWorkHistory, setAddedWorkHistory] = useState([]);
  const [addedAppUserInfo, setAddedAppUserInfo] = useState([]);
  const [resumeName, setResumeName] = useState("");
  const [template, setTemplate] = useState(0);
  const [resumeId, setResumeId] = useState(null);
  const [token, setToken] = useState(null);
  const [errors, setErrors] = useState([]);
  const [addedSkills, setAddedSkills] = useState([]);
  const [skillsList, setSkills] = useState([]);
  const [isEmpty, setIsEmpty] = useState(false);
  const { id } = useParams();

  function insertEducationForm() {
    let newfield = { schoolName: "", educationLevel: "" };

    setAddedEducation([...addedEducation, newfield]);
  }
  function educationUpdateHandler(education, index) {
    const copy = [...addedEducation];

    copy[index] = education;
    setAddedEducation(copy);
  }
  function AddWorkForm() {
    let newfield = {
      jobTitle: "",
      startDate: "",
      endDate: "",
      jobDescription: "",
    };

    setAddedWorkHistory([...addedWorkHistory, newfield]);
  }
  function workHistoryUpdateHandler(workHistory, index) {
    const copy = [...addedWorkHistory];

    copy[index] = workHistory;
    setAddedWorkHistory(copy);
  }
  function appUserInfoUpdateHandler(newValue, propertyName) {
    const copy = { ...addedAppUserInfo };

    copy[propertyName] = newValue;
    setAddedAppUserInfo(copy);
  }
  function resumeNameUpdate(evt) {
    setResumeName(evt.target.value);
  }
  function templateUpdateHandler(evt) {
    console.log(evt.target.value);
    setTemplate(evt.target.value);
  }
  function deleteEducation(index) {
    const newList = addedEducation.filter((s) => s !== addedEducation[index]);
    setAddedEducation(newList);
  }
  function deleteWorkHistory(index) {
    const newList = addedWorkHistory.filter(
      (s) => s !== addedWorkHistory[index]
    );
    setAddedWorkHistory(newList);
  }
  function handleClick(event) {
    const descriptionText = document.getElementById("jobDescription");

    const description = {
      text: descriptionText.value,
      confidenceThreshold: 0.6,
    };
    skillsChecker(description);
  }
  useEffect(() => {
    getToken();

    if (userData === null) {
      history.push("/login");
    } else {
      const userId = userData.claims.jti;
      const jwt = userData.jwt;
      console.log(id);
      fetch("http://localhost:8080/api/resume/" + id, {
        headers: {
          Authorization: `Bearer ${jwt}`,
        },
      })
        .then(async (response) => {
          if (response.status === 200) {
            return response.json();
          } else if (response.status === 400) {
            setIsEmpty(true);
            return response.json();
          } else console.log(await response.json());
        })
        .then((resumeInfo) => {
         console.log(resumeInfo);
          setAddedEducation(resumeInfo.educations);
          setAddedWorkHistory(resumeInfo.workHistories);
          setAddedSkills(resumeInfo.skills);
          setSkills(resumeInfo.skills.map((s) => s.skillName));
          setAddedAppUserInfo(resumeInfo.userInfo);
          setResumeId(resumeInfo.resumeId);
          setTemplate(resumeInfo.templateId);
          setResumeName(resumeInfo.resumeName)
        });
    }
  }, [id]);

  function getToken() {
    const data = {
      client_id: "dhyve40aqrmz8hqh",
      client_secret: "3cadGUb6",
      grant_type: "client_credentials",
      scope: "emsi_open",
    };
    var formBody = [];
    for (var property in data) {
      var encodedKey = encodeURIComponent(property);
      var encodedValue = encodeURIComponent(data[property]);
      formBody.push(encodedKey + "=" + encodedValue);
    }
    formBody = formBody.join("&");

    fetch("https://auth.emsicloud.com/connect/token", {
      method: "POST",
      body: formBody,
      headers: {
        "Content-type": "application/x-www-form-urlencoded",
      },
    })
      .then(async (response) => {
        if (response.status === 200) {
          console.log("pass");
          return response.json();
        } else if (response.status === 400) {
          console.log(await response.json());
        } else console.log(await response.json());
      })
      .then(async (tokenObj) => {
        setToken(await tokenObj);
        console.log(token);
      });
  }
  function skillsChecker(description) {
    // setAddedSkills([])

    fetch(
      "https://emsiservices.com/skills/versions/latest/extract?language=en",
      {
        method: "POST",
        body: JSON.stringify(description),
        headers: {
          Authorization: `Bearer ${token.access_token}`,
          "Content-Type": "application/json",
        },
      }
    )
      .then(async (response) => {
        if (response.status === 200) {
          console.log("Success");
          return await response.json();
        } else if (response.status === 400) {
          setErrors(await response.json());
        } else setErrors(await response.json());
      })
      .then((skillList) => {
        const tempList = skillList.data
          .map((s) => s.skill.name)
          .filter((s) => !skillsList.includes(s));
        console.log(tempList);
        setSkills([...skillsList, ...tempList]);
        // console.log(skillList);
      });
  }
  function addSkillClick(evt) {
    const btn = document.getElementById(evt.target.value);

    console.log(btn.style.backgroundColor);
    if (btn.style.backgroundColor != "green") {
      btn.style.backgroundColor = "green";
      const newSkillsList = addedSkills.concat({ skillName: evt.target.value });
      setAddedSkills(newSkillsList);
    } else {
      btn.style.backgroundColor = "";
      const newSkillsList = addedSkills.filter(
        (s) => s.skillName != evt.target.value
      );
      setAddedSkills(newSkillsList);
    }
  }

  const userData = useContext(AuthContext);
  const history = useHistory();

  function onSubmit(event) {
    event.preventDefault();

    const resume = {
      workHistories: addedWorkHistory,
      educations: addedEducation,
      skills: addedSkills,
      userInfo: addedAppUserInfo,
      templateId: template,
      resumeId: resumeId,
      resumeName: resumeName
    };
    const userId = userData.claims.jti;
    const jwt = userData.jwt;

    fetch("http://localhost:8080/api/resume/" + id, {
      method: "PUT",
      body: JSON.stringify(resume),
      headers: {
        Authorization: `Bearer ${jwt}`,
        "Content-Type": "application/json",
      },
    })
      .then(async (response) => {
        if (response.status === 204) {
          history.push("/api/resume/" + id);
        } else if (response.status === 409) {
          return Promise.reject(["Id mismatch between url and sent resume ☹"]);
        } else if (response.status === 404) {
          return Promise.reject(["Resume not found ☹"]);
        } else if (response.status === 400) {
          return Promise.reject(await response.json());
        }
      })
      .catch((errorList) => {
        if (errorList instanceof TypeError) {
          setErrors(["Could not connect to api ☹"]);
        } else {
          setErrors(errorList);
        }
      });
  }

  
  
  return (
    <div className="container">
      <div className="editForm">
        <nav aria-label="You are here:" role="navigation">
          <ul className="breadcrumbs">
            <li>
              <a href="#Education">Education</a>
            </li>
            <li>
              <a href="#WorkHistory">Work history</a>
            </li>
            <li>
              <a href="#AppUserInfo">User Info</a>
            </li>
            <li>
              <a href="">Template</a>
            </li>
          </ul>
        </nav>
        <div id="Education">
          <h2>Education</h2>
          <Button onClick={insertEducationForm}>Add Education</Button>
          {addedEducation.map((input, index) => (
            <AddEducationForm
              education={input}
              index={index}
              onEducationUpdated={educationUpdateHandler}
              onDelete={deleteEducation}
            />
          ))}
        </div>
        <div id="WorkHistory">
          <h2>Work History</h2>
          <Button onClick={AddWorkForm}>Add Work History</Button>
          {addedWorkHistory.map((input, index) => (
            <AddWorkHistoryForm
              workHistory={input}
              index={index}
              onWorkHistoryUpdated={workHistoryUpdateHandler}
              skillsChecker={skillsChecker}
              onDelete={deleteWorkHistory}
            />
          ))}
          {skillsList.map((s) => (
            <Button className="pill" id={s} value={s} onClick={addSkillClick}>
              {" "}
              {s}
            </Button>
          ))}
        </div>
        <div id="AppUserInfo">
          <h2>User Info</h2>
          <AddAppUserInfoForm
            appUserInfo={addedAppUserInfo}
            onAppUserInfoUpdated={appUserInfoUpdateHandler}
          />
        </div>
        <div>
            <legend>Resume Name</legend>
            <input type='text' name="resumeName" value={resumeName} onChange={resumeNameUpdate}/>
        </div>
        <div>
          <fieldset onChange={templateUpdateHandler}>
            <legend>Choose a template</legend>
            <input
              type="radio"
              name="template"
              value={1}
              id="template1"
              required
            />
            <label for="template1">Professional-Light</label>
            <input
              type="radio"
              name="template"
              value={2}
              id="template2"
              required
            />
            <label for="template2">Professional-Dark</label>
            <input
              type="radio"
              name="template"
              value={3}
              id="template3"
              required
            />
            <label for="template3">Fun Icon Resume</label>
            <input
              type="radio"
              name="template"
              value={4}
              id="template4"
              required
            />
            <label for="template4">Basic Table</label>
            <input
              type="radio"
              name="template"
              value={5}
              id="template5"
              required
            />
            <label for="template5">Goofy Resume-Pink</label>
          </fieldset>
        </div>
        <Button onClick={onSubmit}>Submit</Button>
        
      </div>
      {errors.length > 0 ? <ErrorMessages errorList={errors} /> : null}
    </div>
  );
}

export default EditResume;
