import { useEffect, useState } from "react";

export default function PostForm({ onSubmit, categories, categoryId, setCategoryId }) {
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [image, setImage] = useState(null);
  const [preview, setPreview] = useState(null);

  
  useEffect(() => {
    if (categories.length > 0 && !categoryId) {
      setCategoryId(String(categories[0].categoryId));
    }
  }, [categories]);

  // Handle image selection
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

    // send image also
    onSubmit({ title, content, categoryId, image });

    // reset form
    setTitle("");
    setContent("");
    setImage(null);
    setPreview(null);

    if (categories.length > 0) {
      setCategoryId(String(categories[0].categoryId));
    }
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4 bg-white p-4 rounded shadow">

      {/* Title */}
      <input
        type="text"
        placeholder="Post Title"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
        className="w-full border px-3 py-2 rounded"
        required
      />

      {/* Content */}
      <textarea
        placeholder="Post Content"
        value={content}
        onChange={(e) => setContent(e.target.value)}
        className="w-full border px-3 py-2 rounded"
        rows={5}
        required
      />

      {/* Category */}
      <select
        value={categoryId}
        onChange={(e) => setCategoryId(e.target.value)}
        className="w-full border px-3 py-2 rounded"
        required
      >
        <option value="" disabled>Select a category</option>

        {categories.map((c) => (
          <option key={c.categoryId} value={String(c.categoryId)}>
            {c.categoryTitle}
          </option>
        ))}
      </select>

      {/* Image Upload */}
      <div>
        <input
          type="file"
          accept="image/png, image/jpeg, image/jpg"
          onChange={handleImageChange}
          className="border p-2 rounded"
        />
      </div>

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
        Publish
      </button>

    </form>
  );
}