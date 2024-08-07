import axios from 'axios';

const api = axios.create({
  baseURL: 'https://mvp-project.shop/api',
});

api.interceptors.request.use((config) => {
  const apiKey = localStorage.getItem('apiKey');
  if (apiKey) {
    config.headers['API-KEY'] = apiKey;
  }
  return config;
}, (error) => {
  return Promise.reject(error);
});

export default api;