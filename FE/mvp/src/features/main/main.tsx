import React from 'react';
import Sidebar from '../sidebar/Sidebar';
import styles from './Main.module.css';

const Main: React.FC = () => {
  return (
    <div className={styles.main}>
      <Sidebar />
      <div className={styles.mainContent}>
        <div className={styles.parkingInfo}>
          {/* 주차장 시각화 및 기타 정보 */}
        </div>
        <div className={styles.searchBar}>
          <input type="text" placeholder="123가 4568" />
          <input type="date" />
          <input type="date" />
        </div>
        <div className={styles.dataTables}>
          {/* 데이터 테이블 및 통계 */}
        </div>
      </div>
    </div>
  );
};

export default Main;
