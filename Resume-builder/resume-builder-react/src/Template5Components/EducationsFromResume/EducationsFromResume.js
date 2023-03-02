function EducationsFromResume(props){

    return (
        <tr>
           <td>{props.educationData.schoolName}</td>
           <td>{props.educationData.educationLevel}</td>
        </tr>
    );
}

export default EducationsFromResume;