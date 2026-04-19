import { useEffect, useState } from "react";

export default function PostForm({
  onSubmit,
  categories = [],
  categoryId,
  setCategoryId,
  initialTitle = "",
  initialContent = "",
  initialImageName = null,
  isEdit = false
}) {

  const [title, setTitle] = useState(initialTitle);
  const [content, setContent] = useState(initialContent);
  const [image, setImage] = useState(null);
  const [preview, setPreview] = useState(null);

  useEffect(() => {

    setTitle(initialTitle);
    setContent(initialContent);

   if (initialImageName) {
  setPreview(`https://insighthub-api.onrender.com${initialImageName}`);
}

  }, [initialTitle, initialContent, initialImageName]);

  // default category
  useEffect(() => {
    if (categories.length > 0 && !categoryId) {
      setCategoryId(String(categories[0].categoryId));
    }
  }, [categories]);

  // handle image change
  const handleImageChange = (e) => {

    const file = e.target.files[0];

    setImage(file);

    if (file) {
      setPreview(URL.createObjectURL(file));
    }

  };

  const handleSubmit = (e) => {

    e.preventDefault();

    if (!categoryId) return;

    onSubmit({
      title,
      content,
      categoryId,
      image
    });

    // reset form only if creating post
    if (!isEdit) {
      setTitle("");
      setContent("");
      setImage(null);
      setPreview(null);
    }

  };

  return (
    <form
      onSubmit={handleSubmit}
      className="space-y-4 bg-white p-6 rounded-lg shadow-md w-full max-w-2xl mx-auto"
    >

      {/* Title */}

     <input
  type="text"
  placeholder="Post Title"
  value={title}
  onChange={(e) => setTitle(e.target.value)}
  className="block w-full border border-gray-300 px-3 py-2 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 break-all"
  required
/>

      {/* Content */}

      <textarea
  placeholder="Post Content"
  value={content}
  onChange={(e) => setContent(e.target.value)}
  className="block w-full border border-gray-300 px-3 py-2 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 break-all resize-none"
  rows={8}
  required
/>

      {/* Category */}

      <select
  value={categoryId}
  onChange={(e) => setCategoryId(e.target.value)}
  className="block w-full box-border border border-gray-300 px-3 py-2 rounded-md"
  required
>

        <option value="" disabled>
          Select a category
        </option>

        {categories.map((c) => (

          <option
            key={c.categoryId}
            value={String(c.categoryId)}
          >
            {c.categoryTitle}
          </option>

        ))}

      </select>

      {/* Image Upload */}

      <input
        type="file"
        accept="image/png, image/jpeg, image/jpg"
        onChange={handleImageChange}
        className="w-full box-border border p-2 rounded"
      />

      {/* Image Preview */}

      {preview && (

        <div>

          <img
            src={preview}
            alt="preview"
            className="w-40 rounded border"
          />

        </div>

      )}

      {/* Submit */}

      <button
        type="submit"
        className="w-full bg-green-600 text-white py-2 rounded hover:bg-green-700"
      >
        {isEdit ? "Update Post" : "Publish"}
      </button>

    </form>
  );
}