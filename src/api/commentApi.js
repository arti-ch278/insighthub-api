import { api3 } from "./api"

export const addComment=(postId,comment)=>  {return api3.post(`/${postId}`,comment);};
export const getCommentByPostId=(postId)=> {return api3.get(`/${postId}`);};
export const deleteComment=(commentId)=> {return api3.delete(`/${commentId}`);};

