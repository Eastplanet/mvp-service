import React from 'react';
// import { useSelector } from 'react-redux';
import { useSelector, useDispatch } from 'react-redux';
import { RootState } from '../../store/store';
import styles from './ProfileModal.module.css';
import logo from '../../assets/images/logos/logo.png'
import { logoutSuccess } from '../auth/authSlice';

interface ProfileModalProps {
  onClose: () => void;
}

const ProfileModal: React.FC<ProfileModalProps> = ({ onClose }) => {
  const user = useSelector((state: RootState) => state.auth.user);
  const dispatch = useDispatch();

  const handleLogout = () => {
    dispatch(logoutSuccess());
    onClose();
  };

  return (
    <div className={styles.modalOverlay} onClick={onClose}>
      <div className={styles.modalContent} onClick={(e) => e.stopPropagation()}>
        <button className={styles.closeButton} onClick={onClose}>×</button>
        <div className={styles.modalHeader}>
          <img src={logo} alt="Logo" className={styles.logo} />
        </div>
        <div className={styles.modalBody}>
          <div className={styles.infoRow}>
            <div className={styles.label}>관리자 정보</div>
            <div className={styles.value}>{user.name}</div>
          </div>
          <div className={styles.infoRow}>
            <div className={styles.label}>이메일</div>
            <div className={styles.value}>{user.email}</div>
          </div>
        </div>
        <div className={styles.logoutButtonContainer}>
          <button className={styles.logoutButton} onClick={handleLogout}>로그아웃</button>
        </div>
      </div>
    </div>
  );
};

export default ProfileModal;
