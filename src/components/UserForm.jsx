import { useState } from "react";
import { createUser } from "../api/userApi";

export default function UserForm({ onSuccess }) {

  const [user, setUser] = useState({
    name: "",
    email: "",
    password: "",
    about: ""
  });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await createUser(user);
      alert("User created");
      onSuccess();
    } catch (err) {
      alert("Failed to create user");
      console.error(err);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="p-4 border rounded">
      <h2 className="text-lg font-bold mb-3">Create User</h2>

      <input
        type="text"
        placeholder="Name"
        className="border p-2 w-full mb-2"
        onChange={(e) => setUser({ ...user, name: e.target.value })}
      />

      <input
        type="email"
        placeholder="Email"
        className="border p-2 w-full mb-2"
        onChange={(e) => setUser({ ...user, email: e.target.value })}
      />

      <input
        type="password"
        placeholder="Password"
        className="border p-2 w-full mb-2"
        onChange={(e) => setUser({ ...user, password: e.target.value })}
      />

      <input
        type="text"
        placeholder="About"
        className="border p-2 w-full mb-2"
        onChange={(e) => setUser({ ...user, about: e.target.value })}
      />

      <button className="bg-blue-600 text-black px-4 py-2 rounded">
        Submit
      </button>
    </form>
  );
}