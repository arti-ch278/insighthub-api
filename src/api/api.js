import axios from "axios";

// Users API
export const api = axios.create({
  baseURL: "https://insighthub-api.onrender.com/api/users",
});

// Posts API
export const api1 = axios.create({
  baseURL: "https://insighthub-api.onrender.com/api/posts",
});

// Auth API
export const api2 = axios.create({
  baseURL: "https://insighthub-api.onrender.com/api/auth",
});

// Comment API
export const api3 = axios.create({
  baseURL: "https://insighthub-api.onrender.com/api/comments",
});

// Categories API 
export const api4 = axios.create({ 
  baseURL: "https://insighthub-api.onrender.com/api/categories"
});