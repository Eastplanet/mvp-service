import React from 'react';
import styles from './ExitModal.module.css';

interface ExitModalProps {
  licensePlate: string;
  onConfirm: () => void;
  onClose: () => void;
}

const ExitModal: React.FC<ExitModalProps> = ({ licensePlate, onConfirm, onClose }) => {
  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modalContent}>
        <h1 className={styles.licensePlate}>{licensePlate}</h1>
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
