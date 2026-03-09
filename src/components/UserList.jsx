import { useEffect, useState } from "react";
import { deleteUser, getUsers } from "../api/userApi";

export default function UserList({ reload }) {
  const [userList, setUserList] = useState([]);

  const loadUsers = async () => {
    try {
      const res = await getUsers();
      console.log("USERS RESPONSE:", res.data);

      // Extract array safely
      const users = Array.isArray(res.data.content) ? res.data.content : [];
      setUserList(users);

    } catch (err) {
      console.error("Error loading users:", err);
      setUserList([]);
    }
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
          <span>{u.userName} - {u.email}</span>

          <button
            onClick={() => handleDelete(u.id)}
            className="bg-red-500 px-3 py-1 rounded text-white hover:bg-red-600 cursor-pointer"
          >
            Delete
          </button>
        </div>
      ))}

      {userList.length === 0 && (
        <p className="text-gray-500 text-center">No users found</p>
      )}
    </div>
  );
}