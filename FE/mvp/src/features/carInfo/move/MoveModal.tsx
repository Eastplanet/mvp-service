import React from 'react';
import styles from './MoveModal.module.css';
import { CarLog } from '../CarInfoModal';
import ParkingLot from '../../parkingLot/ParkingLot';
import axios from 'axios';
import { useDispatch } from 'react-redux';
import { fetchParkingData } from '../../main/mainSlice';
import { AppDispatch } from '../../../store/store';


interface MoveModalProps {
  carLog: CarLog;
  currentParkedCars: CarLog[];
  onClose: () => void;
}

const MoveModal: React.FC<MoveModalProps> = ({ carLog, currentParkedCars, onClose }) => {
  const dispatch = useDispatch<AppDispatch>();

  const handleEmptySlotClick = async (slotId: number) => {
    try {
      const response = await axios.post('https://mvp-project.shop/api/parking-bot/move-vehicle', {
        licensePlate: carLog.licensePlate,
        endSpot: slotId,
      });
      console.log('이동 요청 성공:', response.data);
      dispatch(fetchParkingData());
      onClose();
    } catch (error) {
      console.error('이동 중 오류 발생:', error);
    }
  };
  
  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modalContent}>
        <button className={styles.closeButton} onClick={onClose} aria-label="Close">X</button>
        <h2>이동 정보</h2>
        <ParkingLot parkingData={currentParkedCars} selectedCarLog={carLog} mode='move' onEmptySlotClick={handleEmptySlotClick}/>
      </div>
    </div>
  );
};

export default MoveModal;
