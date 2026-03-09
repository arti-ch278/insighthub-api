import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import PostForm from "../components/PostForm";
import { createPost, uploadPostImage } from "../api/postApi";
import { getAllCategories } from "../api/categoryApi";
import { useAuth } from "../context/AuthContext";
import attachTokenToApi from "../api/axiosconfig";

export default function CreatePost() {
  const [categories, setCategories] = useState([]);
  const [categoryId, setCategoryId] = useState("");
  const { user: currentUser, token } = useAuth();
  const navigate = useNavigate();

  // Attach JWT token
  useEffect(() => {
    if (token) attachTokenToApi(token);
  }, [token]);

  // Fetch categories
  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const res = await getAllCategories();
        setCategories(res.data);

        if (res.data.length > 0) {
          setCategoryId(String(res.data[0].categoryId));
        }
      } catch (err) {
        console.error("Error fetching categories:", err);
      }
    };

    fetchCategories();
  }, []);

  const handleCreate = async ({ title, content, categoryId, image }) => {
    if (!currentUser) {
      alert("You must be logged in to create a post!");
      return;
    }

    const numericCategoryId = Number(categoryId);
    if (isNaN(numericCategoryId)) {
      alert("Please select a valid category");
      return;
    }

    const postDto = { title, content };

    try {
      // Create post
      const createdPost = await createPost(
        postDto,
        currentUser.id,
        numericCategoryId
      );

      const postId = createdPost.data.postId;

      // Upload image
      if (image) {
        await uploadPostImage(postId, image); 
      }

      alert("Post created successfully!");
      navigate("/posts");
    } catch (err) {
      console.error("Error creating post:", err);
      alert("Failed to create post");
    }
  };

  return (
    <div className="max-w-2xl mx-auto mt-10 p-6 bg-white shadow rounded">
      <h2 className="text-2xl font-bold mb-6">Create Post</h2>

      <PostForm
        onSubmit={handleCreate}
        categories={categories}
        categoryId={categoryId}
        setCategoryId={setCategoryId}
      />
    </div>
  );
}