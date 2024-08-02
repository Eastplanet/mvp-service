import React from 'react';
import styles from './ParkingLot.module.css';
import parkingCar from '../../assets/images/icons/parking_car.png';
import axios from 'axios';

type CarLog = {
  licensePlate: string;
  parkingDate: string;
  carState: string;
  entryTime: string;
  exitTime?: string;
  fee: number;
  imageBase64?: string;
};

type ParkingLotProps = {
  parkingData: CarLog[];
  onCarLogClick?: (carLog: CarLog) => void;
  mode: 'main' | 'move';
  selectedCarLog?: CarLog;
};

const ParkingLot: React.FC<ParkingLotProps> = ({ parkingData, onCarLogClick, selectedCarLog, mode }) => {
  const handleCarLogClick = (carLog: CarLog) => {
    if (mode === 'main' && onCarLogClick && carLog.licensePlate) {
      onCarLogClick(carLog);
    }
  };

  const handleEmptySpaceClick = (index: number) => {
    if (mode === 'move' && selectedCarLog) {
      const requestData = {
        licensePlate: selectedCarLog.licensePlate,
        endSpot: index
      };
      console.log('Request Data:', requestData);
      axios.post('http://mvp-project.shop:8081/parking-bot/move-vehicle', requestData)
        .then(response => {
          console.log('이동 요청 성공:', response);
        })
        .catch(error => {
          console.error('이동 요청 실패:', error);
        });
    }
  };

  return (
    <div className={styles.parkingLotContainer}>
      <div className={styles.parkingLot}>
        <div className={`${styles.side} ${styles.leftSide}`}>
          {parkingData.slice(0, 3).map((carLog, index) => (
            <div
              key={index + 1}
              className={`${styles.parkingSpace} ${mode === 'move' && selectedCarLog && carLog.licensePlate === selectedCarLog.licensePlate ? styles.selectedParkingSpace : ''}`}
              onClick={() => carLog.licensePlate ? handleCarLogClick(carLog) : handleEmptySpaceClick(index + 1)}
            >
              {carLog.licensePlate && <img src={parkingCar} alt={carLog.licensePlate} className={styles.rotatedCar} />}
            </div>
          ))}
          <div className={`${styles.parkingSpace} ${styles.emptySpace}`}></div>
        </div>
        <div className={styles.road}></div>
        <div className={`${styles.side} ${styles.rightSide}`}>
          {parkingData.slice(3, 7).map((carLog, index) => (
            <div
              key={index + 4}
              className={`${styles.parkingSpace} ${mode === 'move' && selectedCarLog && carLog.licensePlate === selectedCarLog.licensePlate ? styles.selectedParkingSpace : ''}`}
              onClick={() => carLog.licensePlate ? handleCarLogClick(carLog) : handleEmptySpaceClick(index + 4)}
            >
              {carLog.licensePlate && <img src={parkingCar} alt={carLog.licensePlate} />}
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default ParkingLot;
