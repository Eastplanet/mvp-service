import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import axios from 'axios';
import styles from './AddMembersModal.module.css';
import { fetchMembers } from '../membersSlice';
import { AppDispatch } from '../../../store/store';

interface AddMemberModalProps {
  onClose: () => void;
}

const AddMemberModal: React.FC<AddMemberModalProps> = ({ onClose }) => {
  const dispatch = useDispatch<AppDispatch>();
  const [name, setName] = useState('');
  const [licensePlate, setLicensePlate] = useState('');
  const [phoneNumber, setPhoneNumber] = useState('');
  const [endDate, setEndDate] = useState('');
  const [error, setError] = useState('');

  const getISODateString = (date: string) => {
    const dateObject = new Date(date);
    return dateObject.toISOString();
  };

  const handleSubmit = async () => {
    if (!name || !licensePlate || !phoneNumber || !endDate) {
      setError('모든 필수 정보를 입력하세요.');
      return;
    }
  
    const startDate = getISODateString(new Date().toISOString());
    const formattedEndDate = getISODateString(endDate);
  
    try {
      await axios.post('https://mvp-project.shop/api/memberships', { name, licensePlate, phoneNumber, endDate: formattedEndDate, startDate });
      dispatch(fetchMembers());
      onClose();
    } catch (error) {
      console.error('회원 추가 중 오류 발생:', error);
    }
  };

  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modalContent}>
        <button className={styles.backButton} onClick={onClose} aria-label="Close">←</button>
        <h2 className={styles.title}>회원 추가</h2>
        {error && <div className={styles.error}>{error}</div>}
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
            onChange={(e) => setLicensePlate(e.target.value)} 
            className={styles.input}
            placeholder="차량 번호" 
          />
        </div>
        <div className={styles.formGroup}>
          <label className={styles.label}>연락처</label>
          <input 
            type="text" 
            value={phoneNumber} 
            onChange={(e) => setPhoneNumber(e.target.value)} 
            className={styles.input}
            placeholder="연락처" 
          />
        </div>
        <div className={styles.formGroup}>
          <label className={styles.label}>만료일</label>
          <input 
            type="date" 
            value={endDate} 
            onChange={(e) => setEndDate(e.target.value)} 
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