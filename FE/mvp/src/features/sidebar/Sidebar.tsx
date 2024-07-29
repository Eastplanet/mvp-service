import React from 'react';
import styles from './Sidebar.module.css';

interface SidebarProps {
  // 필요한 경우 Props 정의
}

const Sidebar: React.FC<SidebarProps> = () => {
  return (
    <div className={styles.sidebar}>
      <div className={styles.logo}>
        <img src="path/to/logo.png" alt="Logo" />
      </div>
      <nav className={styles.navigation}>
        <ul>
          <li><a href="/home"><img src="path/to/home-icon.png" alt="Home" /></a></li>
          <li><a href="/members"><img src="path/to/members-icon.png" alt="Members" /></a></li>
          <li><a href="/chart"><img src="path/to/chart-icon.png" alt="Chart" /></a></li>
          <li><a href="/settings"><img src="path/to/settings-icon.png" alt="Settings" /></a></li>
        </ul>
      </nav>
      <div className={styles.profile}>
        <img src="path/to/profile-icon.png" alt="Profile" />
      </div>
    </div>
  );
};

export default Sidebar;
