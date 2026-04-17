import { Link, useNavigate } from "react-router-dom"; 
import { useAuth } from "../context/AuthContext";

export default function Navbar() {
  const { isAuthenticated, user: currentUser, logout } = useAuth();
  const navigate = useNavigate(); 

  const handleLogout = () => { 
    logout();                  
    navigate("/login");        
  };

  return (
    <nav className="flex flex-wrap justify-between items-center px-6 py-3 bg-gray-800 text-white">
      <Link to="/" className="text-2xl font-bold">My Blog</Link>
      <div className="flex items-center gap-4">
        <Link to="/posts" className="hover:text-yellow-400">All Posts</Link>
        <Link to="/users" className="hover:text-yellow-400">
          Users
        </Link>

        {isAuthenticated ? (
          <>
            <span className="opacity-80">
              Hi, {currentUser?.name}
            </span>

            <Link to="/create-post" className="hover:text-yellow-400">Create Post</Link>

            {/* Manage Categories for any logged-in user */}
            <Link to="/categories" className="hover:text-yellow-400">
              Manage Categories
            </Link>

            <button
              onClick={handleLogout}  
              className="bg-red-500 px-3 py-1 rounded text-white hover:bg-red-600 cursor-pointer"
            >
              Logout
            </button>
          </>
        ) : (
          <Link to="/login" className="hover:text-yellow-400">Login</Link>
        )}
      </div>
    </nav>
  );
}