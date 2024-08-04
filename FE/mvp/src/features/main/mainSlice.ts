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

// export const fetchParkingData = createAsyncThunk('main/fetchParkingData', async () => {
//   const response = await axios.get('http://mvp-project.shop:8081/stats/home-init');
//   const data = response.data.data;
//   const parkingLots = data.parkingLots.map((lot: any) => ({
//     licensePlate: lot.licensePlate,
//     parkingDate: lot.parkingDate,
//       carState: lot.carState === 1 ? '주차 중' : lot.carState === 0 ? '출차 완료' : '이동 중',
//     entryTime: new Date(lot.entranceTime).toISOString(),
//     exitTime: lot.exitTime ? new Date(lot.exitTime).toISOString() : undefined,
//     fee: lot.fee,
//     imageBase64: lot.image,
//   }));
  
//   return {
//     todayIn: data.todayIn,
//     todayOut: data.todayOut,
//     todayIncome: data.todayIncome,
//     currentParkedCars: parkingLots,
//   };
// });

export const fetchParkingData = createAsyncThunk('main/fetchParkingData', async () => {
  // 실제 API 요청을 주석 처리합니다.
  // const response = await axios.get('http://mvp-project.shop:8081/stats/home-init');
  // const data = response.data.data;

  // 임시 데이터
  const parkingLots = [
    {
      licensePlate: 'ABC123',
      parkingDate: '2024-08-04T12:00:00Z',
      carState: '주차 중',
      entryTime: new Date().toISOString(),
      exitTime: undefined,
      fee: 1000,
      imageBase64: '',
    },
    {
      licensePlate: 'XYZ789',
      parkingDate: '2024-08-04T08:00:00Z',
      carState: '출차 완료',
      entryTime: new Date(new Date().setHours(new Date().getHours() - 6)).toISOString(),
      exitTime: new Date().toISOString(),
      fee: 500,
      imageBase64: '',
    },
    {
      licensePlate: 'LMN456',
      parkingDate: '2024-08-04T09:00:00Z',
      carState: '이동 중',
      entryTime: new Date(new Date().setHours(new Date().getHours() - 5)).toISOString(),
      exitTime: undefined,
      fee: 750,
      imageBase64: '',
    },
    {
      licensePlate: 'QWE234',
      parkingDate: '2024-08-03T15:00:00Z',
      carState: '주차 중',
      entryTime: new Date(new Date().setDate(new Date().getDate() - 1)).toISOString(),
      exitTime: undefined,
      fee: 1200,
      imageBase64: '',
    },
    {
      licensePlate: 'RTY567',
      parkingDate: '2024-08-02T14:00:00Z',
      carState: '출차 완료',
      entryTime: new Date(new Date().setDate(new Date().getDate() - 2)).toISOString(),
      exitTime: new Date(new Date().setDate(new Date().getDate() - 1)).toISOString(),
      fee: 900,
      imageBase64: '',
    },
    {
      licensePlate: 'UIO890',
      parkingDate: '2024-08-01T10:00:00Z',
      carState: '이동 중',
      entryTime: new Date(new Date().setDate(new Date().getDate() - 3)).toISOString(),
      exitTime: undefined,
      fee: 600,
      imageBase64: '',
    },
    {
      licensePlate: 'PAS123',
      parkingDate: '2024-07-31T09:00:00Z',
      carState: '주차 중',
      entryTime: new Date(new Date().setDate(new Date().getDate() - 4)).toISOString(),
      exitTime: undefined,
      fee: 1100,
      imageBase64: '',
    },
  ];
  
  // 임시 데이터를 사용하여 반환합니다.
  return {
    todayIn: 5,
    todayOut: 2,
    todayIncome: 6000,
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
      carState: lot.parkingState === 1 ? '주차 중' : lot.carState === 0 ? '출차 완료' : '이동 중',
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