import React from 'react';
import styles from './ExitModal.module.css'; // CSS 모듈

interface ExitModalProps {
  carNumber: string;
  onConfirm: () => void;
  onClose: () => void;
}

const ExitModal: React.FC<ExitModalProps> = ({ carNumber, onConfirm, onClose }) => {
  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modalContent}>
        <h1 className={styles.carNumber}>{carNumber}</h1>
        <p className={styles.message}>출차하시겠습니까?</p>
        <div className={styles.buttons}>
          <button className={styles.confirmButton} onClick={onConfirm}>확인</button>
          <button className={styles.cancelButton} onClick={onClose}>취소</button>
        </div>
      </div>
    </div>
  );
};

export default ExitModal;
