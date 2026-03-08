
import { api, api1, api2, api3, api4 } from "./api";


export default function attachTokenToApi(token) {
  if (token) {
    api.defaults.headers.common["Authorization"] = `Bearer ${token}`;
    api1.defaults.headers.common["Authorization"] = `Bearer ${token}`;
    api2.defaults.headers.common["Authorization"] = `Bearer ${token}`;
    api3.defaults.headers.common["Authorization"] = `Bearer ${token}`;
    api4.defaults.headers.common["Authorization"] = `Bearer ${token}`; 
  } else {
    delete api.defaults.headers.common["Authorization"];
    delete api1.defaults.headers.common["Authorization"];
    delete api2.defaults.headers.common["Authorization"];
    delete api3.defaults.headers.common["Authorization"];
    delete api4.defaults.headers.common["Authorization"];
  }
}


