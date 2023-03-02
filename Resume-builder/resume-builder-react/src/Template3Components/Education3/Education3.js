function Education3(props) {

    return (
    <div>
         <h3 className="edu-header">{props.educationData.schoolName}</h3>
         <h5>{props.educationData.educationLevel}</h5>
    </div>
        );
    }
    
    export default Education3;