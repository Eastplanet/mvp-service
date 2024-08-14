import React, { useState } from 'react';
import { Link, useLocation } from 'react-router-dom';
import styles from './Sidebar.module.css';
import logo2 from '../../assets/images/logos/logo2.png';
import homeIcon from '../../assets/images/icons/home.png';
import homeDeactivated from '../../assets/images/icons/home_deactivated.png';
import membersIcon from '../../assets/images/icons/members.png';
import membersDeactivated from '../../assets/images/icons/members_deactivated.png';
import chartIcon from '../../assets/images/icons/chart.png';
import chartDeactivated from '../../assets/images/icons/chart_deactivated.png';
import settingIcon from '../../assets/images/icons/setting.png';
import settingDeactivated from '../../assets/images/icons/setting_deactivated.png';
import profileIcon from '../../assets/images/icons/profile.png';
import ProfileModal from '../profile/ProfileModal';

const Sidebar = () => {
  const [isProfileModalOpen, setIsProfileModalOpen] = useState(false);
  const location = useLocation();
  const openProfileModal = () => setIsProfileModalOpen(true);
  const closeProfileModal = () => setIsProfileModalOpen(false);

  const getIcon = (path: string, activeIcon: string, deactivatedIcon: string): string => {
    return location.pathname === path ? activeIcon : deactivatedIcon;
  };

  return (
    <div className={styles.sideback}>
      <div className={styles.sidebar}>
        <div className={styles.logo}>
          <img src={logo2} alt="Company Logo" />
        </div>
        <nav className={styles.navigation}>
          <ul>
          <li>
              <Link to="/home">
                <img src={getIcon('/home', homeIcon, homeDeactivated)} alt="Home" />
              </Link>
            </li>
            <li>
              <Link to="/members">
                <img src={getIcon('/members', membersIcon, membersDeactivated)} alt="Members" />
              </Link>
            </li>
            <li>
              <Link to="/chart">
                <img src={getIcon('/chart', chartIcon, chartDeactivated)} alt="Chart" />
              </Link>
            </li>
            <li>
              <Link to="/setting">
                <img src={getIcon('/setting', settingIcon, settingDeactivated)} alt="Setting" />
              </Link>
            </li>
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
