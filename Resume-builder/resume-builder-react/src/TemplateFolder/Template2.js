import { Cell, Grid, GridContainer } from "react-foundation";
import EducationTemplateTwo from "../Education/EducationTemplateTwo";
import SkillTemplateTwo from "../Skill/SkillTemplateTwo";
import WorkHistoryTemplateTwo from "../WorkHistory/WorkHistoryTemplatTwo";
import "./template2.css";

function Template2({educations,skills, workHistories, userInfo }){

    
    console.log(educations)
    console.log(skills)
    console.log(workHistories)
    console.log(userInfo);


    return(
<GridContainer>

            
<Grid  className="template2">

    <div className="page2">
        <h1 className="nameHeader2">{userInfo.firstName} {userInfo.lastName}</h1>
        <ul id="userInformation2">{userInfo.email}</ul>
        <ul id="userInformationMargin2">{userInfo.address}</ul>
        <ul id="userInformationMargin2">{userInfo.phoneNumber}</ul>
        <ul id="educationHeader2">Education</ul>
        <ul id="educationInfo2">{educations.map(e =>
            <EducationTemplateTwo
                schoolName={e.schoolName}
                educationLevel={e.educationLevel}
            />
        )}
        </ul>
        <hr></hr>

        <h5 id="workHistoryHeader2">Work History</h5>
        <ul id="workHistoryInfo2">{workHistories.map(w =>
            <WorkHistoryTemplateTwo
                company={w.company}
                jobTitle={w.jobTitle}
                startDate={w.startDate}
                endDate={w.endDate}
                jobDescription={w.jobDescription}
            />
        )}
        </ul>

        <hr></hr>

        <h5 id="skillHeader2">Skill:</h5>
        <ul>{skills.map(s =>
            <SkillTemplateTwo
                skillName={s.skillName}
            />
        )}</ul>
    </div>

</Grid>
</GridContainer>
    );
}

export default Template2;