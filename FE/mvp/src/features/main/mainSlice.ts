import { createSlice, createAsyncThunk, PayloadAction } from '@reduxjs/toolkit';

interface MainState {
  todayIn: number;
  todayOut: number;
  todayIncome: number;
  searchTerm: string;
  startDate: string;
  endDate: string;
  searchData: Array<{ carNumber: string; parkingDate: string; carState: string }>;
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
  status: 'idle',
  error: null,
};

export const fetchParkingData = createAsyncThunk('main/fetchParkingData', async () => {
  const response = await fetch('https://api.example.com/parking-data');
  const data = await response.json();
  return data;
});

export const fetchSearchData = createAsyncThunk(
  'main/fetchSearchData',
  async ({ searchTerm, startDate, endDate }: { searchTerm: string; startDate: string; endDate: string }) => {
    // API 요청을 대체하는 임시 데이터
    // const response = await fetch(`https://api.example.com/search?term=${searchTerm}&startDate=${startDate}&endDate=${endDate}`);
    // const data = await response.json();
    const data = [
        { carNumber: '123가 4568', parkingDate: '20240729', carState: '주차 중' },
        { carNumber: '123가 4568', parkingDate: '20240728', carState: '출차 완료' },
        { carNumber: '123가 4568', parkingDate: '20240728', carState: '출차 완료' },
        { carNumber: '123가 4568', parkingDate: '20240727', carState: '출차 완료' },
        { carNumber: '123가 4568', parkingDate: '20240727', carState: '출차 완료' },
        { carNumber: '123가 4568', parkingDate: '20240727', carState: '출차 완료' },
        { carNumber: '123가 4568', parkingDate: '20240726', carState: '출차 완료' },
        { carNumber: '123가 4568', parkingDate: '20240725', carState: '출차 완료' },
        { carNumber: '123가 4568', parkingDate: '20240725', carState: '출차 완료' },
        { carNumber: '123가 4568', parkingDate: '20240724', carState: '출차 완료' },
        { carNumber: '123가 4568', parkingDate: '20240724', carState: '출차 완료' },
        { carNumber: '123가 4568', parkingDate: '20240724', carState: '출차 완료' },
    ];
    return data;
  }
);

const mainSlice = createSlice({
  name: 'main',
  initialState,
  reducers: {
    setSearchTerm: (state, action: PayloadAction<string>) => {
      state.searchTerm = action.payload;
    },
    setStartDate: (state, action: PayloadAction<string>) => {
      state.startDate = action.payload;
    },
    setEndDate: (state, action: PayloadAction<string>) => {
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
      });
  },
});

export const { setSearchTerm, setStartDate, setEndDate } = mainSlice.actions;

export default mainSlice.reducer;
