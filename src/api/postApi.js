import { api1 } from "./api";

// CREATE
export const createPost = (post, userId, categoryId) =>
  api1.post(`/user/${userId}/category/${categoryId}`, post);

// UPDATE 
export const updatePost = (postData, postId, file) => {
  if (file) {
    // If uploading new image
    const formData = new FormData();
    formData.append("title", postData.title);
    formData.append("content", postData.content);
    formData.append("file", file); // new image file
    return api1.put(`/${postId}`, formData, {
      headers: { "Content-Type": "multipart/form-data" },
    });
  } else {
    // No new image, send JSON with old imageName
    return api1.put(`/${postId}`, postData);
  }
};

// GET SINGLE
export const getPostById = (postId) => api1.get(`/${postId}`);

// DELETE
export const deletePost = (postId) => api1.delete(`/${postId}`);

// GET ALL POST SUMMARY
export const getAllPostSummary = () => api1.get("/summary");

// UPLOAD POST IMAGE 
export const uploadPostImage = (postId, file) => {
  const formData = new FormData();
  formData.append("file", file);
  return api1.post(`/${postId}/upload-image`, formData);
};