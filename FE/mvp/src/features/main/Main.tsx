import React, { useState, useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { RootState, AppDispatch } from '../../store/store'; // 올바른 경로 확인
import { fetchParkingData, fetchSearchData, setSearchTerm, setStartDate, setEndDate } from './mainSlice';
import Sidebar from '../sidebar/Sidebar';
import styles from './Main.module.css';
import searchIcon from '../../assets/images/icons/searchIcon.png'
import CarInfo from '../carInfo/CarInfoModal';

type CarLog = {
  carNumber: string;
  parkingDate: string;
  carState: string;
  entryTime: Date;
  exitTime?: Date;
  fee: number;
  imageBase64?: string;
};

const Main: React.FC = () => {
  const dispatch: AppDispatch = useDispatch();
  const { todayIn, todayOut, todayIncome, searchTerm, startDate, endDate, searchData } = useSelector((state: RootState) => state.main);

  const [selectedCarLog, setSelectedCarLog] = useState<CarLog | null>(null);

  useEffect(() => {
    dispatch(fetchParkingData());
  }, [dispatch]);

  const handleSearch = () => {
    dispatch(fetchSearchData({ searchTerm, startDate, endDate }));
  };

  const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === 'Enter') {
      handleSearch();
    }
  };

  const formatDate = (dateString: string) => {
    const year = dateString.substring(0, 4);
    const month = dateString.substring(4, 6);
    const day = dateString.substring(6, 8);
    return `${year}.${month}.${day}`;
  };

  const getCarStateClass = (state: string) => {
    switch(state) {
      case '주차 중':
        return styles.carStateParked;
      case '출차 완료':
        return styles.carStateExited;
      case '이동 중':
        return styles.carStateMoving;
      default:
        return '';
    }
  };

  const handleCarLogClick = (carLog: CarLog) => {
    setSelectedCarLog(carLog);
  };

  return (
    <div className={styles.main}>
      <Sidebar />
      <div className={styles.mainContent}>
        <div className={styles.parkingInfo}>
          <div className={styles.parkingMap}>
            {/* 주차장 지도 */}
          </div>
          <div className={styles.dataTables}>
            <div className={styles.todayIn}>
              <p className={styles.dataName}>당일 입차대수</p>
              <p className={styles.dataValue}>{todayIn}대</p>
            </div>
            <div className={styles.todayOut}>
              <p className={styles.dataName}>당일 출차대수</p>
              <p className={styles.dataValue}>{todayOut}대</p>
            </div>
            <div className={styles.todayIncome}>
              <p className={styles.dataName}>당일 매출액</p>
              <p className={styles.dataValue}>{todayIncome}원</p>
            </div>
          </div>
        </div>
        <div className={styles.searchSection}>
          <div className={styles.searchArea}>
            <p>차량 검색</p>
            <div className={styles.searchBar}>
              <input 
                className={styles.searchInput} 
                type="text" 
                placeholder="Search Car Number" 
                value={searchTerm}
                onChange={(e) => dispatch(setSearchTerm(e.target.value))}
                onKeyDown={handleKeyDown}
                maxLength={10}
              />
              <button className={styles.searchButton} onClick={handleSearch}>
                <img src={searchIcon} alt="Search" className={styles.icon} />
              </button>
            </div>
            <div className={styles.searchDate}>
              <input 
                className={styles.dateInput} 
                type="date" 
                value={startDate}
                onChange={(e) => dispatch(setStartDate(e.target.value))}
              />
              <input 
                className={styles.dateInput} 
                type="date" 
                value={endDate}
                onChange={(e) => dispatch(setEndDate(e.target.value))}
              />
            </div>
          </div>
          {searchData.length > 0 && (
            <div className={styles.searchData}>
              <ul>
              {searchData.map((carLog: CarLog, index: number) => (
                <li key={index} onClick={() => handleCarLogClick(carLog)}>
                  <div className={styles.leftData}>
                    <div className={styles.carNumber}>{carLog.carNumber}</div>
                    <div className={styles.parkingDate}>{formatDate(carLog.parkingDate)}</div>
                  </div>
                  <div className={getCarStateClass(carLog.carState)}>
                    {carLog.carState}
                  </div>
                </li>
              ))}
              </ul>
            </div>
          )}
        </div>
      </div>
      {selectedCarLog && (
        <CarInfo carLog={selectedCarLog} onClose={() => setSelectedCarLog(null)} />
      )}
    </div>
  );
};

export default Main;
