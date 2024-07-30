import React from 'react';
import styles from './Modal.module.css'; // 각 모달에 대한 CSS 파일
import { CarLog } from '../CarInfoModal';

interface MoveModalProps {
  carLog: CarLog;
  onClose: () => void;
}

const MoveModal: React.FC<MoveModalProps> = ({ carLog, onClose }) => {
  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modalContent}>
        <button className={styles.closeButton} onClick={onClose} aria-label="Close">X</button>
        <h2>이동 정보</h2>
        {/* 이동 모달 내용 추가 */}
      </div>
    </div>
  );
};

export default MoveModal;
