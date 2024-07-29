// src/features/auth/Login.tsx
import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
// import { login } from './authActions';
import { AppDispatch } from '../../store/store';
import styles from './Login.module.css';
import logo from '../../assets/images/logos/logo.png'
import { loginSuccess } from './authSlice';

const Login: React.FC = () => {
  const dispatch = useDispatch<AppDispatch>();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleLogin = () => {
    // dispatch(login({ email, password }));
    dispatch(loginSuccess());
  };

  return (
    <div className={styles.container}>
      <div className={styles.left}>
        <img src={logo} alt="MVP Logo" className={styles.logo} />
        <div className={styles.form}>
          <input
            type="text"
            placeholder="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            className={styles.input}
          />
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className={styles.input}
          />
          <button onClick={handleLogin} className={styles.button}>
            Login
          </button>
        </div>
      </div>
      <div className={styles.right}></div>
    </div>
  );
};

export default Login;
