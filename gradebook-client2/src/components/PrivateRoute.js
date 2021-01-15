import React from 'react';
import { Redirect } from 'react-router-dom';
import { getUser } from '../tools/user';

let isAuthenticated = false;
const PrivateRoute = ({ component: Component, ...rest }) => {
  if (getUser()) {
    isAuthenticated = true;
  } else {
    isAuthenticated = false;
  }
  return isAuthenticated ? <Component {...rest} /> : <Redirect to="/login" />;
};
export default PrivateRoute;
