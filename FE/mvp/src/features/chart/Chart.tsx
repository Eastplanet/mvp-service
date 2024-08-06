import React from 'react';
import Sidebar from '../sidebar/Sidebar';
import styles from './Chart.module.css';

const Chart = () => {
  return (
    <div className={styles.chartPage}>
      <Sidebar />
      <div className={styles.page}>
        <div className={styles.mainContent}>
          <div className={styles.summaryContainer}>
            <div className={styles.summary}>
              <p className={styles.summaryTitle}>당월 매출</p>
              <h3 className={styles.summaryData}>5,500,000 원</h3>
              <p className={styles.summaryComparison}>전월 대비
                <span className={styles.up}>+10.0%</span>
                <span className={styles.arrowUp}></span>
              </p>
            </div>
            <div className={styles.summary}>
              <p className={styles.summaryTitle}>당월 주차량</p>
              <h3 className={styles.summaryData}>2,000 대</h3>
              <p className={styles.summaryComparison}>전월 대비
                <span className={styles.down}>-2.5%</span>
                <span className={styles.arrowDown}></span>
              </p>
            </div>
            <div className={styles.summary}>
              <p className={styles.summaryTitle}>오늘 매출</p>
              <h3 className={styles.summaryData}>200,000 원</h3>
              <p className={styles.summaryComparison}>어제 대비
                <span className={styles.up}>+8.8%</span>
                <span className={styles.arrowUp}></span>  
              </p>
            </div>
            <div className={styles.summary}>
              <p className={styles.summaryTitle}>오늘 주차량</p>
              <h3 className={styles.summaryData}>80 대</h3>
              <p className={styles.summaryComparison}>어제 대비
                <span className={styles.up}>+7.0%</span>
                <span className={styles.arrowUp}></span>
              </p>
            </div>
          </div>
          <div className={styles.chartContainer}>
            {/* 여기에 차트 컴포넌트 또는 이미지를 추가하세요 */}
            <img src="path_to_chart_image" alt="차트" />
          </div>
        </div>
        <div className={styles.details}>
          <p>월 주차 회원 수</p>
          <h3>30 대</h3>
          <p>월 주차 매출</p>
          <h3>6,000,000 원</h3>
          <p>지난달 총 매출</p>
          <h3>10,000,000 원</h3>
          <p>총 매출</p>
          <h3 className={styles.detailsTotal}>11,500,000 원</h3>
          <span className={styles.totalUp}>+15.0%
            <span className={styles.arrowUp}></span>
          </span>
        </div>
      </div>
    </div>
  );
}

export default Chart;

