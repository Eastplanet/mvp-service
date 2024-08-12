import React, { useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import { RootState } from './store/store';
import Login from './features/auth/Login';
import Main from './features/main/Main';
import Members from './features/members/Members';
import Set from './features/set/Set';
import Chart from './features/chart/Chart';
import './App.css';
import { fetchParkingData } from './features/main/mainSlice';

function App() {
  const isAuthenticated = useSelector((state: RootState) => state.auth.isAuthenticated);
  const dispatch = useDispatch();
    
  const parkingData = useSelector((state: RootState) => state.main.currentParkedCars);
  const userName = useSelector((state: RootState) => state.auth.user?.name);
  
  useEffect(() => {
    const eventSource = new EventSource(`https://mvp-project.shop/api/notify/subscribe/${userName}`);

    eventSource.onopen = () => {
      console.log("Connection opened");
    };
    
    eventSource.onmessage = (event) => {
      console.log("Received message:", event.data);
    };

    eventSource.onerror = (error) => {
      console.error("EventSource failed:", error);
    };

    eventSource.addEventListener('업무수정', (event) => {
      dispatch(fetchParkingData() as any);
      console.log("Fetched Parking Data:", parkingData);
      console.log(event.data);
    });

    return () => {
      console.log("end!");
      eventSource.close();
    };
  }, []);

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
              <Route path="/*" element={<Navigate to="/home" />} />
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