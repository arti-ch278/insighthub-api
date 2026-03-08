
import axios from "axios";

// Users API
export const api = axios.create({
  baseURL: "http://localhost:8080/api/users",
});

// Posts API
export const api1 = axios.create({
  baseURL: "http://localhost:8080/api/posts",
});

// Auth API
export const api2 = axios.create({
  baseURL: "http://localhost:8080/api/auth",
});
// Comment API
export const api3 = axios.create({
  baseURL: "http://localhost:8080/api/comments",
});
// Categories API 
export const api4 = axios.create({ 
  baseURL: "http://localhost:8080/api/categories"
 });