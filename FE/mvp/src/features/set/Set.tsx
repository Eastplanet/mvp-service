import React from 'react';
import Sidebar from '../sidebar/Sidebar';
import styles from './Set.module.css';

const Set: React.FC = () => {
  return (
    <div className={styles.container}>
      <Sidebar />

      <div className={styles.page}>
        <div className={styles.roundedBox}>
          {/* 관리 운영 시간 */}
          <div className={styles.section}>
            <p className={styles.title}>관리 운영 시간</p>
            <div className={styles.timeSetting}>
              <div className={styles.timeRow}>
                <p>주일</p>
                <input type="time" className={styles.timeInput} /> ~
                <input type="time" className={styles.timeInput} />
              </div>
              <div className={styles.timeRow}>
                <p>주말 및 공휴일</p>
                <input type="time" className={styles.timeInput} /> ~
                <input type="time" className={styles.timeInput} />
              </div>
            </div>
          </div>

          {/* 시간별 금액 설정 */}
          <div className={styles.section}>
            <p className={styles.title}>시간별 금액 설정</p>
            <div className={styles.rateSetting}>
              <div className={styles.rateRow}>
                <span>기본금:</span>
                <input type="number" className={styles.rateInput} />분
                <input type="number" className={styles.rateInput} />원
                <p className={styles.nocosttime}>10분이하 정차 무료</p>
              </div>
              <div className={styles.rateRow}>
                <span>추가금:</span>
                <input type="number" className={styles.rateInput} />분당
                <input type="number" className={styles.rateInput} />원
              </div>
            </div>
          </div>

          {/* 주차권 금액 */}
          <div className={styles.section}>
            <p className={styles.title}>주차권 금액</p>
            <div className={styles.ticketRates}>
              <div className={styles.ticketRow}>
                <span>1일권:</span>
                <input type="number" className={styles.rateInput} />원
              </div>
              <div className={styles.ticketRow}>
                <span>1주일권:</span>
                <input type="number" className={styles.rateInput} />원
              </div>
              <div className={styles.ticketRow}>
                <span>1달권:</span>
                <input type="number" className={styles.rateInput} />원
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Set;
