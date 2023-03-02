

function Template5({ educations, skills, workHistories, userInfo }) {

    return (
        <div className="resumePage">
            <div className="resume row">
                <div className="large-4 columns">
                    <div className="resume-wrapper">
                        <h2><strong>{userInfo.firstName} {userInfo.lastName}</strong></h2>
                        {/* SKils */}
                        <div className="large-4 columns">
                            <div className="resume-wrapper">
                                <h3 className="resume-header">Skills</h3>
                                <ul>
                                    {skills.map(s =>
                                        <li>{s.skillName}</li>
                                    )}
                                </ul>
                                <h3>Education</h3>
                                {educations.map(e =>
                                    <div className="resume-spacing">
                                        <h5><strong>{e.schoolName}</strong></h5>
                                        <h6><em>{e.educationLevel}</em></h6>                            </div>
                                )}
                                <h3>Experience</h3>
                                {workHistories.map(w =>
                                    <div className="resume-spacing">
                                        <h5><strong>{w.jobTitle}</strong></h5>
                                        <h6><em>{w.company}</em></h6>
                                        <p>{w.jobDescription}</p>
                                    </div>
                                )}
                                <h3>About Me</h3>
                                <div className="resume-user-info">
                                    <i className="fa fa-envelope" aria-hidden="true"></i>{userInfo.email}
                                </div>
                                <div className="resume-user-info">
                                    <i className="fa fa-solid fa-location-pin" aria-hidden="true"></i>{userInfo.address}
                                </div>
                                <div className="resume-user-info">
                                    <i className="fa fa-phone" aria-hidden="true"></i>{userInfo.phoneNumber}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Template5;
