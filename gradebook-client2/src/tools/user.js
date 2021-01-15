let user = null;
export const saveUser = (usr) => {
  user = usr;
};
export const getUser = () => {
  return user;
};

export const deleteUser = () => {
  user = null;
};
