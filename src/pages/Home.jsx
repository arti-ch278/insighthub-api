import { useState } from "react";
import UserForm from "../components/userForm";
import UserList from "../components/UserList";


function Home (){

  const [reload,setReload] = useState(false);

  return (
    
    <div className="grid grid-cols-2 gap-4 p-6">
      <UserForm onSuccess={() => setReload(!reload)}></UserForm>
      <UserList reload={reload} />
    </div>
  );
}

export default Home;