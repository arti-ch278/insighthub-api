import { api } from "./api"

//create user

export const createUser = (user) => api.post("", user);
export const getUsers = () => api.get("");
export const getUserById = (id) => api.get(`/${id}`);
export const updateUser = (id, user) => api.put(`/${id}`, user);
export const deleteUser = (id) => api.delete(`/${id}`);