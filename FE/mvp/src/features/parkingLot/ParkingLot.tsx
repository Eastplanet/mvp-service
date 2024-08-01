import React from 'react';
import styles from './ParkingLot.module.css';
import parkingCar from '../../assets/images/icons/parking_car.png'

type CarLog = {
  carNumber: string;
  parkingDate: string;
  carState: string;
  entryTime: Date;
  exitTime?: Date;
  fee: number;
  imageBase64?: string;
};

type ParkingLotProps = {
  parkingData: CarLog[];
  onCarLogClick: (carLog: CarLog) => void;
};

const ParkingLot: React.FC<ParkingLotProps> = ({ parkingData, onCarLogClick }) => {
  return (
    <div className={styles.parkingLotContainer}>
      <div className={styles.parkingLot}>
        <div className={styles.side}>
          {parkingData.slice(0, 3).map((carLog, index) => (
            <div
              key={index}
              className={`${styles.parkingSpace} ${carLog.carNumber ? styles.occupied : styles.empty}`}
              onClick={() => onCarLogClick(carLog)}
            >
              {carLog.carNumber && <img src={parkingCar} alt={carLog.carNumber} />}
            </div>
          ))}
          <div className={styles.emptySpace}></div>
        </div>
        <div className={styles.road}></div>
        <div className={styles.side}>
          {parkingData.slice(3, 7).map((carLog, index) => (
            <div
              key={index}
              className={`${styles.parkingSpace} ${carLog.carNumber ? styles.occupied : styles.empty}`}
              onClick={() => onCarLogClick(carLog)}
            >
              {carLog.carNumber && <img src={parkingCar} alt={carLog.carNumber} />}
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default ParkingLot;
