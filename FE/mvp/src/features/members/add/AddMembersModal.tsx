import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import api from '../../../api/axios';
import styles from './AddMembersModal.module.css';
import { fetchMembers } from '../membersSlice';
import { AppDispatch } from '../../../store/store';
import backIcon from '../../../assets/images/icons/back.png';
import doneIcon from '../../../assets/images/icons/arrow.png';


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

  const getTodayDate = () => {
    const today = new Date();
    const yyyy = today.getFullYear();
    const mm = (today.getMonth() + 1).toString().padStart(2, '0');
    const dd = today.getDate().toString().padStart(2, '0');
    return `${yyyy}-${mm}-${dd}`;
  };

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
      await api.post('/memberships', { name, licensePlate, phoneNumber, endDate: formattedEndDate, startDate });
      dispatch(fetchMembers());
      onClose();
    } catch (error) {
      console.error('회원 추가 중 오류 발생:', error);
    }
  };

  const handlePhoneNumberChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    let value = e.target.value.replace(/\D/g, '').slice(0, 11);
    const inputEvent = e.nativeEvent as InputEvent;
  
    if (inputEvent.inputType === 'deleteContentBackward') {
      setPhoneNumber(e.target.value); 
      return;
    }
  
    if (value.length > 6) {
      value = `${value.slice(0, 3)}-${value.slice(3, 7)}-${value.slice(7)}`;
    } else if (value.length > 3) {
      value = `${value.slice(0, 3)}-${value.slice(3)}`;
    }
  
    setPhoneNumber(value);
  };

  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modalContent}>
        <button className={styles.backButton} onClick={onClose} aria-label="Close">
          <img src={backIcon} alt="back" />
        </button>
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
            onChange={handlePhoneNumberChange}
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
            min={getTodayDate()}
          />
        </div>
        <div className={styles.buttonContainer}>
          <button className={styles.submitButton} onClick={handleSubmit}>
            <img src={doneIcon} alt="done" />
          </button>
        </div>
      </div>
    </div>
  );
};

export default AddMemberModal;