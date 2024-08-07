import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';

interface AuthState {
  user: {
    name: string | null;
    email: string | null;
    phoneNumber?: string | null;
  } | null;
  token: string | null;
  isAuthenticated: boolean;
  loading: boolean;
  error: string | null;
}

const initialState: AuthState = {
  user: null,
  token: localStorage.getItem('apiKey'),
  isAuthenticated: !!localStorage.getItem('apiKey'),
  loading: false,
  error: null,
};

export const login = createAsyncThunk(
  'auth/login',
  async (credentials: { email: string; password: string }) => {
    const response = await axios.post('https://mvp-project.shop/api/manager/login', credentials);
    return response.data;
  }
);

export const logout = createAsyncThunk('auth/logout', async () => {
  localStorage.removeItem('apiKey');
});

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(login.pending, (state) => {
      state.loading = true;
      state.error = null;
    });
    builder.addCase(login.fulfilled, (state, action) => {
      state.loading = false;
      const { name, email, phoneNumber, apiKey } = action.payload.data;
      state.user = { name, email, phoneNumber };
      state.token = apiKey;
      state.isAuthenticated = true;
      state.error = null;
      localStorage.setItem('apiKey', apiKey);
    });
    builder.addCase(login.rejected, (state, action) => {
      state.loading = false;
      state.error = action.error.message || '로그인에 실패했습니다.';
    });
    builder.addCase(logout.fulfilled, (state) => {
      state.user = null;
      state.token = null;
      state.isAuthenticated = false;
    });
  },
});

export default authSlice.reducer;
