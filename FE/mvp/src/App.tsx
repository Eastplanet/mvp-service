// src/App.tsx
import React from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { RootState } from './store/store';
import Login from './features/auth/Login';
import Main from './features/main/Main';
import Members from './features/members/Members';
import Set from './features/set/Set';
import Chart from './features/chart/Chart';
import './App.css';

function App() {
  const isAuthenticated = useSelector((state: RootState) => state.auth.isAuthenticated);

  return (
    <Router>
      <div className="App">
        <Routes>
          {isAuthenticated ? (
            <>
              <Route path="/home" element={<Main />} />
              <Route path="/members" element={<Members />} />
              <Route path="/setting" element={<Set />} />
              
              <Route path="/chart" element={<Chart />} />
              <Route path="*" element={<Navigate to="/home" />} />
              
              {/* 여기에 추가적인 라우트를 설정할 수 있습니다. */}
            </>
          ) : (
            <>
              <Route path="/login" element={<Login />} />
              <Route path="*" element={<Navigate to="/login" />} />
            </>
          )}
        </Routes>
      </div>
    </Router>
  );
}

export default App;
