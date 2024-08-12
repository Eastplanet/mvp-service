import { createSlice, createAsyncThunk, PayloadAction } from '@reduxjs/toolkit';
import api from '../../api/axios';
import { logoutSuccess } from '../auth/authSlice';

interface RevenueData {
  date: string;
  revenue: number;
  parkingCount: number;
}

interface ChartData {
  dailyRevenues: RevenueData[];
  monthlyRevenues: RevenueData[];
  totalRevenue: number;
  totalMembershipsRevenue: number;
  usingTimeAvg: number;
  revenueAvg: number;
}

interface ChartState {
  data: ChartData | null;
  loading: boolean;
  error: string | null;
}

const initialState: ChartState = {
  data: null,
  loading: false,
  error: null,
};

export const fetchChartData = createAsyncThunk<ChartData, void>(
  'chart/fetchChartData',
  async (_, thunkAPI) => {
    const { dispatch, rejectWithValue } = thunkAPI;
    try {
      const response = await api.get('/stats/revenue');
      return response.data.data;
    } catch (error: any) {
      if (error.response && error.response.status === 401) {
        dispatch(logoutSuccess());
      }
      return rejectWithValue(error.message || '데이터 가져오기에 실패했습니다.');
    }
  }
);

const chartSlice = createSlice({
  name: 'chart',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchChartData.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchChartData.fulfilled, (state, action: PayloadAction<ChartData>) => {
        state.data = action.payload;
        console.log(state.data)
        state.loading = false;
        state.error = null;
      })
      .addCase(fetchChartData.rejected, (state, action: PayloadAction<any>) => {
        state.loading = false;
        state.error = action.payload;
      });
  },
});

export default chartSlice.reducer;
