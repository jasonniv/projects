function UserInfoFromResume(props){

    return (
        <tr>
           <td>{props.infoData.firstName}</td>
           <td>{props.infoData.lastName}</td>
           <td>{props.infoData.email}</td>
           <td>{props.infoData.address}</td>
           <td>{props.infoData.phoneNumber}</td>
        </tr>
    );
}

export default UserInfoFromResume;