// features/main/mainSlice.ts
import { createSlice, createAsyncThunk, PayloadAction } from '@reduxjs/toolkit';
import axios from 'axios';

interface CarLog {
  licensePlate: string;
  parkingDate: string;
  carState: string;
  entryTime: string;
  exitTime?: string;
  fee: number;
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

const getDefaultDateRange = () => {
  const toSimpleISODate = (date: Date) => {
    return date.toISOString().replace(/\.\d{3}Z$/, '');
  };
  
  const currentDate = new Date();
  const endDate = toSimpleISODate(currentDate);
  const startDate = toSimpleISODate(new Date(currentDate.setDate(currentDate.getDate() - 30)));
  return { startDate, endDate };
};

export const fetchParkingData = createAsyncThunk('main/fetchParkingData', async () => {
  const response = await axios.get('http://mvp-project.shop:8081/stats/home-init');
  const data = response.data.data;
  const parkingLots = data.parkingLots.map((lot: any) => ({
    licensePlate: lot.licensePlate,
    parkingDate: lot.parkingDate,
    carState: lot.carState === 1 ? '주차 중' : lot.carState === 0 ? '출차 완료' : '이동 중',
    entryTime: new Date(lot.entranceTime).toISOString(),
    exitTime: lot.exitTime ? new Date(lot.exitTime).toISOString() : undefined,
    fee: lot.fee,
    imageBase64: lot.image,
  }));
  
  return {
    todayIn: data.todayIn,
    todayOut: data.todayOut,
    todayIncome: data.todayIncome,
    currentParkedCars: parkingLots,
  };
});

export const fetchSearchData = createAsyncThunk(
  'main/fetchSearchData',
  async ({ licensePlate, startDate, endDate }: { licensePlate: string; startDate: string; endDate: string }) => {
    if (!startDate || !endDate) {
      const defaultDates = getDefaultDateRange();
      startDate = defaultDates.startDate;
      endDate = defaultDates.endDate;
    } else {
      startDate = new Date(startDate).toISOString();
      endDate = new Date(endDate).toISOString();
    }

    const response = await axios.get('http://mvp-project.shop:8081/stats/parking-log', {
      params: { licensePlate, startDate, endDate }
    });

    const data = response.data.data;
    const parkingLots = data.map((lot: any) => ({
      licensePlate: lot.licensePlate,
      parkingDate: lot.parkingDate,
      carState: lot.carState === 1 ? '주차 중' : lot.carState === 0 ? '출차 완료' : '이동 중',
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
    setlicensePlate(state, action: PayloadAction<string>) {
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

export const { setlicensePlate, setStartDate, setEndDate } = mainSlice.actions;

export default mainSlice.reducer;