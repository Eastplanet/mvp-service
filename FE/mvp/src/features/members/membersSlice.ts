import { createSlice, PayloadAction, createAsyncThunk } from '@reduxjs/toolkit';
import api from '../../api/axios';
import { setLicensePlate } from '../main/mainSlice';

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
  try {
    const response = await api.get('https://mvp-project.shop/api/memberships/list');
    const data = response.data.data;
    return data.map((item: any, index: number) => ({
      id: index + 1,
      name: item.name,
      car: item.licensePlate,
      phone: item.phoneNumber,
      join_date: new Date(item.startDate),
      secession_date: new Date(item.endDate),
    }));
  } catch (error) {
    console.error(error);
  }
});

export const deleteMembersFromServer = createAsyncThunk<void, string[]>('members/deleteMembers', async (licensePlates) => {
  await Promise.all(
    licensePlates.map((licensePlate) => api.delete(`https://mvp-project.shop/api/memberships/${licensePlate}`))
  );
});

export const updateMemberOnServer = createAsyncThunk<Member, Member>(
  'members/updateMemberOnServer',
  async (member) => {
    console.log([member.car,member.secession_date.toISOString(),member.phone,member.name])
    const response = await api.patch(`https://mvp-project.shop/api/memberships`, {
      licensePlate: member.car,
      endDate: member.secession_date.toISOString(),
      phoneNumber: member.phone,
      name: member.name,
    });
    return {
      ...member,
      secession_date: response.data.endDate.toISOString(),
      phone: response.data.phoneNumber,
      name: response.data.name,
    };
  }
);

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
        state.members = state.members.filter(member => !action.meta.arg.includes(member.car));
      })
      .addCase(updateMemberOnServer.fulfilled, (state, action) => {
        const index = state.members.findIndex(member => member.car === action.payload.car);
        if (index !== -1) {
          state.members[index] = action.payload;
        }
      });
  }
});

export const { setMembers, addMember, updateMember, deleteMember } = membersSlice.actions;
export default membersSlice.reducer;
