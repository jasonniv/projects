function WorkHistoryTemplateTwo({company, jobTitle, startDate, endDate, jobDescription}){

    return(
        <div className="workHistoryTwo">
            <h6 className="workHistoryCompanyTwo">{company}</h6>
            <h6 className="workHistoryJobTitleTwo">{jobTitle}</h6>
            <h6 className="workHistoryStartDateTwo">{startDate} —</h6>
            <h6 className="workHistoryendDateTwo">{endDate}</h6>
            <h6 className="workHistoryJobDescriptionTwo">{jobDescription}</h6>
        </div>
    )

}
export default WorkHistoryTemplateTwo;