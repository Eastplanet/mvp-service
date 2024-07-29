import React from 'react';
import styles from './Sidebar.module.css';
import logo2 from '../../assets/images/logos/logo2.png';
import homeIcon from '../../assets/images/icons/home.png';
import membersIcon from '../../assets/images/icons/members.png';
import chartIcon from '../../assets/images/icons/chart.png';
import settingIcon from '../../assets/images/icons/setting.png';
import profileIcon from '../../assets/images/icons/profile.png';

interface SidebarProps {
  // 필요한 경우 Props 정의
}

const Sidebar: React.FC<SidebarProps> = () => {
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
            <li><a href="/settings"><img src={ settingIcon } alt="Settings" /></a></li>
          </ul>
        </nav>
        <div className={styles.profile}>
          <img src={profileIcon} alt="Profile" />
        </div>
      </div>
    </div>
  );
};

export default Sidebar;
