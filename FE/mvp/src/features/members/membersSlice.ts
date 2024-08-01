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
    { id: 6, name: '오동규', car: '77마 7777', phone: '010-0000-0000', join_date: new Date('2024-01-01'), secession_date: new Date('2024-12-31') },
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
