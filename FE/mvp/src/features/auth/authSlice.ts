import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';

interface AuthState {
  user: any;
  token: string | null;
  isAuthenticated: boolean;
  loading: boolean;
  error: string | null;
}

const initialState: AuthState = {
  user: null,
  token: null,
  isAuthenticated: false,
  loading: false,
  error: null,
};

export const login = createAsyncThunk(
  'auth/login',
  async (credentials: { email: string; password: string }) => {
    // const response = await axios.post('/api/login', credentials);
    // return response.data;
    return {
      user: {
        id: 1,
        name: 'John Doe',
        email: credentials.email,
      },
      token: 'dummy-token',
    };
  }
);

export const logout = createAsyncThunk('auth/logout', async () => {
  await axios.post('/api/logout');
});

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    loginSuccess: (state) => {
      state.isAuthenticated = true;
    },
    logoutSuccess: (state) => {
      state.isAuthenticated = false;
    },
  },
  extraReducers: (builder) => {
    // builder.addCase(login.pending, (state) => {
    //   state.loading = true;
    //   state.error = null;
    // });
    // builder.addCase(login.fulfilled, (state, action) => {
    //   state.loading = false;
    //   state.user = action.payload.user;
    //   state.token = action.payload.token;
    //   state.isAuthenticated = true;
    // });
    // builder.addCase(login.rejected, (state, action) => {
    //   state.loading = false;
    //   state.error = action.error.message || 'Failed to login';
    // });
    // builder.addCase(logout.fulfilled, (state) => {
    //   state.user = null;
    //   state.token = null;
    //   state.isAuthenticated = false;
    // });
  },
});

export const { loginSuccess, logoutSuccess } = authSlice.actions;

export default authSlice.reducer;
