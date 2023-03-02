import UserInfoFromResume from "../Template5Components/UserInfoFromResume/UserInfoFromResume";
import WorkHistoriesFromResume from "../Template5Components/WorkHistoriesFromResume/WorkHistoriesFromResume";
import EducationsFromResume from "../Template5Components/EducationsFromResume/EducationsFromResume";
import SkillsFromResume from "../Template5Components/SkillsFromResume/SkillsFromResume";
import "./Template5.css";


function Template4({ educations, skills, workHistories, userInfo }) {
    return (
        <div>
            <h3>Basic Resume Information Table</h3>
            <table>
                <thead>
                    <tr>
                        <th>FirstName</th>
                        <th>Last Name</th>
                        <th>Email</th>
                        <th>Address</th>
                        <th>Phone Number</th>
                    </tr>
                </thead>
                <tbody>
                    <UserInfoFromResume key={userInfo.infoId} infoData={userInfo} />
                </tbody>
            </table>
            <table>
                <thead>
                    <tr>
                        <th>School Name</th>
                        <th>Education Level</th>
                    </tr>
                </thead>
                <tbody>
                    {educations.map(e => <EducationsFromResume key={e.educationId} educationData={e} />)}
                </tbody>
            </table>
            <table>
                <thead>
                    <tr>
                        <th>Company</th>
                        <th>Job Title</th>
                        <th>Start Date</th>
                        <th>End Date</th>
                        <th>Job Description</th>
                    </tr>
                </thead>
                <tbody>
                    {workHistories.map(w => <WorkHistoriesFromResume key={w.workHistoryId} workHistoriesData={w} />)}
                </tbody>
            </table>
            <table>
                <thead>
                    <tr>
                        <th>Skill Name</th>
                    </tr>
                </thead>
                <tbody>
                    {skills.map(s => <SkillsFromResume key={s.skillId} skillData={s} />)}
                </tbody>
            </table>
        </div>
    );
}

export default Template4;