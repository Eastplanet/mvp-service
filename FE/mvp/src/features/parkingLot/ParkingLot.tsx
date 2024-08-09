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
  lotState?: number;
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
          {parkingData.slice(0, 3).map((carLog, index) => {
            const isMoveMode = mode === 'move';
            const isSelectedCar = isMoveMode && selectedCarLog && carLog.licensePlate === selectedCarLog.licensePlate;
            const isUnavailable = carLog.lotState === 2;
            const hasCar = !!carLog.licensePlate;
            const isDiagonal = isMoveMode && (hasCar || isUnavailable) && selectedCarLog && carLog.licensePlate !== selectedCarLog.licensePlate;

            const classNames = [
              styles.parkingSpace,
              isSelectedCar ? styles.selectedParkingSpace : '',
              isUnavailable ? styles.unavailableParkingSpace : styles.clickableParkingSpace,
              isDiagonal ? styles.diagonal : ''
            ].join(' ');

            return (
              <div
                key={index + 1}
                className={classNames}
                onClick={() => {
                  if (carLog.lotState !== 2) handleCarLogClick(carLog, index + 1);
                }}
              >
                {carLog.licensePlate && <img src={parkingCar} alt={carLog.licensePlate} className={styles.rotatedCar} />}
                {carLog.lotState === 2 && <span className={styles.moving}>이동 중</span>}
              </div>
            );
          })}
          <div
            className={`${styles.parkingSpace} ${styles.emptySpace} ${mode === 'move' ? styles.clickableParkingSpace : ''}`}
            onClick={() => {
              if (mode === 'move') handleSlotClick(4);
            }}
          ></div>
        </div>
        <div className={styles.road}></div>
        <div className={`${styles.side} ${styles.rightSide}`}>
          {parkingData.slice(3, 7).map((carLog, index) => {
            const isMoveMode = mode === 'move';
            const isSelectedCar = isMoveMode && selectedCarLog && carLog.licensePlate === selectedCarLog.licensePlate;
            const isUnavailable = carLog.lotState === 2;
            const hasCar = !!carLog.licensePlate;
            const isDiagonal = isMoveMode && (hasCar || isUnavailable) && selectedCarLog && carLog.licensePlate !== selectedCarLog.licensePlate;

            const classNames = [
              styles.parkingSpace,
              isSelectedCar ? styles.selectedParkingSpace : '',
              isUnavailable ? styles.unavailableParkingSpace : styles.clickableParkingSpace,
              isDiagonal ? styles.diagonal : ''
            ].join(' ');

            return (
              <div
                key={index + 4}
                className={classNames}
                onClick={() => {
                  if (carLog.lotState !== 2) handleCarLogClick(carLog, index + 4);
                }}
              >
                {carLog.licensePlate && <img src={parkingCar} alt={carLog.licensePlate} />}
                {carLog.lotState === 2 && <span className={styles.moving}>이동 중</span>}
              </div>
            );
          })}
        </div>
      </div>
    </div>
  );
};

export default ParkingLot;
