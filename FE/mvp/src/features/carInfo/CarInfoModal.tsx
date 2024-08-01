import React, { useState } from 'react';
import styles from './CarInfoModal.module.css';
import axios from 'axios';
import ExitModal from './exit/ExitModal';
import MoveModal from './move/MoveModal';
import DiscountModal from './discount/DiscountModal';

export interface CarLog {
  carNumber: string;
  parkingDate: string;
  carState: string;
  entryTime: Date;
  exitTime?: Date;
  fee: number;
  imageBase64?: string;
}

interface CarInfoModalProps {
  carLog: CarLog;
  onClose: () => void;
}

const CarInfoModal: React.FC<CarInfoModalProps> = ({ carLog, onClose }) => {
  const [showExitModal, setShowExitModal] = useState(false);

  const handleConfirmExit = () => {
    // axios.post('/api/exit', {
    //   carNumber: carLog.carNumber
    // })
    // .then(response => {
    //   console.log('출차 완료:', response.data);
    //   setShowExitModal(false);
    // })
    // .catch(error => {
    //   console.error('출차 중 오류 발생:', error);
    // });
    setShowExitModal(false);
  };

  const [showMoveModal, setShowMoveModal] = useState(false);
  const [showDiscountModal, setShowDiscountModal] = useState(false);

  const handleApplyDiscount = (discount: number) => {
    axios.post('/api/apply-discount', {
      carNumber: carLog.carNumber,
      discount
    })
    .then(response => {
      console.log('할인 적용 완료:', response.data);
      setShowDiscountModal(false); // 모달 닫기
    })
    .catch(error => {
      console.error('할인 적용 중 오류 발생:', error);
    });
  };

  if (!carLog) return null;

  const formatDateTime = (date: Date) => date.toLocaleString('ko-KR', { dateStyle: 'short', timeStyle: 'short' });

  // 상태에 따른 스타일 결정
  const getStatusColor = (state: string) => {
    switch (state) {
      case '주차 중':
        return 'blue';
      case '출차 완료':
        return 'black';
      case '이동 중':
        return 'red';
      default:
        return 'black';
    }
  };

  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modalContent}>
        <button className={styles.closeButton} onClick={onClose} aria-label="Close">X</button>
        <div className={styles.modalBody}>
          <div className={styles.imageContainer}>
            {carLog.imageBase64 ? (
              <img src={`data:image/png;base64,${carLog.imageBase64}`} alt="Car" className={styles.carImage} />
            ) : (
              <div className={styles.noImage}>이미지 없음</div>
            )}
          </div>
          <div className={styles.infoAndButtons}>
            <div className={styles.detailsContainer}>
              <div className={styles.carDetails}>
                <div className={styles.detailRow}>
                  <div className={styles.dataName}>차량번호</div>
                  <div className={styles.dataValue}>{carLog.carNumber}</div>
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
                  <div className={styles.dataValue}>{carLog.fee.toLocaleString()}원</div>
                </div>
              </div>
            </div>
            <div className={styles.buttonContainer}>
              <button className={styles.modalButton} style={{ backgroundColor: '#4CAF50' }} onClick={() => setShowExitModal(true)}>출차</button>
              <button className={styles.modalButton} style={{ backgroundColor: '#2196F3' }} onClick={() => setShowMoveModal(true)}>이동</button>
              <button
                className={styles.modalButton}
                style={{
                  backgroundColor: carLog.carState === '출차 완료' ? '#d3d3d3' : '#FF9800',
                  cursor: carLog.carState === '출차 완료' ? 'not-allowed' : 'pointer'
                }}
                onClick={() => setShowDiscountModal(true)}
                disabled={carLog.carState === '출차 완료'}
              >
                할인
              </button>
            </div>
          </div>
        </div>
        {showExitModal && (
          <ExitModal
            carNumber={carLog.carNumber}
            onClose={() => setShowExitModal(false)}
            onConfirm={handleConfirmExit}
          />
        )}
        {showMoveModal && <MoveModal carLog={carLog} onClose={() => setShowMoveModal(false)} />}
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
