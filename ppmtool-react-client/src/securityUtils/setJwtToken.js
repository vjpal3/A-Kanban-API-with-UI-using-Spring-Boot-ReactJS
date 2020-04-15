import axios from 'axios';

const setJwtToken = (token) => {
  if (token) {
    // set the header with 'Authorization' as key
    axios.defaults.headers.common['Authorization'] = token;
  } else {
    delete axios.defaults.headers.common['Authorization'];
  }
};

export default setJwtToken;
