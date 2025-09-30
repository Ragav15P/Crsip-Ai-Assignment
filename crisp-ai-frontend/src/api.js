// src/api.js
import axios from 'axios';

const baseURL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

const api = axios.create({
  baseURL,
  timeout: 20000,
});

export default api;
