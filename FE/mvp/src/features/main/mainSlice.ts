// features/main/mainSlice.ts
import { createSlice, createAsyncThunk, PayloadAction } from '@reduxjs/toolkit';
import api from '../../api/axios';
import { logoutSuccess } from '../auth/authSlice';

interface CarLog {
  licensePlate: string;
  parkingDate: string;
  carState: string;
  entryTime: string;
  exitTime?: string;
  fee: number;
  lotState?: number;
  imageBase64?: string;
}

interface MainState {
  todayIn: number;
  todayOut: number;
  todayIncome: number;
  licensePlate: string;
  startDate: string;
  endDate: string;
  searchData: CarLog[];
  currentParkedCars: CarLog[];
  status: 'idle' | 'loading' | 'succeeded' | 'failed';
  error: string | null;
}

const initialState: MainState = {
  todayIn: 0,
  todayOut: 0,
  todayIncome: 0,
  licensePlate: '',
  startDate: '',
  endDate: '',
  searchData: [],
  currentParkedCars: [],
  status: 'idle',
  error: null,
};

export const fetchParkingData = createAsyncThunk(
  'main/fetchParkingData',
  async (_, thunkAPI) => {
    const { dispatch, rejectWithValue } = thunkAPI;
    try {
      const response = await api.get('/stats/home-init');
      const data = response.data.data;
      const parkingLots = data.parkingLots.map((lot: any) => ({
        licensePlate: lot.licensePlate,
        parkingDate: lot.parkingDate,
        carState: lot.carState === 0 ? '주차 중' : lot.carState === 1 ? '대기 중' : lot.carState === 2 ? '이동 중' : '',
        entryTime: new Date(lot.entranceTime).toISOString(),
        exitTime: lot.exitTime ? new Date(lot.exitTime).toISOString() : undefined,
        fee: lot.fee,
        lotState: lot.lotState,
        imageBase64: lot.image,
      }));
      
      return {
        todayIn: data.todayIn,
        todayOut: data.todayOut,
        todayIncome: data.todayIncome,
        currentParkedCars: parkingLots,
      };
    } catch (error: any) {
      if (error.response && error.response.status === 401) {
        dispatch(logoutSuccess());
      }
      return rejectWithValue(error.message || '데이터 가져오기에 실패했습니다.');
    }
  }
);

export const fetchSearchData = createAsyncThunk(
  'main/fetchSearchData',
  async ({ licensePlate, startDate, endDate }: { licensePlate: string; startDate: string; endDate: string }) => {
    startDate = new Date(startDate).toISOString().split('.')[0];
    endDate = new Date(endDate).toISOString().split('.')[0];

    const response = await api.get('/stats/parking-log', {
      params: { licensePlate, startDate, endDate }
    });

    const data = response.data.data;
    const parkingLots = data.map((lot: any) => ({
      licensePlate: lot.licensePlate,
      parkingDate: lot.parkingDate,
      carState: lot.parkingState === 1 ? '출차' : '입차',
      entryTime: new Date(lot.entranceTime).toISOString(),
      exitTime: lot.exitTime ? new Date(lot.exitTime).toISOString() : undefined,
      fee: lot.fee,
      imageBase64: lot.image,
    }));
    return parkingLots;
  }
);

const mainSlice = createSlice({
  name: 'main',
  initialState,
  reducers: {
    setLicensePlate(state, action: PayloadAction<string>) {
      state.licensePlate = action.payload;
    },
    setStartDate(state, action: PayloadAction<string>) {
      state.startDate = action.payload;
    },
    setEndDate(state, action: PayloadAction<string>) {
      state.endDate = action.payload;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchSearchData.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(fetchSearchData.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.searchData = action.payload;
      })
      .addCase(fetchSearchData.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.error.message || null;
      })
      .addCase(fetchParkingData.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(fetchParkingData.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.todayIn = action.payload.todayIn;
        state.todayOut = action.payload.todayOut;
        state.todayIncome = action.payload.todayIncome;
        state.currentParkedCars = action.payload.currentParkedCars;
      })
      .addCase(fetchParkingData.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.error.message || null;
      })
  },
});

export const { setLicensePlate, setStartDate, setEndDate } = mainSlice.actions;

export default mainSlice.reducer;