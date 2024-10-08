import { configureStore, combineReducers } from '@reduxjs/toolkit';
import { persistStore, persistReducer } from 'redux-persist';
import storage from 'redux-persist/lib/storage';
import authReducer from '../features/auth/authSlice';
import mainReducer from '../features/main/mainSlice';
import membersReducer from '../features/members/membersSlice';
import chartReducer from '../features/chart/chartSlice';

// 각 리듀서 결합
const rootReducer = combineReducers({
  auth: authReducer,
  main: mainReducer,
  members: membersReducer,
  chart: chartReducer,
});

// persist 설정
const persistConfig = {
  key: 'root',
  storage,
  whitelist: ['auth'],
};

// persistReducer를 사용하여 리듀서를 감싸기
const persistedReducer = persistReducer(persistConfig, rootReducer);

// 스토어 구성
export const store = configureStore({
  reducer: persistedReducer,
});

export const persistor = persistStore(store);

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;