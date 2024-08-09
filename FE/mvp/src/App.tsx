import React, { useEffect, useState, useRef } from 'react';
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

import { EventSourcePolyfill, NativeEventSource } from 'event-source-polyfill';

interface Message {
  message: string;
  timestamp: string;
}

function App() {
  const isAuthenticated = useSelector((state: RootState) => state.auth.isAuthenticated);
  const dispatch = useDispatch();
  const [messages, setMessages] = useState<Message[]>([]);
  const token = useSelector((state: RootState) => state.auth.token);
  const eventSourceRef = useRef<EventSource | null>(null);
  
  // const token = localStorage.getItem('apiKey');
  // useEffect(() => {
  //   if (isAuthenticated && token) {
  //     const fetchSse = async () => {
  //       try {
  //         const EventSource = EventSourcePolyfill || NativeEventSource;
  //         eventSourceRef.current = new EventSource(`https://mvp-project.shop/api/notify/subscribe/testEmail`, {
  //           headers: {
  //             "Content-Type": "text/event-stream",
  //             'API-KEY': token,
  //           },
  //           // withCredentials: true,
  //         });

  //         eventSourceRef.current.onmessage = (event: MessageEvent) => {
  //           console.log('Received new message:', event.data);
  //           const newMessage: Message = JSON.parse(event.data);
  //           console.log('message', newMessage);

  //           dispatch(fetchParkingData() as any);
  //           setMessages((prevMessages) => [...prevMessages, newMessage]);
  //         };

  //         eventSourceRef.current.onerror = (event: Event) => {
  //           console.error('EventSource failed:', event);
  //           if (eventSourceRef.current) {
  //             eventSourceRef.current.close();
  //           }
  //         };
  //       } catch (error) {
  //         console.error('Error initializing EventSource:', error);
  //       }
  //     };
  //     fetchSse();

  //     return () => {
  //       if (eventSourceRef.current) {
  //         eventSourceRef.current.close();
  //       }
  //     };
  //   }
  // }, [isAuthenticated, token, dispatch]);
  
  useEffect(() => {
    const eventSource = new EventSource('https://mvp-project.shop/api/notify/subscribe/test');
    console.log('start 1')

    eventSource.onopen = () => {
      console.log("Connection opened");
    };
    
    console.log('start 2')
    // eventSource.onmessage = (event) => {
    //   console.log("start3");
    //   // 데이터가 event.data에 포함되어 있으며, 이를 그대로 사용
    //   console.log("Received message:", event.data);
    //   // 데이터가 단순 문자열이라면 직접 사용
    //   setMessages((prev) => [...prev, event.data]);
    // };

    eventSource.onerror = (error) => {
      console.log("start4");
      console.error("EventSource failed:", error);
    };

    eventSource.addEventListener('업무수정', (event) => {
      fetchParkingData()
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