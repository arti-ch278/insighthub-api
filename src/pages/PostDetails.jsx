import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { getPostById, deletePost } from "../api/postApi";
import Comments from "../components/Comments";

const getCurrentUserId = () => {
  const user = JSON.parse(localStorage.getItem("user"));
  return user ? user.id : null;
};

export default function PostDetails() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [post, setPost] = useState(null);
  const [loading, setLoading] = useState(true);
  const [deleting, setDeleting] = useState(false);
  const [currentUserId, setCurrentUserId] = useState(null);

  useEffect(() => {
    loadPost();
    setCurrentUserId(getCurrentUserId());
  }, [id]);

  const loadPost = async () => {
    try {
      setLoading(true);
      const res = await getPostById(id);
      setPost(res.data);
    } catch (err) {
      console.error("Error fetching post:", err);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async () => {
    const confirmed = window.confirm("Are you sure you want to delete this post?");
    if (!confirmed) return;

    try {
      setDeleting(true);
      await deletePost(post.postId);
      alert("Post deleted successfully!");
      navigate("/posts");
    } catch (err) {
      console.error("Delete failed:", err);
      alert("Failed to delete post.");
    } finally {
      setDeleting(false);
    }
  };

  const handleUpdate = () => {
    navigate(`/posts/edit/${post.postId}`);
  };

  if (loading) return <div>Loading...</div>;
  if (!post) return <div>Post not found!</div>;

  const isAuthor = currentUserId === post.userId;

  return (
    <div className="max-w-3xl mx-auto p-6 border rounded bg-gray-50 shadow">

      {/* Update + Delete Buttons */}
      {isAuthor && (
        <div className="flex justify-end space-x-2 mb-4">
          <button
            className="bg-yellow-500 text-white px-3 py-1 rounded hover:bg-yellow-600"
            onClick={handleUpdate}
            disabled={deleting}
          >
            Update Post
          </button>

          <button
            className="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600"
            onClick={handleDelete}
            disabled={deleting}
          >
            {deleting ? "Deleting..." : "Delete Post"}
          </button>
        </div>
      )}

      {/* Title */}
      
      <h1 className="text-3xl font-bold mb-4 break-all">
        {post.title}
      </h1>

      {/* Image */}
      {post.imageName && (
        <img
          src={`https://insighthub-api.onrender.com${post.imageName}`}
          alt={post.title}
          className="w-full max-h-[400px] object-cover rounded mb-6"
        />
      )}

      {/* Post Info */}
      <p className="text-gray-500 text-sm mb-6">
        Created: {post.addedDate ? new Date(post.addedDate).toLocaleString() : "N/A"} |{" "}
        Category: {post.categoryName} | Author: {post.authorName}
      </p>

      {/* Post Content */}
      
      <p className="text-gray-700 whitespace-pre-line break-all mb-8">
        {post.content}
      </p>

      {/* Comments */}
      <div className="mt-10">
        <h2 className="text-xl font-semibold mb-3">Comments</h2>
        <Comments postId={post.postId} />
      </div>

    </div>
  );
}