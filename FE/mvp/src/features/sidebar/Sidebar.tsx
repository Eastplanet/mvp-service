import React, { useState } from 'react';
// import { Link } from 'react-router-dom'; // Link 컴포넌트를 임포트합니다.
import styles from './Sidebar.module.css';
import logo2 from '../../assets/images/logos/logo2.png';
import homeIcon from '../../assets/images/icons/home.png';
import membersIcon from '../../assets/images/icons/members.png';
import chartIcon from '../../assets/images/icons/chart.png';
import settingIcon from '../../assets/images/icons/setting.png';
import profileIcon from '../../assets/images/icons/profile.png';
import ProfileModal from '../profile/ProfileModal';

interface SidebarProps {
  // 필요한 경우 Props 정의
}

const Sidebar: React.FC<SidebarProps> = () => {
  const [isProfileModalOpen, setIsProfileModalOpen] = useState(false);

  const openProfileModal = () => setIsProfileModalOpen(true);
  const closeProfileModal = () => setIsProfileModalOpen(false);

  return (
    <div className={styles.sideback}>
      <div className={styles.sidebar}>
        <div className={styles.logo}>
          <img src={ logo2 } alt="Logo" />
        </div>
        <nav className={styles.navigation}>
          <ul>
            <li><a href="/home"><img src={ homeIcon } alt="Home" /></a></li>
            <li><a href="/members"><img src={ membersIcon } alt="Members" /></a></li>
            <li><a href="/chart"><img src={ chartIcon } alt="Chart" /></a></li>
            <li><a href="/setting"><img src={ settingIcon } alt="Setting" /></a></li>
          </ul>
        </nav>
        <div className={styles.profile} onClick={openProfileModal}>
          <img src={profileIcon} alt="Profile" />
        </div>
      </div>
      {isProfileModalOpen && <ProfileModal onClose={closeProfileModal} />}
    </div>
  );
};

export default Sidebar;
