import { useEffect, useState } from "react"; 
import { Link } from "react-router-dom";
import { getAllPostSummary } from "../api/postApi";

export default function PostList() {
  const [posts, setPosts] = useState([]);

  useEffect(() => {
    const loadPosts = async () => {
      try {
        const res = await getAllPostSummary();
        console.log("Posts API response:", res.data);

        // Sort posts by addedDate descending (newest first)
        const sortedPosts = (res.data || []).sort(
          (a, b) => new Date(b.addedDate) - new Date(a.addedDate)
        );

        setPosts(sortedPosts); // set sorted posts
      } catch (err) {
        console.error("Error loading posts:", err);
      }
    };
    loadPosts();
  }, []);

  return (
    <div className="mt-6">
      <h2 className="text-xl font-bold mb-4">All Posts</h2>
      <div className="space-y-4">
        {Array.isArray(posts) && posts.length > 0 ? (
          posts.map((p) => (
            <div className="p-4 border rounded bg-gray-50" key={p.id}>
              <h3 className="text-lg font-semibold">{p.title}</h3>
              <p className="text-gray-700 break-all">{p.content}</p>
              <div className="text-sm text-gray-500">
                Category: {p.categoryName || "N/A"} |
                Created: {new Date(p.addedDate).toLocaleString()} |
                Author: {p.authorName}
              </div>

              <Link
                to={`/posts/${p.postId}`}
                className="text-blue-600 hover:underline mt-2 block"
              >
                Read More
              </Link>
            </div>
          ))
        ) : (
          <p>No posts found.</p>
        )}
      </div>
    </div>
  );
}