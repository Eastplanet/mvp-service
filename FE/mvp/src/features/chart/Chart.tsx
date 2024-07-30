// import React from 'react';
// import Sidebar from '../sidebar/Sidebar';
// import styles from './Set.module.css';

// const Set: React.FC = () => {
//   return (
//     <div className={styles.container}>
//       <div className={styles.sidebarWrapper}>
//         <Sidebar />
//       </div>
//       <div className={styles.page}>
//         <div className={styles.runtime}>
//           <p className={styles.title}>관리 운영 시간</p>
//           <p>주일</p>
//           <div className={styles.timeInputs}>
//             <input type="time" className={styles.timeInput} /> ~
//             <input type="time" className={styles.timeInput} />
//           </div>
//         </div>
//       </div>
//     </div>
//   );
// };

// export default Set;

import React from 'react';
import Sidebar from '../sidebar/Sidebar';
import styles from './Chart.module.css';

const Set: React.FC = () => {
  return (
    <div className={styles.container}>
      <Sidebar />
      <div className={styles.datachart}>
        <p className={styles.title}>Chart</p>
        <div className={styles.datas}>
          <div className={styles.data}>
            <div>
              <span>이번 달 매출</span>
              <span>회원 제외</span>
            </div>
            <div>
              <span>금액</span>
              <span>원</span>
            </div>
            <div className={styles.dif}>
              <img src="" alt="" />
              <span>계산값%</span> 
              <span>지난 달 대비</span>
            </div>
          </div>
          <div className={styles.data}>22</div>
          <div className={styles.data}>33</div>
          <div className={styles.data}>44</div>
        </div>
        <div className={styles.chart}>
          가운데 아랫 부분
        </div>
      </div>
      <div className={styles.monthdata}>
        오른쪽
      </div>
      
    </div>
  );
};

export default Set;
