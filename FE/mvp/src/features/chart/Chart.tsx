import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import Sidebar from '../sidebar/Sidebar';
import styles from './Chart.module.css';
import { fetchChartData } from './chartSlice';
import { RootState, AppDispatch } from '../../store/store';
import Graph from './graph/Graph';

const calculateComparison = (current: number, previous: number): { comparison: string, status: string } => {
  if (!previous || previous === 0) return { comparison: 'N/A', status: 'same' };
  const difference = current - previous;
  const percentage = (difference / previous) * 100;
  let status = 'same';
  if (percentage > 0) {
    status = 'up';
  } else if (percentage < 0) {
    status = 'down';
  }
  return {
    comparison: `${difference > 0 ? '+' : ''}${percentage.toFixed(1)}%`,
    status
  };
};

const Chart: React.FC = () => {
  const dispatch = useDispatch<AppDispatch>();
  const { data, loading, error } = useSelector((state: RootState) => state.chart);

  useEffect(() => {
    dispatch(fetchChartData());
  }, [dispatch]);

  useEffect(() => {
    console.log("Chart state:", { data, loading, error });
  }, [data, loading, error]);

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;
  if (!data) return null;
  
  const todayData = data.dailyRevenues && data.dailyRevenues.length > 0 ? data.dailyRevenues[data.dailyRevenues.length - 1] : null;
  const yesterdayData = data.dailyRevenues && data.dailyRevenues.length > 1 ? data.dailyRevenues[data.dailyRevenues.length - 2] : null;
  const thisMonthData = data.monthlyRevenues && data.monthlyRevenues.length > 0 ? data.monthlyRevenues[data.monthlyRevenues.length - 1] : null;
  const lastMonthData = data.monthlyRevenues && data.monthlyRevenues.length > 1 ? data.monthlyRevenues[data.monthlyRevenues.length - 2] : null;

  const dailyRevenuesComparison = todayData && yesterdayData ? calculateComparison(todayData.revenue, yesterdayData.revenue) : { comparison: 'N/A', status: 'same' };
  const dailyParkingComparison = todayData && yesterdayData ? calculateComparison(todayData.parkingCount, yesterdayData.parkingCount) : { comparison: 'N/A', status: 'same' };
  const monthlyRevenuesComparison = thisMonthData && lastMonthData ? calculateComparison(thisMonthData.revenue, lastMonthData.revenue) : { comparison: 'N/A', status: 'same' };
  const monthlyParkingComparison = thisMonthData && lastMonthData ? calculateComparison(thisMonthData.parkingCount, lastMonthData.parkingCount) : { comparison: 'N/A', status: 'same' };


  return (
    <div className={styles.chartPage}>
      <Sidebar />
      <div className={styles.page}>
        <div className={styles.mainContent}>
        <div className={styles.summaryContainer}>
          <div className={styles.summary}>
              <p className={styles.summaryTitle}>당월 매출</p>
              <h3 className={styles.summaryData}>{thisMonthData ? thisMonthData.revenue.toLocaleString() : 'N/A'} 원</h3>
              <p className={styles.summaryComparison}>전월 대비 <span className={styles[monthlyRevenuesComparison.status]}>{monthlyRevenuesComparison.comparison}</span></p>
            </div>
            <div className={styles.summary}>
              <p className={styles.summaryTitle}>당월 주차량</p>
              <h3 className={styles.summaryData}>{thisMonthData ? thisMonthData.parkingCount.toLocaleString() : 'N/A'} 대</h3>
              <p className={styles.summaryComparison}>전월 대비 <span className={styles[monthlyParkingComparison.status]}>{monthlyParkingComparison.comparison}</span></p>
            </div>
            <div className={styles.summary}>
              <p className={styles.summaryTitle}>당일 매출</p>
              <h3 className={styles.summaryData}>{todayData ? todayData.revenue.toLocaleString() : 'N/A'} 원</h3>
              <p className={styles.summaryComparison}>전일 대비 <span className={styles[dailyRevenuesComparison.status]}>{dailyRevenuesComparison.comparison}</span></p>
            </div>
            <div className={styles.summary}>
              <p className={styles.summaryTitle}>당일 주차량</p>
              <h3 className={styles.summaryData}>{todayData ? todayData.parkingCount.toLocaleString() : 'N/A'} 대</h3>
              <p className={styles.summaryComparison}>전일 대비 <span className={styles[dailyParkingComparison.status]}>{dailyParkingComparison.comparison}</span></p>
            </div>
          </div>
          <div className={styles.chartContainer}>
            <Graph dailyRevenues={data.dailyRevenues} monthlyRevenues={data.monthlyRevenues} />
          </div>
        </div>
        <div className={styles.details}>
        <p>이번 달 총수익</p>
        <h3 className={styles.summaryData}>{data.totalRevenue.toLocaleString()} 원</h3>
        <p>회원권 주차 매출</p>
        <h3 className={styles.summaryData}>{data.totalMembershipsRevenue.toLocaleString()} 원</h3>
        <p>평균 이용 시간</p>
        <h3 className={styles.summaryData}>{data.usingTimeAvg.toLocaleString()} 분</h3>
        <p>평균 금액</p>
        <h3 className={styles.summaryData}>{data.revenueAvg.toLocaleString()} 원</h3>
        </div>
      </div>
    </div>
  );
}

export default Chart;
