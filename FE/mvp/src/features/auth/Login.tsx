import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { AppDispatch } from '../../store/store';
import styles from './Login.module.css';
import logo from '../../assets/images/logos/logo.png'
import { login } from './authSlice';
import { RootState } from '../../store/store';

const Login: React.FC = () => {
  const dispatch = useDispatch<AppDispatch>();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const { error, loading } = useSelector((state: RootState) => state.auth);

  const handleLogin = () => {
    dispatch(login({ email, password }));
    // dispatch(loginSuccess());
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
          {loading && <p>Loading...</p>}
          {error && <p className={styles.error}>{error}</p>}
        </div>
      </div>
      <div className={styles.right}></div>
    </div>
  );
};

export default Login;
