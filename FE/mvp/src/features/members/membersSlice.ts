// membersSlice.ts
import { createSlice, PayloadAction, createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';

interface Member {
  id: number;
  name: string;
  car: string;
  phone: string;
  join_date: Date;
  secession_date: Date;
}

interface MembersState {
  members: Member[];
  status: 'idle' | 'loading' | 'succeeded' | 'failed';
  error: string | null;
}

const initialState: MembersState = {
  members: [],
  status: 'idle',
  error: null,
};

export const fetchMembers = createAsyncThunk<Member[]>('members/fetchMembers', async () => {
  // try {
  //   const response = await axios.get<Member[]>('/api/members');
  //   return response.data;
  // } catch (error) {
  //   if (axios.isAxiosError(error) && error.response) {
  //     throw new Error(error.response.data);
  //   }
  //   throw new Error('Failed to fetch members');
  // }
  return [
    { id: 1, name: '김동현', car: '123가 4568', phone: '010-0000-0000', join_date: new Date('2024-01-31'), secession_date: new Date('2024-12-31') },
    { id: 2, name: '김세진', car: '11나 2233', phone: '010-0000-0000', join_date: new Date('2024-07-20'), secession_date: new Date('2024-12-31') },
    { id: 3, name: '손우혁', car: '12다 3457', phone: '010-0000-0000', join_date: new Date('2024-05-31'), secession_date: new Date('2024-12-31') },
    { id: 4, name: '손원륜', car: '98라 9653', phone: '010-0000-0000', join_date: new Date('2024-03-31'), secession_date: new Date('2024-12-31') },
    { id: 5, name: '오동규', car: '77마 7777', phone: '010-0000-0000', join_date: new Date('2024-07-04'), secession_date: new Date('2024-07-31') },
    { id: 6, name: '홍길동', car: '77마 7777', phone: '010-0000-0000', join_date: new Date('2024-01-01'), secession_date: new Date('2024-12-31') },
    { id: 7, name: '강호동', car: '77마 0007', phone: '010-1000-1006', join_date: new Date('2023-07-01'), secession_date: new Date('2024-07-01') },
  { id: 8, name: '조수연', car: '77마 0008', phone: '010-1000-1007', join_date: new Date('2023-08-01'), secession_date: new Date('2024-08-01') },
  { id: 9, name: '하준호', car: '77마 0009', phone: '010-1000-1008', join_date: new Date('2023-09-01'), secession_date: new Date('2024-09-01') },
  { id: 10, name: '서지수', car: '77마 0010', phone: '010-1000-1009', join_date: new Date('2023-10-01'), secession_date: new Date('2024-10-01') },
  { id: 11, name: '이승기', car: '77마 0011', phone: '010-1000-1010', join_date: new Date('2023-11-01'), secession_date: new Date('2024-11-01') },
  { id: 12, name: '김유정', car: '77마 0012', phone: '010-1000-1011', join_date: new Date('2023-12-01'), secession_date: new Date('2024-12-01') },
  { id: 13, name: '홍석천', car: '77마 0013', phone: '010-1000-1012', join_date: new Date('2024-01-01'), secession_date: new Date('2025-01-01') },
  { id: 14, name: '이예림', car: '77마 0014', phone: '010-1000-1013', join_date: new Date('2024-02-01'), secession_date: new Date('2025-02-01') },
  { id: 15, name: '장동건', car: '77마 0015', phone: '010-1000-1014', join_date: new Date('2024-03-01'), secession_date: new Date('2025-03-01') },
  { id: 16, name: '오승환', car: '77마 0016', phone: '010-1000-1015', join_date: new Date('2024-04-01'), secession_date: new Date('2025-04-01') },
  { id: 17, name: '유미', car: '77마 0017', phone: '010-1000-1016', join_date: new Date('2024-05-01'), secession_date: new Date('2025-05-01') },
  { id: 18, name: '박서준', car: '77마 0018', phone: '010-1000-1017', join_date: new Date('2024-06-01'), secession_date: new Date('2025-06-01') },
  { id: 19, name: '조민호', car: '77마 0019', phone: '010-1000-1018', join_date: new Date('2024-07-01'), secession_date: new Date('2025-07-01') },
  { id: 20, name: '신세경', car: '77마 0020', phone: '010-1000-1019', join_date: new Date('2024-08-01'), secession_date: new Date('2025-08-01') },
  { id: 21, name: '하정우', car: '77마 0021', phone: '010-1000-1020', join_date: new Date('2024-09-01'), secession_date: new Date('2025-09-01') },
  { id: 22, name: '김보라', car: '77마 0022', phone: '010-1000-1021', join_date: new Date('2024-10-01'), secession_date: new Date('2025-10-01') },
  { id: 23, name: '유승호', car: '77마 0023', phone: '010-1000-1022', join_date: new Date('2024-11-01'), secession_date: new Date('2025-11-01') },
  { id: 24, name: '민수', car: '77마 0024', phone: '010-1000-1023', join_date: new Date('2024-12-01'), secession_date: new Date('2025-12-01') },
  { id: 25, name: '장난감', car: '77마 0025', phone: '010-1000-1024', join_date: new Date('2025-01-01'), secession_date: new Date('2026-01-01') },
  { id: 26, name: '안현수', car: '77마 0026', phone: '010-1000-1025', join_date: new Date('2025-02-01'), secession_date: new Date('2026-02-01') },
  { id: 27, name: '정가람', car: '77마 0027', phone: '010-1000-1026', join_date: new Date('2025-03-01'), secession_date: new Date('2026-03-01') },
  { id: 28, name: '이유진', car: '77마 0028', phone: '010-1000-1027', join_date: new Date('2025-04-01'), secession_date: new Date('2026-04-01') },
  { id: 29, name: '전지현', car: '77마 0029', phone: '010-1000-1028', join_date: new Date('2025-05-01'), secession_date: new Date('2026-05-01') },
  { id: 30, name: '오지호', car: '77마 0030', phone: '010-1000-1029', join_date: new Date('2025-06-01'), secession_date: new Date('2026-06-01') },
  { id: 31, name: '임수정', car: '77마 0031', phone: '010-1000-1030', join_date: new Date('2025-07-01'), secession_date: new Date('2026-07-01') },
  { id: 32, name: '정진영', car: '77마 0032', phone: '010-1000-1031', join_date: new Date('2025-08-01'), secession_date: new Date('2026-08-01') },
  { id: 33, name: '배수지', car: '77마 0033', phone: '010-1000-1032', join_date: new Date('2025-09-01'), secession_date: new Date('2026-09-01') },
  { id: 34, name: '하나', car: '77마 0034', phone: '010-1000-1033', join_date: new Date('2025-10-01'), secession_date: new Date('2026-10-01') },
  { id: 35, name: '한지민', car: '77마 0035', phone: '010-1000-1034', join_date: new Date('2025-11-01'), secession_date: new Date('2026-11-01') },
  { id: 36, name: '강아지', car: '77마 0036', phone: '010-1000-1035', join_date: new Date('2025-12-01'), secession_date: new Date('2026-12-01') },
  ];
});

export const deleteMembersFromServer = createAsyncThunk<void, number[]>('members/deleteMembers', async (ids) => {
  await axios.post('/api/deleteMembers', { ids });
});

const membersSlice = createSlice({
  name: 'members',
  initialState,
  reducers: {
    setMembers(state, action: PayloadAction<Member[]>) {
      state.members = action.payload;
    },
    addMember(state, action: PayloadAction<Member>) {
      state.members.push(action.payload);
    },
    updateMember(state, action: PayloadAction<Member>) {
      const index = state.members.findIndex(member => member.id === action.payload.id);
      if (index !== -1) {
        state.members[index] = action.payload;
      }
    },
    deleteMember(state, action: PayloadAction<number[]>) {
      state.members = state.members.filter(member => !action.payload.includes(member.id));
    }
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchMembers.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(fetchMembers.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.members = action.payload;
      })
      .addCase(fetchMembers.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.error.message || 'Failed to fetch members';
      })
      .addCase(deleteMembersFromServer.fulfilled, (state, action) => {
        state.members = state.members.filter(member => !action.meta.arg.includes(member.id));
      });
  }
});

export const { setMembers, addMember, updateMember, deleteMember } = membersSlice.actions;
export default membersSlice.reducer;
