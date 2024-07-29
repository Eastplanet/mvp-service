import { configureStore } from '@reduxjs/toolkit';
import { persistStore, persistReducer } from 'redux-persist';
import storage from 'redux-persist/lib/storage';
import authReducer from '../features/auth/authSlice';

// persist 설정
const persistConfig = {
  key: 'root',
  storage,
  whitelist: ['auth'],
};

// persistReducer를 사용하여 리듀서를 감싸기
const persistedReducer = persistReducer(persistConfig, authReducer);

// 스토어 구성
export const store = configureStore({
  reducer: {
    auth: persistedReducer,
  },
});

export const persistor = persistStore(store);

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
