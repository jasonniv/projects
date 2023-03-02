import { Grid, GridContainer } from "react-foundation";
import Education3 from "../Template3Components/Education3/Education3";
import WorkHistory3 from "../Template3Components/WorkHistory3/WorkHistory3";
import "./template3.css";

function Template3({ educations, skills, workHistories, userInfo }) {

    return (
        <div className="resumePage">
            <header className="header">
                <div id="photo">
                    <img src="https://www.lafd.org/profiles/lafd/themes/lafd_org/logo.png" alt="lafd" />
                </div>
                <h1>{userInfo.firstName} {userInfo.lastName}</h1>
            </header>
                    <hr />
                    <hr />
            <main>
                <article id="mainLeft">
                    <section className="p">
                        <h2><i className="fa-regular fa-address-book"></i>CONTACT</h2>
                        <div className="move-right">
                            <i className="fa fa-envelope"></i>
                            <b>{userInfo.email}</b>
                        </div>
                        <div>
                            <i className="fa-solid fa-phone"></i>
                            <b>{userInfo.phoneNumber}</b>
                        </div>
                        <div>
                            <i className="fa-solid fa-location-pin"></i>
                            <b> {userInfo.address}</b>
                        </div>
                    </section>
                    <section>
                        <h2><i className="fa-solid fa-graduation-cap"></i>EDUCATION</h2>
                        {educations.map(e =>
                            <div>
                                <h4>{e.schoolName}</h4>
                                <h5>{e.educationLevel}</h5>
                            </div>
                        )}
                    </section>
                </article>
                <article id="mainRight">
                    <section>
                        <h2><i className="fa-solid fa-briefcase"></i>WORK EXPERIENCE</h2>
                        <div>
                            {workHistories.map(w =>
                                <div>
                                    <h3>{w.company}</h3>
                                    <div>From:  {w.startDate}</div> <div>To: {w.endDate}</div>
                                    <b>{w.jobTitle}</b>
                                    <div>
                                        {w.jobDescription}
                                        <div>Skills:</div>
                                        <ul>
                                            {skills.map(s =>
                                                <li>{s.skillName}</li>
                                            )}
                                        </ul>
                                    </div>
                                </div>
                            )}
                        </div>
                    </section>
                </article>
            </main>
        </div>
    );
}

export default Template3;