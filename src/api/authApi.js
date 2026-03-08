import { api2 } from "./api";

export const loginApi = (userName, password) => {
  return api2.post("/login", {
    username: userName,
    password: password,
  });
};