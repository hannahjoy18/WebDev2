function ProfileList() {
    const profiles = ["Hannah", "Krisha", "Chenkate", "Gianna"];
  
    return (
      <div className="component-box">
        <h2>Profile List</h2>
        <ul>
          {profiles.map((name, index) => (
            <li key={index}>{name}</li>
          ))}
        </ul>
      </div>
    );
  }
  
  export default ProfileList;
  