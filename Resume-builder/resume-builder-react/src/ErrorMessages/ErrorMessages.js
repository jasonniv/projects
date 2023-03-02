
function ErrorMessages({ errorList }) {
    return (
        // <div className="container">
        //     <div className="alert-box alert round">
        //     <ul>
        //         {errorList.map((s,k) => <li key={k}> {s} </li>)}
        //     </ul>
        //     </div>
        // </div>
        
        // <div data-alert className="alert-box">

        //     <a href="#" class="close">&times;</a>
        // </div>
        
        <div className="callout alert small" data-closable="slide-out-right">
            <ul>
                {errorList.map(e => (
                     <div key={e}>{e}</div>
                ))}
            </ul>
      </div>
);
}
export default ErrorMessages;