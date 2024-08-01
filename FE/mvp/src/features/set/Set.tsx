import React from 'react';
import Sidebar from '../sidebar/Sidebar';
import styles from './Set.module.css';

const Set: React.FC = () => {
  return (
    <div className={styles.container}>
      <Sidebar />

      <div className={styles.page}>
        <div className={styles.roundedBox}>
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

          {/* 시스템 중지 */}
          <div className={styles.section_bottom}>
            <p className={styles.title}>시스템 정지</p>
            <div className={styles.system_button}>
                <button className={styles.button_deactivate}>시스템 비활성화 </button>
                <button className={styles.button_activate}>시스템 활성화</button>
        
            </div>
          </div>
          <button className={styles.save}>저장</button>
        </div>
      </div>
    </div>
  );
};

export default Set;
