import { useEffect, useState } from "react";
import CommentForm from "./CommentForm";
import { getCommentByPostId, deleteComment } from "../api/commentApi";

const Comment = ({ comment, postId, onCommentAdded }) => {
  const [showReply, setShowReply] = useState(false);

  // Delete handler
  const handleDelete = async () => {
    const id = comment.id;
    if (!id) {
      console.error("Comment ID missing:", comment);
      return;
    }

    try {
      await deleteComment(id);
      onCommentAdded();
    } catch (err) {
      console.error("Error deleting comment:", err);
    }
  };

  return (
    <div className={`${comment.parentCommentId ? "ml-5" : "ml-0"} my-2`}>
      {/* Comment content and actions */}
      <div className="flex items-center justify-between bg-gray-100 p-2 rounded">
        <p className="font-semibold">
          {comment.username} : {comment.content}
        </p>

        <div className="flex gap-3">
          <button
            onClick={() => setShowReply(!showReply)}
            className="text-blue-500 text-sm hover:underline"
          >
            Reply
          </button>

          <button
            onClick={handleDelete}
            className="text-red-500 text-sm hover:underline"
          >
            Delete
          </button>
        </div>
      </div>

      {/* Reply form */}
      {showReply && (
        <CommentForm
          postId={postId}
          parentCommentId={comment.id}
          onCommentAdded={onCommentAdded}
        />
      )}

      {/* Render replies recursively */}
      {comment.replies
        ?.filter((reply) => reply.id) // ignore replies with missing id
        .map((reply) => (
          <Comment
            key={`reply-${reply.id}`}
            comment={reply}
            postId={postId}
            onCommentAdded={onCommentAdded}
          />
        ))}
    </div>
  );
};

const Comments = ({ postId }) => {
  const [comments, setComments] = useState([]);

  const fetchComments = async () => {
    try {
      const res = await getCommentByPostId(postId);
      // Only render top-level comments (parentCommentId = null)
      const topLevelComments = res.data.filter(
        (comment) => comment.parentCommentId === null
      );
      setComments(topLevelComments);
    } catch (err) {
      console.error("Error fetching comments:", err);
    }
  };

  useEffect(() => {
    fetchComments();
  }, [postId]);

  return (
    <div>
      <CommentForm postId={postId} onCommentAdded={fetchComments} />

      {comments.map((comment) => (
        <Comment
          key={`comment-${comment.id}`} // unique key
          comment={comment}
          postId={postId}
          onCommentAdded={fetchComments}
        />
      ))}
    </div>
  );
};

export default Comments;