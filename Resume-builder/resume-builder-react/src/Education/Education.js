function Education({schoolName, educationLevel}){

    return(
           <div className="education">
                <h5 className="schoolName">{schoolName}</h5>
                <h5 className="educationLevel">{educationLevel}</h5>
           </div> 
    );

}
export default Education;