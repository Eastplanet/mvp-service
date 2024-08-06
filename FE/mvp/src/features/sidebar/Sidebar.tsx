import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import styles from './Sidebar.module.css';
import logo2 from '../../assets/images/logos/logo2.png';
import homeIcon from '../../assets/images/icons/home.png';
import membersIcon from '../../assets/images/icons/members.png';
import chartIcon from '../../assets/images/icons/chart.png';
import settingIcon from '../../assets/images/icons/setting.png';
import profileIcon from '../../assets/images/icons/profile.png';
import ProfileModal from '../profile/ProfileModal';

const Sidebar = () => {
  const [isProfileModalOpen, setIsProfileModalOpen] = useState(false);

  const openProfileModal = () => setIsProfileModalOpen(true);
  const closeProfileModal = () => setIsProfileModalOpen(false);

  return (
    <div className={styles.sideback}>
      <div className={styles.sidebar}>
        <div className={styles.logo}>
          <img src={logo2} alt="Company Logo" />
        </div>
        <nav className={styles.navigation}>
          <ul>
            <li><Link to="/home"><img src={homeIcon} alt="Home" /></Link></li>
            <li><Link to="/members"><img src={membersIcon} alt="Members" /></Link></li>
            <li><Link to="/chart"><img src={chartIcon} alt="Chart" /></Link></li>
            <li><Link to="/setting"><img src={settingIcon} alt="Setting" /></Link></li>
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
