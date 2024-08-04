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
  selectedCarLog?: CarLog;
  onEmptySlotClick?: (slotId: number) => void;
};

const ParkingLot: React.FC<ParkingLotProps> = ({ parkingData, onCarLogClick, selectedCarLog, mode, onEmptySlotClick }) => {
  const handleSlotClick = (slotId: number) => {
    if (mode === 'move' && onEmptySlotClick) {
      onEmptySlotClick(slotId);
    }
  };

  const handleCarLogClick = (carLog: CarLog, slotId: number) => {
    if (carLog.licensePlate) {
      if (onCarLogClick) {
        onCarLogClick(carLog);
      }
    } else if (mode === 'move' && onEmptySlotClick) {
      onEmptySlotClick(slotId);
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
              onClick={() => handleCarLogClick(carLog, index + 1)}
            >
              {carLog.licensePlate && <img src={parkingCar} alt={carLog.licensePlate} className={styles.rotatedCar} />}
            </div>
          ))}
          <div
            className={`${styles.parkingSpace} ${styles.emptySpace}`}
            onClick={() => {
              if (mode === 'move') handleSlotClick(4);
            }}
          ></div>
        </div>
        <div className={styles.road}></div>
        <div className={`${styles.side} ${styles.rightSide}`}>
          {parkingData.slice(3, 7).map((carLog, index) => (
            <div
              key={index + 4}
              className={`${styles.parkingSpace} ${mode === 'move' && selectedCarLog && carLog.licensePlate === selectedCarLog.licensePlate ? styles.selectedParkingSpace : ''}`}
              onClick={() => handleCarLogClick(carLog, index + 4)}
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
