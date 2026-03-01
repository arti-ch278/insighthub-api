import { useEffect, useState } from "react";
import { deleteUser, getUsers } from "../api/userApi";

export default function UserList({ reload }) {

  const [userList, setUserList] = useState([]);

  const loadUsers = async () => {
    const res = await getUsers();
    setUserList(res.data.content);
  };

  useEffect(() => {
    loadUsers();
  }, [reload]);

  const handleDelete = async (id) => {
    await deleteUser(id);
    loadUsers();
  };

  return (
    <div className="p-4">
      <h2 className="text-lg font-bold mb-3">All Users</h2>

      {userList.map((u) => (
        <div key={u.id} className="flex justify-between border p-2 mb-2">
          <span>{u.name} - {u.email}</span>

          <button
            onClick={() => handleDelete(u.id)}
            className="bg-red-500 text-black px-3 py-1 rounded"
          >
            Delete
          </button>
        </div>
      ))}
    </div>
  );
}