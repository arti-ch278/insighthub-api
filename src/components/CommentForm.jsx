import { useEffect, useState } from "react";
import { addComment } from "../api/commentApi";
import attachTokenToApi from "../api/axiosconfig";


export default function CommentForm({ postId, parentCommentId, onCommentAdded }) {

  const [comment, setComment] = useState("");
  const token = localStorage.getItem("token");
  useEffect(()=>{
    if(token){
        attachTokenToApi(token);
    }
  },[token])
  

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!token) {
      alert("Login required to comment");
      return;
    }

     try {
     const commentData = parentCommentId  ? {content:comment, parentCommentId } : {content:comment};

       await addComment(postId, commentData);

       setComment("");
       onCommentAdded();
     } catch (err) {
       console.error("Error adding comment:", err);
     }
  };

  return (
    <form
  onSubmit={handleSubmit}
  className="my-3 flex flex-col gap-2 bg-gray-50 p-3 rounded-lg"
>
  <textarea
    value={comment}
    onChange={(e) => setComment(e.target.value)}
    placeholder="Add a comment..."
    className="border border-gray-300 rounded-md p-2 w-full focus:ring-2 focus:ring-blue-400 outline-none"
    rows="3"
  />

 
  <div className="flex justify-start">
    <button
      type="submit"
      className="bg-blue-600 text-white px-3 py-1 rounded-md hover:bg-blue-700 transition-all text-sm"
    >
      Comment
    </button>
  </div>
</form>
  );
}