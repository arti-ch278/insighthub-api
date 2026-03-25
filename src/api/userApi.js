import { api } from "./api"

//create user

export const createUser = (user) => api.post("", user);
export const getUsers = () => api.get("");
//export const getUsers = (page = 0, size = 10) => api.get(`?page=${page}&size=${size}`);
export const getUserById = (id) => api.get(`/${id}`);
export const updateUser = (id, user) => api.put(`/${id}`, user);
export const deleteUser = (id) => api.delete(`/${id}`);