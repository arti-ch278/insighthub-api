
import { Route, Routes } from "react-router-dom";
import Home from "./pages/Home";
import Posts from "./pages/Posts";          // main posts page
import CreatePost from "./pages/CreatePost";
import Login from "./components/Login";
import Navbar from "./components/Navbar";
import AuthProvider from "./context/AuthContext";
import PostDetails from "./pages/PostDetails";
import EditPost from "./pages/EditPost";   
import Categories from "./pages/Categories"; 



export default function App() {
  return (
    <AuthProvider>
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/posts" element={<Posts />} />        
        <Route path="/create-post" element={<CreatePost />} />
        <Route path="/login" element={<Login />} />
        <Route path="/posts/:id" element={<PostDetails />} />
        {/*  added route for update/edit post */}
        <Route path="/posts/edit/:id" element={<EditPost />} />
        <Route path="/categories" element={<Categories />} />
      </Routes>
    </AuthProvider>
  );
}