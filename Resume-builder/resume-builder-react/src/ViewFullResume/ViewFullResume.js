import { useContext, useState, useEffect } from "react";
import { Button } from "react-foundation";
import { Link, useHistory, useParams } from "react-router-dom";
import AuthContext from "../AuthContext";
import Template1 from "../TemplateFolder/Template1";
import Template2 from "../TemplateFolder/Template2";
import Template3 from "../TemplateFolder/Template3";
import Template5 from "../TemplateFolder/Template5";
import Template4 from "../TemplateFolder/Template4";
import "./ViewFullResume.css";
import GenericPdfDownloader from "../Download/Download";


function ViewFullResume(props) {

    const [resume, setResume] = useState({});
    const userData = useContext(AuthContext);
    const history = useHistory();
    const [isEmpty, setIsEmpty] = useState(false);
    const [educations, setEducations] = useState([])
    const [userInfo, setUserInfo] = useState([])
    const [skills, setSkills] = useState([])
    const [workHistories, setWorkHistories] = useState([])
    const { id } = useParams();

    useEffect(() => {

        if (userData === null) {
            history.push("/login");
        } else {
            const userId = userData.claims.jti;
            const jwt = userData.jwt;
            fetch("http://localhost:8080/api/resume/" + id,
                //props.id
                {
                    headers: {
                        Authorization: `Bearer ${jwt}`
                    }
                })
                .then(async response => {
                    if (response.status === 200) {
                        return response.json();
                    } else if (response.status === 400) {
                        setIsEmpty(true);
                        return response.json();

                    } else (console.log(await response.json()))
                })
                .then(resumeInfo => {
                    setResume(resumeInfo);
                    setEducations(resumeInfo.educations)
                    setUserInfo(resumeInfo.userInfo)
                    setSkills(resumeInfo.skills)
                    setWorkHistories(resumeInfo.workHistories)
                });
        }
    }, [id]);

    function loadResume() {
        switch (resume.templateId) {
            case 1:
                return (
                    <Template1
                        key={resume.resumeId}
                        educations={educations}
                        skills={skills}
                        workHistories={workHistories}
                        userInfo={userInfo} />
                )
            case 2:
                return (
                    <Template2
                        key={resume.resumeId}
                        educations={educations}
                        skills={skills}
                        workHistories={workHistories}
                        userInfo={userInfo} />
                )
            case 3:
                return (
                    <Template3
                        key={resume.resumeId}
                        educations={educations}
                        skills={skills}
                        workHistories={workHistories}
                        userInfo={userInfo} />
                )
            case 4:
                return (
                    <Template4
                        key={resume.resumeId}
                        educations={educations}
                        skills={skills}
                        workHistories={workHistories}
                        userInfo={userInfo} />
                )
            case 5:
                return (
                    <Template5
                        key={resume.resumeId}
                        educations={educations}
                        skills={skills}
                        workHistories={workHistories}
                        userInfo={userInfo} />
                )
            default:
                break;
        }
    }


    return (

        <div className="page">
            <GenericPdfDownloader
            downloadFileName="resume.pdf" 
            rootElementId="Resume" 
            />
        
            {!isEmpty ?
                <div id="Resume">
                    {loadResume()}
                </div>
                : <div className="container"> No Resume Found</div>}


        </div>


    );
}

export default ViewFullResume;