import { createSlice, createAsyncThunk, PayloadAction } from '@reduxjs/toolkit';
import api from '../../api/axios';

interface RevenueData {
  date: string;
  revenue: number;
  parkingCount: number;
}

interface ChartData {
  dailyRevenue: RevenueData[];
  monthlyRevenue: RevenueData[];
  totalRevenue: number;
  membershipProfit: number;
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
    try {
      const response = await api.get('https://mvp-project.shop/api/stats/revenue');
      console.log(response.data.data)
      return response.data.data;
    } catch (error) {
      console.error('Failed to fetch data:', error);
      return thunkAPI.rejectWithValue('Failed to fetch data');
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
