

import React from 'react';
import Sidebar from '../sidebar/Sidebar';
import styles from './Chart.module.css';

const Set: React.FC = () => {
  return (
    <div className={styles.container}>
      <Sidebar />
      <div className={styles.datachart}>
        <p className={styles.title}>Chart</p>
        <div className={styles.datas}>
          <div className={styles.data}>
            <div className={styles.data_left}>
              <span>이번 달 매출</span>
              <span className={styles.small_font}>회원 제외</span>
            </div>
            <div className={styles.data_right}>
              <span>1000000</span>
              <span>(천)원</span>
            </div>
            <div className={styles.data_left}>
              <img src="" alt="화살표" />
              <span>1%</span> 
              <span className={styles.small_font}>지난 달 대비</span>
            </div>
          </div>
          <div className={styles.data}>
          <div className={styles.data_left}>
              <span>이번 달 주차량</span>
              <span className={styles.small_font}>회원 제외</span>
            </div>
            <div className={styles.data_right}>
              <span>1000</span>
              <span>대</span>
            </div>
            <div className={styles.data_left}>
              <img src="" alt="화살표" />
              <span>1%</span> 
              <span className={styles.small_font}>지난 달 대비</span>
            </div>
          </div>
          <div className={styles.data}>
          <div className={styles.data_left}>
              <span>오늘 매출</span>
              <span className={styles.small_font}>회원 제외</span>
            </div>
            <div className={styles.data_right}>
              <span>1000000</span>
              <span>(천)원</span>
            </div>
            <div className={styles.data_left}>
              <img src="" alt="화살표" />
              <span>1%</span> 
              <span className={styles.small_font}>지난 달 대비</span>
            </div>
          </div>
          <div className={styles.data}>
          <div className={styles.data_left}>
              <span>오늘 주차량</span>
              <span className={styles.small_font}>회원 제외</span>
            </div>
            <div className={styles.data_right}>
              <span>100</span>
              <span>대</span>
            </div>
            <div className={styles.data_left}>
              <img src="" alt="화살표" />
              <span>1%</span> 
              <span className={styles.small_font}>지난 달 대비</span>
            </div>
          </div>
        </div>
        <div className={styles.chart}>
          가운데 아랫 부분
        </div>
      </div>
      <div className={styles.monthdata}>
        오른쪽
      </div>
      
    </div>
  );
};

export default Set;
