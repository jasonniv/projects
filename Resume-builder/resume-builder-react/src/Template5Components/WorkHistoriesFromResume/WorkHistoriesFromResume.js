function WorkHistoriesFromResume(props){

    return (
        <tr>
           <td>{props.workHistoriesData.company}</td>
           <td>{props.workHistoriesData.jobTitle}</td>
           <td>{props.workHistoriesData.startDate}</td>
           <td>{props.workHistoriesData.endDate}</td>
           <td>{props.workHistoriesData.jobDescription}</td>
        </tr>
    );
}

export default WorkHistoriesFromResume;