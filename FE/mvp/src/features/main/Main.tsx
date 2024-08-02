import React, { useState, useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { RootState, AppDispatch } from '../../store/store';
import { fetchParkingData, fetchSearchData, setlicensePlate, setStartDate, setEndDate } from './mainSlice';
import Sidebar from '../sidebar/Sidebar';
import styles from './Main.module.css';
import searchIcon from '../../assets/images/icons/searchIcon.png'
import CarInfo from '../carInfo/CarInfoModal';
import ParkingLot from '../parkingLot/ParkingLot';

type CarLog = {
  licensePlate: string;
  parkingDate: string;
  carState: string;
  entryTime: string;
  exitTime?: string;
  fee: number;
  imageBase64?: string;
};

const Main: React.FC = () => {
  const dispatch: AppDispatch = useDispatch();
  const { todayIn, todayOut, todayIncome, licensePlate, startDate, endDate, searchData, currentParkedCars } = useSelector((state: RootState) => state.main);
  const [selectedCarLog, setSelectedCarLog] = useState<CarLog | null>(null);
  const [searchPerformed, setSearchPerformed] = useState(false);

  useEffect(() => {
    dispatch(fetchParkingData());
  }, [dispatch]);

  useEffect(() => {
  }, [searchData, currentParkedCars]);

  const handleSearch = () => {
    dispatch(fetchSearchData({ licensePlate, startDate, endDate }));
    setSearchPerformed(true);
  };

  const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === 'Enter') {
      handleSearch();
    }
  };

  const formatDate = (dateString: string) => {
    if (!dateString) {
      return '';
    }
    
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    
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

  const carLogsToDisplay = searchPerformed && searchData.length === 0 ? [] : (searchData.length > 0 ? searchData : currentParkedCars);

  return (
    <div className={styles.main}>
      <Sidebar />
      <div className={styles.mainContent}>
        <div className={styles.parkingInfo}>
          <div className={styles.parkingMap}>
            <ParkingLot parkingData={currentParkedCars} onCarLogClick={handleCarLogClick} mode='main'/>
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
                value={licensePlate}
                onChange={(e) => dispatch(setlicensePlate(e.target.value))}
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
          {searchPerformed && searchData.length === 0 && (
            <div className={styles.noResults}>
              <p>검색 결과가 없습니다.</p>
            </div>
          )}
          {carLogsToDisplay.length > 0 && (
            <div className={styles.searchData}>
              <ul>
                {carLogsToDisplay
                  .filter(carLog => carLog.licensePlate)
                  .map((carLog: CarLog, index: number) => (
                    <li key={index} onClick={() => handleCarLogClick(carLog)}>
                      <div className={styles.leftData}>
                        <div className={styles.licensePlate}>{carLog.licensePlate}</div>
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
