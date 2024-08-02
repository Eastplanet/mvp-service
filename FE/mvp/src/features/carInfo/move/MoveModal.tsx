import React from 'react';
import styles from './MoveModal.module.css';
import { CarLog } from '../CarInfoModal';
import ParkingLot from '../../parkingLot/ParkingLot';

interface MoveModalProps {
  carLog: CarLog;
  currentParkedCars: CarLog[];
  onClose: () => void;
}

const MoveModal: React.FC<MoveModalProps> = ({ carLog, currentParkedCars, onClose }) => {
  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modalContent}>
        <button className={styles.closeButton} onClick={onClose} aria-label="Close">X</button>
        <h2>이동 정보</h2>
        <ParkingLot parkingData={currentParkedCars} selectedCarLog={carLog} mode='move'/>
      </div>
    </div>
  );
};

export default MoveModal;
