import { api1 } from "./api";

// CREATE
export const createPost = (post, userId, categoryId) =>
  api1.post(`/user/${userId}/category/${categoryId}`, post);

// UPDATE
export const updatePost = (post, postId) =>
  api1.put(`/${postId}`, post);

// GET SINGLE
export const getPostById = (postId) =>
  api1.get(`/${postId}`);

// DELETE
export const deletePost = (postId) =>
  api1.delete(`/${postId}`);

// GET ALL POST SUMMARY

export const getAllPostSummary = () => api1.get("/summary");
//  Upload post image

export const uploadPostImage = (postId, file) => {
  const formData = new FormData();
  formData.append("file", file);   

  return api1.post(`/${postId}/upload-image`, formData);
};