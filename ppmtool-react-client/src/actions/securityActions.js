import axios from 'axios';
import { GET_ERRORS, SET_CURRENT_USER } from './types';
import jwt_decode from 'jwt-decode';

import setJwtToken from '../securityUtils/setJwtToken';

export const createNewUser = (newUser, history) => async (dispatch) => {
  try {
    await axios.post('/api/users/register', newUser);
    history.push('/login');
    dispatch({
      type: GET_ERRORS,
      payload: {},
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data,
    });
  }
};

// LoginRequest is the JSON data having username and password info
export const login = (LoginRequest) => async (dispatch) => {
  try {
    // post => login request
    const res = await axios.post('/api/users/login', LoginRequest);

    // extract token from res.data
    const { token } = res.data;

    // store the token in localstorage
    localStorage.setItem('jwtToken', token);

    // set our token in header
    setJwtToken(token);

    // decode token on React & get payload
    const decoded = jwt_decode(token);

    // dispatch to securityReducer
    dispatch({
      type: SET_CURRENT_USER,
      payload: decoded,
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: GET_ERRORS.response.data,
    });
  }
};
