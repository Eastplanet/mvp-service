import React, { useState, useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { RootState, AppDispatch } from '../../store/store';
import { fetchParkingData, fetchSearchData, setLicensePlate, setStartDate, setEndDate } from './mainSlice';
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
  lotState?: number;
  imageBase64?: string;
};

const Main: React.FC = () => {
  const dispatch: AppDispatch = useDispatch();
  const { todayIn, todayOut, todayIncome, licensePlate, startDate, endDate, searchData, currentParkedCars } = useSelector((state: RootState) => state.main);
  const [selectedCarLog, setSelectedCarLog] = useState<CarLog | null>(null);
  const [searchPerformed, setSearchPerformed] = useState(false);
  const [activeTab, setActiveTab] = useState<'currentStatus' | 'parkingLog'>('currentStatus');
  const [filteredCurrentParkedCars, setFilteredCurrentParkedCars] = useState<CarLog[]>([]);

  useEffect(() => {
    dispatch(fetchParkingData());
  }, [dispatch]);

  useEffect(() => {
  }, [searchData, currentParkedCars]);

  useEffect(() => {
    if (activeTab === 'parkingLog') {
      dispatch(fetchSearchData({ licensePlate, startDate, endDate }));
      setSearchPerformed(true);
    }
  }, [activeTab, dispatch, licensePlate, startDate, endDate]);

  const handleSearch = () => {
    if (activeTab === 'currentStatus') {
      const filteredCars = currentParkedCars.filter(car =>
        car.licensePlate && car.licensePlate.includes(licensePlate)
      );
      setFilteredCurrentParkedCars(filteredCars);
    } else {
      dispatch(fetchSearchData({ licensePlate, startDate, endDate }));
      setSearchPerformed(true);
    }
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
    if (state === '주차 중' || state === '입차') {
      return styles.carStateParked;
    } else if (state === '대기 중' || state === '출차') {
      return styles.carStateExited;
    } else if (state === '이동 중') {
      return styles.carStateMoving;
    } else {
      return '';
    }
  };

  const handleCarLogClick = (carLog: CarLog) => {
    setSelectedCarLog(carLog);
  };

  const carLogsToDisplay = activeTab === 'currentStatus' 
  ? (filteredCurrentParkedCars.length > 0 ? filteredCurrentParkedCars : currentParkedCars)
  : (searchPerformed && searchData.length === 0 ? [] : searchData);

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
            <div className={styles.tabContainer}>
              <p
                className={`${styles.tab} ${activeTab === 'currentStatus' ? styles.activeTab : ''}`}
                onClick={() => setActiveTab('currentStatus')}>
                현재 주차 현황
              </p>
              <p
                className={`${styles.tab} ${activeTab === 'parkingLog' ? styles.activeTab : ''}`}
                onClick={() => {
                  setActiveTab('parkingLog');
                  handleSearch();
                }}
              >
                주차 로그
              </p>
            </div>
            <div className={styles.searchBar}>
              <input 
                className={styles.searchInput} 
                type="text" 
                placeholder="Search Car Number" 
                value={licensePlate}
                onChange={(e) => dispatch(setLicensePlate(e.target.value))}
                onKeyDown={handleKeyDown}
                maxLength={10}
              />
              <button className={styles.searchButton} onClick={handleSearch}>
                <img src={searchIcon} alt="Search" className={styles.icon} />
              </button>
            </div>
            {activeTab === 'parkingLog' && (
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
            )}
          </div>
          {searchPerformed && searchData.length === 0 && (
            <div className={styles.noResults}>
              <p>검색 결과가 없습니다.</p>
            </div>
          )}
          {carLogsToDisplay.length > 0 && (
            <div className={`${styles.searchData} ${activeTab === 'currentStatus' ? styles.now : styles.log}`}>
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
