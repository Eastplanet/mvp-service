import React, { useState } from 'react';
import styles from './DiscountModal.module.css';
import { CarLog } from '../CarInfoModal';

interface DiscountModalProps {
  carLog: CarLog;
  onClose: () => void;
  onApplyDiscount: (discount: number) => void;
}

const DiscountModal: React.FC<DiscountModalProps> = ({ carLog, onClose, onApplyDiscount }) => {
  const [discountAmount, setDiscountAmount] = useState(0);

  const handleDiscount = (amount: number) => {
    setDiscountAmount(Math.min(amount, carLog.fee));
  };

  const applyDiscount = () => {
    onApplyDiscount(discountAmount);
    onClose();
  };

  const calculateFinalAmount = () => {
    return Math.max(carLog.fee - discountAmount, 0);
  };

  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modalContent}>
        <button className={styles.closeButton} onClick={onClose} aria-label="Close">x</button>
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
                  <div className={styles.dataName}>주차 요금</div>
                  <div className={styles.dataValue}>{carLog.fee.toLocaleString()}원</div>
                </div>
                <div className={styles.detailRow}>
                  <div className={styles.dataName}>할인 요금</div>
                  <div className={styles.dataValue}>
                  <input
                    type="number"
                    value={discountAmount}
                    onChange={(e) => handleDiscount(Math.max(0, Math.min(Number(e.target.value), carLog.fee)))}
                    className={styles.input}
                    placeholder="0"
                    min="0"
                    max={carLog.fee}
                    style={{ textAlign: 'right' }}
                  />
                    원
                  </div>
                </div>
                <div className={styles.detailRow}>
                  <div className={styles.dataName}>정산 요금</div>
                  <div className={styles.dataValue}>{calculateFinalAmount().toLocaleString()}원</div>
                </div>
              </div>
            </div>
            <div className={styles.buttonContainer}>
              <div className={styles.buttons}>
                <button className={styles.modalButton} onClick={() => handleDiscount(carLog.fee * 0.1)}>10% 할인</button>
                <button className={styles.modalButton} onClick={() => handleDiscount(carLog.fee * 0.5)}>50% 할인</button>
                <button className={styles.modalButton} onClick={() => handleDiscount(carLog.fee)}>전액 할인</button>
                <button className={styles.modalButton} onClick={() => handleDiscount(1000)}>1000원 할인</button>
                <button className={styles.modalButton} onClick={() => handleDiscount(5000)}>5000원 할인</button>
                <button className={`${styles.modalButton} ${styles.applyButton}`} onClick={applyDiscount}>할인적용</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default DiscountModal;
