import "./WorkHistory3.css";

function WorkHistory3({ workHistories, skills }) {

    return (
        <div>
            {workHistories.map(w =>
                <div>
                    <div>From: {w.startDate} To:{w.endDate}</div>
                    <div>{w.jobTitle}</div>
                    <div>
                        {w.jobDescription}
                        <div>Skills</div>
                        <ul>
                            {skills.map(s =>
                                <li>{s.skillName}</li>
                            )}
                        </ul>
                    </div>
                </div>
            )}
        </div>
    );
}

export default WorkHistory3;