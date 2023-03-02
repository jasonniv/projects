function WorkHistory({company, jobTitle, startDate, endDate, jobDescription}){

    return(
        <div className="workHistory">
            <h6 className="workHistoryCompany">{company}</h6>
            <h6 className="workHistoryJobTitle">{jobTitle}</h6>
            <h6 className="workHistoryStartDate">{startDate} —</h6>
            <h6 className="workHistoryendDate">{endDate}</h6>
            <h6 className="workHistoryJobDescription">{jobDescription}</h6>
        </div>
    )

}
export default WorkHistory;