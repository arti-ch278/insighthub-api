
import { useState, useEffect } from "react";
import { useAuth } from "../context/AuthContext";
import attachTokenToApi from "../api/axiosconfig";
import {
  getCategories,
  createCategory,
  updateCategory,
  deleteCategory,
} from "../api/categoryApi";

export default function Categories() {
  const { token } = useAuth(); 
  const [categories, setCategories] = useState([]);
  const [newTitle, setNewTitle] = useState("");
  const [newDescription, setNewDescription] = useState("");
  const [editingId, setEditingId] = useState(null);
  const [editingTitle, setEditingTitle] = useState("");
  const [editingDescription, setEditingDescription] = useState("");

  // Attach token
  useEffect(() => {
    if (token) attachTokenToApi(token);
  }, [token]);

  // Fetch categories
  const fetchCategories = async () => {
    if (!token) return;
    try {
      const res = await getCategories();
      setCategories(Array.isArray(res.data) ? res.data : res.data.content);
    } catch (err) {
      console.error("Error fetching categories:", err);
    }
  };

  useEffect(() => {
    fetchCategories();
  }, [token]);

  // Add new category
  const handleAdd = async () => {
    if (!newTitle) return alert("Category title is required");
    try {
      await createCategory({
        categoryTitle: newTitle,
        categoryDescription: newDescription,
      });
      setNewTitle("");
      setNewDescription("");
      fetchCategories();
    } catch (err) {
      console.error("Error adding category:", err);
    }
  };

  // Start editing
  const startEdit = (cat) => {
    setEditingId(cat.categoryId);
    setEditingTitle(cat.categoryTitle);
    setEditingDescription(cat.categoryDescription || "");
  };

  // Save update
  const handleUpdate = async () => {
    if (!editingTitle) return alert("Category title is required");
    try {
      await updateCategory(editingId, {
        categoryTitle: editingTitle,
        categoryDescription: editingDescription,
      });
      setEditingId(null);
      setEditingTitle("");
      setEditingDescription("");
      fetchCategories();
    } catch (err) {
      console.error("Error updating category:", err);
    }
  };

  // Delete category
  const handleDelete = async (categoryId) => {
    try {
      await deleteCategory(categoryId);
      fetchCategories();
    } catch (err) {
      console.error("Error deleting category:", err);
    }
  };

  // Show login prompt if not logged in
  if (!token) {
    return (
      <div className="p-5 text-red-500">
        Please log in to manage categories.
      </div>
    );
  }

  return (
    <div className="p-5 bg-gray-50 rounded max-w-3xl mx-auto">
      <h2 className="text-2xl font-bold mb-4">Manage Categories</h2>

      {/* Add new category */}
      <div className="flex flex-col gap-2 mb-5">
        <input
          type="text"
          value={newTitle}
          onChange={(e) => setNewTitle(e.target.value)}
          placeholder="Category title"
          className="border p-2 rounded w-full"
        />
        <textarea
          value={newDescription}
          onChange={(e) => setNewDescription(e.target.value)}
          placeholder="Category description (optional)"
          className="border p-2 rounded w-full"
          rows={3}
        />
        <button
          onClick={handleAdd}
          className="bg-blue-500 text-white px-3 py-1 rounded hover:bg-blue-600 w-32"
        >
          Add Category
        </button>
      </div>

      {/* List categories */}
      <ul className="space-y-3">
  {categories.map((cat) => (
    <li
      key={cat.categoryId}
      className="flex flex-col bg-white p-3 rounded shadow-sm"
    >
      {editingId === cat.categoryId ? (
       
        <div className="flex flex-col gap-2 w-full">
          <input
            type="text"
            value={editingTitle}
            onChange={(e) => setEditingTitle(e.target.value)}
            className="border p-1 rounded w-full"
            placeholder="Category title"
          />
          <textarea
            value={editingDescription}
            onChange={(e) => setEditingDescription(e.target.value)}
            rows={2}
            className="border p-1 rounded w-full"
            placeholder="Category description (optional)"
          />
          <div className="flex gap-2">
            <button
              onClick={handleUpdate}
              className="bg-green-500 text-white px-3 rounded hover:bg-green-600"
            >
              Save
            </button>
            <button
              onClick={() => {
                setEditingId(null);
                setEditingTitle("");
                setEditingDescription("");
              }}
              className="bg-gray-400 text-white px-3 rounded hover:bg-gray-500"
            >
              Cancel
            </button>
          </div>
        </div>
      ) : (
        
        <div className="flex justify-between items-start">
          <div>
            <p className="font-semibold">{cat.categoryTitle}</p>
            {cat.categoryDescription && (
              <p className="text-gray-600">{cat.categoryDescription}</p>
            )}
          </div>

          <div className="flex gap-2">
            <button
              onClick={() => {
                setEditingId(cat.categoryId);
                setEditingTitle(cat.categoryTitle);
                setEditingDescription(cat.categoryDescription || "");
              }}
              className="bg-yellow-400 text-white px-3 rounded hover:bg-yellow-500"
            >
              Edit
            </button>

            <button
              onClick={() => {
                if (cat.postCount && cat.postCount > 0) {
                  alert(
                    "Cannot delete category: it has posts assigned"
                  );
                  return;
                }
                handleDelete(cat.categoryId);
              }}
              className="bg-red-500 text-white px-3 rounded hover:bg-red-600"
            >
              Delete
            </button>
          </div>
        </div>
      )}
    </li>
  ))}
</ul>
    </div>
  );
}