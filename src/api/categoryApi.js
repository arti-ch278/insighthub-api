

import { api4 } from "./api";

// Create a new category
export const createCategory = (category) => api4.post("", category);

// Update an existing category
export const updateCategory = (categoryId, category) =>
  api4.put(`/${categoryId}`, category);

// Get a single category by ID
export const getCategoryById = (categoryId) =>
  api4.get(`/${categoryId}`);

// Get all categories (paginated)
export const getCategories = (pageNumber = 0, pageSize = 10) =>
  api4.get(`?pageNumber=${pageNumber}&pageSize=${pageSize}`);

// Get all categories (no paging) – for Create Post dropdown
export const getAllCategories = () => api4.get(""); 

// Delete a category
export const deleteCategory = (categoryId) =>
  api4.delete(`/${categoryId}`);