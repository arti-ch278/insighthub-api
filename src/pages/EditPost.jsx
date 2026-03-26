import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { getPostById, updatePost, uploadPostImage } from "../api/postApi";
import { getAllCategories } from "../api/categoryApi";
import PostForm from "../components/PostForm";

export default function EditPost() {

  const { id } = useParams();
  const navigate = useNavigate();

  const [post, setPost] = useState(null);
  const [categories, setCategories] = useState([]);
  const [categoryId, setCategoryId] = useState("");
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const loadData = async () => {
      try {

        const postRes = await getPostById(id);
        const catRes = await getAllCategories();

        setPost(postRes.data);
        setCategories(catRes.data);

        // set current category
        setCategoryId(String(postRes.data.categoryId));

      } catch (err) {
        console.error("Error loading data:", err);
      } finally {
        setLoading(false);
      }
    };

    loadData();
  }, [id]);

  const handleSubmit = async ({ title, content, image }) => {

    try {

      // update title + content + category
      await updatePost(
        {
          title,
          content,
          categoryId
        },
        id
      );

      // upload image if selected
      if (image) {
        await uploadPostImage(id, image);
      }

      alert("Post updated successfully!");
      navigate(`/posts/${id}`);

    } catch (err) {
      console.error("Failed to update post:", err);
      alert("Failed to update post");
    }
  };

  if (loading) return <div>Loading...</div>;
  if (!post) return <div>Post not found</div>;

  return (
    <div className="w-full flex justify-center">
  <div className="w-full max-w-2xl">

    <h2 className="text-2xl font-bold mb-6">
      Edit Post
    </h2>

    <PostForm
      onSubmit={handleSubmit}
      categories={categories}
      categoryId={categoryId}
      setCategoryId={setCategoryId}
      initialTitle={post.title}
      initialContent={post.content}
      initialImageName={post.imageName}
      isEdit={true}
    />

  </div>
</div>
  );
}