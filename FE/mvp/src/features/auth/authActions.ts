// src/features/auth/authActions.ts
import { createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';

export const login = createAsyncThunk('auth/login', async (credentials: { email: string; password: string }) => {
  const response = await axios.post('/api/login', credentials);
  return response.data;
});

export const logout = createAsyncThunk('auth/logout', async () => {
  await axios.post('/api/logout');
});
