import React, { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchParkingData } from '../main/mainSlice';
import styles from './CarInfoModal.module.css';
import api from '../../api/axios';
import ExitModal from './exit/ExitModal';
import MoveModal from './move/MoveModal';
import DiscountModal from './discount/DiscountModal';
import { RootState, AppDispatch } from '../../store/store';
import out from '../../assets/images/icons/out.png';
import move from '../../assets/images/icons/move.png';
import discount from '../../assets/images/icons/discount.png';

export interface CarLog {
  licensePlate: string;
  parkingDate: string;
  carState: string;
  entryTime: string;
  exitTime?: string;
  fee: number;
  lotState?: number;
  imageBase64?: string;
}

interface CarInfoModalProps {
  carLog: CarLog;
  onClose: () => void;
}

const CarInfoModal: React.FC<CarInfoModalProps> = ({ carLog, onClose }) => {
  const dispatch: AppDispatch = useDispatch();
  const [showExitModal, setShowExitModal] = useState(false);
  const [showMoveModal, setShowMoveModal] = useState(false);
  const [showDiscountModal, setShowDiscountModal] = useState(false);
  const currentParkedCars = useSelector((state: RootState) => state.main.currentParkedCars);

  const handleConfirmExit = () => {
    api.delete(`/parking-bot/exit/${carLog.licensePlate}`)
      .then(response => {
        setShowExitModal(false);
        dispatch(fetchParkingData());
        onClose();
      })
      .catch(error => {
        console.error('출차 중 오류 발생:', error);
        setShowExitModal(false);
      });
  };

  const handleApplyDiscount = (discount: number) => {
    console.log([carLog.licensePlate, discount])
    api.post('/parked-vehicle/discount', {
      licensePlate: carLog.licensePlate,
      discountAmount: discount
    })
    .then(response => {
      console.log('할인 적용 완료:', response.data);
      dispatch(fetchParkingData())
      // setShowDiscountModal(false);
      onClose();
    })
    .catch(error => {
      console.error('할인 적용 중 오류 발생:', error);
    });
  };

  if (!carLog) return null;

  const formatDateTime = (dateString: string) => {
    if (dateString === null) {
        return '';
    }
    const date = new Date(dateString);
    return date.toLocaleString('ko-KR', { dateStyle: 'short', timeStyle: 'short' });
  };

  const getStatusColor = (state: string) => {
    if (state === '주차 중' || state === '입차') {
      return 'blue';
    } else if (state === '대기 중' || state === '출차') {
      return 'black';
    } else if (state === '이동 중') {
      return 'red';
    } else {
      return 'black';
    }
  };

  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modalContent}>
        <button className={styles.closeButton} onClick={onClose} aria-label="Close">x</button>
        <div className={styles.modalBody}>
          <div className={styles.imageContainer}>
            {carLog.imageBase64 ? (
              <img 
                src={'data:image/jpeg;base64,'+carLog.imageBase64 || ''} 
                alt="Car" 
                className={styles.carImage} 
              />
            ) : (
              <div className={styles.noImage}>이미지 없음</div>
            )}
          </div>
          <div className={styles.infoAndButtons}>
            <div className={styles.detailsContainer}>
              <div className={styles.carDetails}>
                <div className={styles.detailRow}>
                  <div className={styles.dataName}>차량번호</div>
                  <div className={styles.dataValue}>{carLog.licensePlate}</div>
                </div>
                <div className={styles.detailRow}>
                  <div className={styles.dataName}>입차시간</div>
                  <div className={styles.dataValue}>{formatDateTime(carLog.entryTime)}</div>
                </div>
                <div className={styles.detailRow}>
                  <div className={styles.dataName}>출차시간</div>
                  <div className={styles.dataValue}>{carLog.exitTime ? formatDateTime(carLog.exitTime) : '-'}</div>
                </div>
                <div className={styles.detailRow}>
                  <div className={styles.dataName}>상태</div>
                  <div className={styles.dataValue} style={{ color: getStatusColor(carLog.carState) }}>
                    {carLog.carState}
                  </div>
                </div>
                <div className={styles.detailRow}>
                  <div className={styles.dataName}>요금</div>
                  <div className={styles.dataValue}>{carLog.fee ? carLog.fee.toLocaleString() : '-'}원</div>
                </div>
              </div>
            </div>
            <div className={styles.buttonContainer}>
              <button
                className={styles.modalButton}
                style={{
                  backgroundColor: carLog.carState === '주차 중' ? '#92E973' : '#d3d3d3',
                  cursor: carLog.carState === '주차 중' ? 'pointer' : 'not-allowed'
                }}
                onClick={() => setShowExitModal(true)}
                disabled={carLog.carState !== '주차 중'}
              >
                출차
                <img className={styles.imgOut} src={out} alt="출차" />
              </button>
              <button
                className={styles.modalButton}
                style={{
                  backgroundColor: carLog.carState === '주차 중' ? '#7391E9' : '#d3d3d3',
                  cursor: carLog.carState === '주차 중' ? 'pointer' : 'not-allowed'
                }}
                onClick={() => setShowMoveModal(true)}
                disabled={carLog.carState !== '주차 중'}
              >
                이동
                <img className={styles.imgMove} src={move} alt="이동" />
              </button>
              <button
                className={styles.modalButton}
                style={{
                  backgroundColor: carLog.carState === '주차 중' || carLog.carState === '이동 중' ? '#FFB74B' : '#d3d3d3',
                  cursor: carLog.carState === '주차 중' || carLog.carState === '이동 중' ? 'pointer' : 'not-allowed'
                }}
                onClick={() => setShowDiscountModal(true)}
                disabled={!(carLog.carState === '주차 중' || carLog.carState === '이동 중')}
              >
                할인
                <img className={styles.imgDiscount} src={discount} alt="할인" />
              </button>
            </div>
          </div>
        </div>
        {showExitModal && (
          <ExitModal
            licensePlate={carLog.licensePlate}
            onClose={() => setShowExitModal(false)}
            onConfirm={handleConfirmExit}
          />
        )}
        {showMoveModal && 
          <MoveModal
            carLog={carLog}
            currentParkedCars={currentParkedCars}
            onClose={() => setShowMoveModal(false)} 
          />
        }
        {showDiscountModal && (
          <DiscountModal
            carLog={carLog}
            onClose={() => setShowDiscountModal(false)}
            onApplyDiscount={handleApplyDiscount}
          />
        )}
      </div>
    </div>
  );
};

export default CarInfoModal;
