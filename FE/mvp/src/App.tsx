// src/App.tsx
import React from 'react';
import { useSelector } from 'react-redux';
import { RootState } from './store/store';
import Login from './features/auth/Login';
import Main from './features/main/main'; // 예: 로그인 후 보여질 대시보드 컴포넌트
import './App.css';

function App() {
  // Redux 상태에서 인증 상태를 가져옵니다.
  const isAuthenticated = useSelector((state: RootState) => state.auth.isAuthenticated);

  return (
    <div className="App">
      {isAuthenticated ? (
        <Main /> // 사용자가 로그인된 경우, 대시보드 컴포넌트를 렌더링
      ) : (
        <Login /> // 로그인되지 않은 경우, 로그인 컴포넌트를 렌더링
      )}
    </div>
  );
}

export default App;
