import React, { useState } from 'react';
import axios from 'axios';
import styles from './AddMembersModal.module.css';

interface AddMemberModalProps {
  onClose: () => void;
}

const AddMemberModal: React.FC<AddMemberModalProps> = ({ onClose }) => {
  const [name, setName] = useState('');
  const [licensePlate, setlicensePlate] = useState('');
  const [phone, setPhone] = useState('');
  const [expiryDate, setExpiryDate] = useState('');

  const handleSubmit = async () => {
    try {
      await axios.post('/api/members', { name, licensePlate, phone, expiryDate });
      onClose(); // 성공 시 모달 닫기
    } catch (error) {
      console.error('회원 추가 중 오류 발생:', error);
    }
  };

  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modalContent}>
        <button className={styles.backButton} onClick={onClose} aria-label="Close">←</button>
        <h2 className={styles.title}>회원 추가</h2>
        <div className={styles.formGroup}>
          <label className={styles.label}>이름</label>
          <input 
            type="text" 
            value={name} 
            onChange={(e) => setName(e.target.value)} 
            className={styles.input}
            placeholder="이름" 
          />
        </div>
        <div className={styles.formGroup}>
          <label className={styles.label}>차량 번호</label>
          <input 
            type="text" 
            value={licensePlate} 
            onChange={(e) => setlicensePlate(e.target.value)} 
            className={styles.input}
            placeholder="차량 번호" 
          />
        </div>
        <div className={styles.formGroup}>
          <label className={styles.label}>연락처</label>
          <input 
            type="text" 
            value={phone} 
            onChange={(e) => setPhone(e.target.value)} 
            className={styles.input}
            placeholder="연락처" 
          />
        </div>
        <div className={styles.formGroup}>
          <label className={styles.label}>만료일</label>
          <input 
            type="date" 
            value={expiryDate} 
            onChange={(e) => setExpiryDate(e.target.value)} 
            className={styles.input}
          />
        </div>
        <div className={styles.buttonContainer}>
          <button className={styles.submitButton} onClick={handleSubmit}>
            →
          </button>
        </div>
      </div>
    </div>
  );
};

export default AddMemberModal;
