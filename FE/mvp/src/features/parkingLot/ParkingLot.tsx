import React from 'react';
import styles from './ParkingLot.module.css';
import parkingCar from '../../assets/images/icons/parking_car.png';

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
};

const ParkingLot: React.FC<ParkingLotProps> = ({ parkingData, onCarLogClick, mode }) => {
  const handleCarLogClick = (carLog: CarLog) => {
    if (mode === 'main' && onCarLogClick && carLog.licensePlate) {
      onCarLogClick(carLog);
    }
  };

  return (
    <div className={styles.parkingLotContainer}>
      <div className={styles.parkingLot}>
        <div className={`${styles.side} ${styles.leftSide}`}>
          {parkingData.slice(0, 3).map((carLog, index) => (
            <div
              key={index}
              className={styles.parkingSpace}
              onClick={() => handleCarLogClick(carLog)}
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
              key={index}
              className={styles.parkingSpace}
              onClick={() => handleCarLogClick(carLog)}
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
