import { Button, Colors } from "react-foundation";
import FormInput from "../FormInput/FormInput";

function AddWorkHistoryForm({workHistory, onWorkHistoryUpdated, index, skillsChecker, onDelete}){
    function updateWorkHistory(evt){
        const target = evt.target;
        const name = target.name;
        const value = target.value;

        const copy = {...workHistory};
        copy[name] = value;

        onWorkHistoryUpdated(copy,index);
    }

    function handleClick() {
      const descriptionText = document.getElementById("jobDescription"+index);
      
      
      const description = { text: descriptionText.value, confidenceThreshold: 0.6 };
      skillsChecker(description);
    }

    function deleteClick(){
      console.log(index)
      onDelete(index)
    }

    

    return(
        
            <div key={index} className="form">
                <FormInput
                inputType={"text"}
                identifier={"company"}
                labelText={"Company"}
                currVal={workHistory.company}
                onChangeHandler={updateWorkHistory}
              />
              <FormInput
                inputType={"text"}
                identifier={"jobTitle"}
                labelText={"Job Title"}
                currVal={workHistory.jobTitle}
                onChangeHandler={updateWorkHistory}
              />
              <FormInput
                inputType={"date"}
                identifier={"startDate"}
                labelText={"Start Date"}
                currVal={workHistory.startDate}
                onChangeHandler={updateWorkHistory}
              />
                <FormInput
                inputType={"date"}
                identifier={"endDate"}
                labelText={"End Date"}
                currVal={workHistory.endDate}
                onChangeHandler={updateWorkHistory}
              />
                <textarea
                className="textarea"
                id={"jobDescription"+index}
                name={"jobDescription" }
                value={workHistory.jobDescription}
                onChange={updateWorkHistory}
               
              />
              <Button color={Colors.ALERT} onClick={deleteClick}>Cancel</Button>
              <Button onClick={handleClick}> load skills</Button>
              
            </div>
          );
}

export default AddWorkHistoryForm;