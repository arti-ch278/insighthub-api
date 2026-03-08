import { createContext, useContext, useEffect, useState } from "react";
import attachTokenToApi from "../api/axiosconfig";
import { loginApi } from "../api/authApi";


const AuthContext=createContext();

export default function AuthProvider({children}){
    const [user,setUser]=useState(()=> JSON.parse( localStorage.getItem("user")));
    const [token ,setToken]=useState(()=>localStorage.getItem("token"));
    useEffect(()=>{
        attachTokenToApi(token);
    },[token]);


const login = async (userName, password) => {
  try {
    const res = await loginApi(userName, password);
    const { token, user } = res.data;
    setUser(user);
    setToken(token);
    localStorage.setItem("user", JSON.stringify(user));
    localStorage.setItem("token", token);
    
    // return token along with success
    return { success: true, token }; 
  } catch (err) {
    const msg = err.response?.data?.message || "Login Failed";
    return { success: false, message: msg };
  }
};

    const logout=()=>{
        setUser(null);
        setToken(null);
        localStorage.removeItem("user");
        localStorage.removeItem("token");
    };
return (
    <AuthContext.Provider value={{user, token,login,logout, isAuthenticated: !!token}}>
    {children}
    </AuthContext.Provider>
);
}
export const useAuth=()=> useContext(AuthContext);