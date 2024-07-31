// features/main/mainSlice.ts
import { createSlice, createAsyncThunk, PayloadAction } from '@reduxjs/toolkit';
import axios from 'axios';

interface CarLog {
  carNumber: string;
  parkingDate: string;
  carState: string;
  entryTime: Date;
  exitTime?: Date;
  fee: number;
  imageBase64?: string;
}

interface MainState {
  todayIn: number;
  todayOut: number;
  todayIncome: number;
  searchTerm: string;
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
  searchTerm: '',
  startDate: '',
  endDate: '',
  searchData: [],
  currentParkedCars: [],
  status: 'idle',
  error: null,
};

export const fetchParkingData = createAsyncThunk('main/fetchParkingData', async () => {
  const response = await fetch('https://api.example.com/parking-data');
  const data = await response.json();
  return data;
});

export const fetchCurrentParkedCars = createAsyncThunk('main/fetchCurrentParkedCars', async () => {
  // const response = await axios.get<CarLog[]>('https://api.example.com/currentParkedCars');
  // return response.data;
  const data: CarLog[] = [
    {
      carNumber: '123가 4568',
      parkingDate: '20240729',
      carState: '주차 중',
      entryTime: new Date(2024, 6, 29, 14, 15),
      exitTime: undefined,
      fee: 1000,
      imageBase64: 'iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg==',
    },
  ]
  return data;
});

export const fetchSearchData = createAsyncThunk(
  'main/fetchSearchData',
  async ({ searchTerm, startDate, endDate }: { searchTerm: string; startDate: string; endDate: string }) => {
    // 예제 데이터 (API 요청 대신 사용)
    // const response = await axios.get('https://api.example.com/search', {
    //   params: { term: searchTerm, startDate, endDate }
    // });
    // const data = response.data;
    const data: CarLog[] = [
      {
        carNumber: '123가 4568',
        parkingDate: '20240729',
        carState: '주차 중',
        entryTime: new Date(2024, 6, 29, 14, 15),
        exitTime: undefined,
        fee: 1000,
        imageBase64: 'iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg==',
      },
      {
        carNumber: '123가 4568',
        parkingDate: '20240728',
        carState: '출차 완료',
        entryTime: new Date(2024, 6, 28, 10, 15),
        exitTime: new Date(2024, 6, 28, 11, 7),
        fee: 1000
      }
    ]
    return data;
  }
);

const mainSlice = createSlice({
  name: 'main',
  initialState,
  reducers: {
    setSearchTerm(state, action: PayloadAction<string>) {
      state.searchTerm = action.payload;
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
      .addCase(fetchParkingData.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(fetchParkingData.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.todayIn = action.payload.todayIn;
        state.todayOut = action.payload.todayOut;
        state.todayIncome = action.payload.todayIncome;
      })
      .addCase(fetchParkingData.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.error.message || null;
      })
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
      .addCase(fetchCurrentParkedCars.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(fetchCurrentParkedCars.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.currentParkedCars = action.payload;
      })
      .addCase(fetchCurrentParkedCars.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.error.message || null;
      });
  },
});

export const { setSearchTerm, setStartDate, setEndDate } = mainSlice.actions;

export default mainSlice.reducer;