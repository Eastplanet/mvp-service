import React from 'react';
import Sidebar from '../sidebar/Sidebar';
import styles from './Set.module.css';

const Set: React.FC = () => {
  return (
    <div className={styles.container}>
      <div className={styles.sidebarWrapper}>
        <Sidebar />
      </div>
      <div className={styles.page}>
        <div className={styles.runtime}>
          <p className={styles.title}>관리 운영 시간</p>
          <p>주일</p>
          <div className={styles.timeInputs}>
            <input type="time" className={styles.timeInput} /> ~
            <input type="time" className={styles.timeInput} />
          </div>
        </div>
      </div>
    </div>
  );
};

export default Set;
