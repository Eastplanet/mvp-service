import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import Sidebar from '../sidebar/Sidebar';
import styles from './Chart.module.css';
import { fetchChartData } from './chartSlice';
import { RootState, AppDispatch } from '../../store/store';

const calculateComparison = (current: number, previous: number): string => {
  if (!previous || previous === 0) return 'N/A';
  const difference = current - previous;
  const percentage = (difference / previous) * 100;
  return `${difference > 0 ? '+' : ''}${percentage.toFixed(1)}%`;
};

const Chart: React.FC = () => {
  const dispatch = useDispatch<AppDispatch>();
  const { data, loading, error } = useSelector((state: RootState) => state.chart);

  useEffect(() => {
    dispatch(fetchChartData());
  }, [dispatch]);

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;
  if (!data) return null;

  const todayData = data.dailyRevenue && data.dailyRevenue.length > 0 ? data.dailyRevenue[data.dailyRevenue.length - 1] : null;
  const yesterdayData = data.dailyRevenue && data.dailyRevenue.length > 1 ? data.dailyRevenue[data.dailyRevenue.length - 2] : null;
  const thisMonthData = data.monthlyRevenue && data.monthlyRevenue.length > 0 ? data.monthlyRevenue[data.monthlyRevenue.length - 1] : null;
  const lastMonthData = data.monthlyRevenue && data.monthlyRevenue.length > 1 ? data.monthlyRevenue[data.monthlyRevenue.length - 2] : null;

  const dailyRevenueComparison = todayData && yesterdayData ? calculateComparison(todayData.revenue, yesterdayData.revenue) : 'N/A';
  const dailyParkingComparison = todayData && yesterdayData ? calculateComparison(todayData.parkingCount, yesterdayData.parkingCount) : 'N/A';
  const monthlyRevenueComparison = thisMonthData && lastMonthData ? calculateComparison(thisMonthData.revenue, lastMonthData.revenue) : 'N/A';
  const monthlyParkingComparison = thisMonthData && lastMonthData ? calculateComparison(thisMonthData.parkingCount, lastMonthData.parkingCount) : 'N/A';

  return (
    <div className={styles.chartPage}>
      <Sidebar />
      <div className={styles.page}>
        <div className={styles.mainContent}>
          <div className={styles.summaryContainer}>
            <div className={styles.summary}>
              <p className={styles.summaryTitle}>이번 달 매출</p>
              <h3 className={styles.summaryData}>{thisMonthData ? thisMonthData.revenue.toLocaleString() : 'N/A'} 원</h3>
              <p className={styles.summaryComparison}>지난 달 대비 <span className={styles.up}>{monthlyRevenueComparison}</span></p>
            </div>
            <div className={styles.summary}>
              <p className={styles.summaryTitle}>이번 달 주차량</p>
              <h3 className={styles.summaryData}>{thisMonthData ? thisMonthData.parkingCount.toLocaleString() : 'N/A'} 대</h3>
              <p className={styles.summaryComparison}>지난 달 대비 <span className={styles.down}>{monthlyParkingComparison}</span></p>
            </div>
            <div className={styles.summary}>
              <p className={styles.summaryTitle}>오늘 매출</p>
              <h3 className={styles.summaryData}>{todayData ? todayData.revenue.toLocaleString() : 'N/A'} 원</h3>
              <p className={styles.summaryComparison}>어제 대비 <span className={styles.up}>{dailyRevenueComparison}</span></p>
            </div>
            <div className={styles.summary}>
              <p className={styles.summaryTitle}>오늘 주차량</p>
              <h3 className={styles.summaryData}>{todayData ? todayData.parkingCount.toLocaleString() : 'N/A'} 대</h3>
              <p className={styles.summaryComparison}>어제 대비 <span className={styles.up}>{dailyParkingComparison}</span></p>
            </div>
          </div>
          <div className={styles.chartContainer}>
            {/* 여기에 차트 컴포넌트 또는 이미지를 추가하세요 */}
            <img src="path_to_chart_image" alt="차트" />
          </div>
        </div>
        <div className={styles.details}>
          <p>이번 달 총수익</p>
          <h3 className={styles.summaryData}>{thisMonthData ? thisMonthData.revenue.toLocaleString() : 'N/A'} 원</h3>
          <p>회원권 주차 매출</p>
          <h3 className={styles.summaryData}>{thisMonthData ? thisMonthData.parkingCount.toLocaleString() : 'N/A'} 대</h3>
          <p>평균 이용 시간</p>
          <h3 className={styles.summaryData}>{todayData ? todayData.revenue.toLocaleString() : 'N/A'} 원</h3>
          <p>평균 금액</p>
          <h3 className={styles.summaryData}>{todayData ? todayData.parkingCount.toLocaleString() : 'N/A'} 대</h3>
        </div>
      </div>
    </div>
  );
}

export default Chart;
