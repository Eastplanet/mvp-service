import React, { useState, useEffect, useRef } from 'react';
import Sidebar from '../sidebar/Sidebar';
import styles from './Set.module.css';

// 초기 상태의 타입 정의
interface InitialState {
  basicTime: number;
  basicCost: number;
  additionalTime: number;
  additionalCost: number;
  oneDayTicket: number;
  oneWeekTicket: number;
  oneMonthTicket: number;
  isSystemActive: boolean;
}

const Set: React.FC = () => {
  // 상태 관리를 위한 useState 훅 사용
  const [isSystemActive, setIsSystemActive] = useState(true);
  const [basicTime, setBasicTime] = useState(0);
  const [basicCost, setBasicCost] = useState(0);
  const [additionalTime, setAdditionalTime] = useState(0);
  const [additionalCost, setAdditionalCost] = useState(0);
  const [oneDayTicket, setOneDayTicket] = useState(0);
  const [oneWeekTicket, setOneWeekTicket] = useState(0);
  const [oneMonthTicket, setOneMonthTicket] = useState(0);
  const [isDirty, setIsDirty] = useState(false);

  // 초기 상태를 저장하기 위한 ref 사용
  const initialStateRef = useRef<InitialState>({
    basicTime: 0,
    basicCost: 0,
    additionalTime: 0,
    additionalCost: 0,
    oneDayTicket: 0,
    oneWeekTicket: 0,
    oneMonthTicket: 0,
    isSystemActive: true
  });

  // 데이터 로드 함수
  const fetchData = async () => {
    try {
      const response = await fetch('/api/get-settings');
      const data = await response.json();
      // 받아온 데이터로 상태 업데이트
      setBasicTime(data.basicTime);
      setBasicCost(data.basicCost);
      setAdditionalTime(data.additionalTime);
      setAdditionalCost(data.additionalCost);
      setOneDayTicket(data.oneDayTicket);
      setOneWeekTicket(data.oneWeekTicket);
      setOneMonthTicket(data.oneMonthTicket);
      setIsSystemActive(data.isSystemActive);
      
      // 초기 상태 저장
      initialStateRef.current = {
        basicTime: data.basicTime,
        basicCost: data.basicCost,
        additionalTime: data.additionalTime,
        additionalCost: data.additionalCost,
        oneDayTicket: data.oneDayTicket,
        oneWeekTicket: data.oneWeekTicket,
        oneMonthTicket: data.oneMonthTicket,
        isSystemActive: data.isSystemActive
      };
    } catch (error) {
      console.error('데이터 로드 실패:', error);
    }
  };

  // 컴포넌트 마운트 시 데이터 로드
  useEffect(() => {
    fetchData();
  }, []);

  // 상태가 변경되었는지 확인하여 isDirty 업데이트
  useEffect(() => {
    const initialState = initialStateRef.current;
    const hasChanges = (
      basicTime !== initialState.basicTime ||
      basicCost !== initialState.basicCost ||
      additionalTime !== initialState.additionalTime ||
      additionalCost !== initialState.additionalCost ||
      oneDayTicket !== initialState.oneDayTicket ||
      oneWeekTicket !== initialState.oneWeekTicket ||
      oneMonthTicket !== initialState.oneMonthTicket ||
      isSystemActive !== initialState.isSystemActive
    );

    setIsDirty(hasChanges);
  }, [basicTime, basicCost, additionalTime, additionalCost, oneDayTicket, oneWeekTicket, oneMonthTicket, isSystemActive]);

  // 입력값 변경 핸들러
  const handleInputChange = (setter: React.Dispatch<React.SetStateAction<number>>) => (e: React.ChangeEvent<HTMLInputElement>) => {
    setter(Number(e.target.value));
  };

  // 시스템 상태 변경 핸들러
  const handleSystemStateChange = (newState: boolean) => {
    setIsSystemActive(newState);
  };

  // 저장 버튼 클릭 핸들러
  const handleSave = () => {
    const data = {
      basicTime,
      basicCost,
      additionalTime,
      additionalCost,
      oneDayTicket,
      oneWeekTicket,
      oneMonthTicket,
      isSystemActive
    };
    
    console.log("저장된 데이터:", data);

    // 실제 서버로 데이터 전송 코드 (주석 처리된 예시)
    // fetch('/api/save-settings', {
    //   method: 'POST',
    //   headers: {
    //     'Content-Type': 'application/json',
    //   },
    //   body: JSON.stringify(data),
    // })
    // .then(response => response.json())
    // .then(result => console.log('성공:', result))
    // .catch(error => console.error('실패:', error));
  };

  return (
    <div className={styles.container}>
      <Sidebar />
      <div className={styles.page}>
        <div className={styles.roundedBox}>
          {/* 시간별 금액 설정 섹션 */}
          <div className={styles.section}>
            <p className={styles.title}>시간별 금액 설정</p>
            <div className={styles.rateSetting}>
              <div className={styles.rateRow}>
                <span>기본금:</span>
                <input
                  type="number"
                  className={styles.rateInputTime}
                  value={basicTime}
                  onChange={handleInputChange(setBasicTime)}
                />분
                <input
                  type="number"
                  className={styles.rateInputTime}
                  value={basicCost}
                  onChange={handleInputChange(setBasicCost)}
                />원
                <p className={styles.nocosttime}>10분이하 정차 무료</p>
              </div>
              <div className={styles.rateRow}>
                <span>추가금:</span>
                <input
                  type="number"
                  className={styles.rateInputTime}
                  value={additionalTime}
                  onChange={handleInputChange(setAdditionalTime)}
                />분당
                <input
                  type="number"
                  className={styles.rateInputTime}
                  value={additionalCost}
                  onChange={handleInputChange(setAdditionalCost)}
                />원
              </div>
            </div>
          </div>

          {/* 주차권 금액 섹션 */}
          <div className={styles.section}>
            <p className={styles.title}>주차권 금액</p>
            <div className={styles.ticketRates}>
              <div className={styles.ticketRow}>
                <span>1일권:</span>
                <input
                  type="number"
                  className={styles.rateInput}
                  value={oneDayTicket}
                  onChange={handleInputChange(setOneDayTicket)}
                />원
              </div>
              <div className={styles.ticketRow}>
                <span>1주일권:</span>
                <input
                  type="number"
                  className={styles.rateInput}
                  value={oneWeekTicket}
                  onChange={handleInputChange(setOneWeekTicket)}
                />원
              </div>
              <div className={styles.ticketRow}>
                <span>1달권:</span>
                <input
                  type="number"
                  className={styles.rateInput}
                  value={oneMonthTicket}
                  onChange={handleInputChange(setOneMonthTicket)}
                />원
              </div>
            </div>
          </div>

          {/* 시스템 상태 조정 섹션 */}
          <div className={styles.section_bottom}>
            <p className={styles.title}>시스템 정지</p>
            <div className={styles.system_button}>
              <button
                className={`${styles.button_deactivate} ${!isSystemActive ? styles.active : ''}`}
                onClick={() => handleSystemStateChange(false)}
              >
                시스템 비활성화
              </button>
              <button
                className={`${styles.button_activate} ${isSystemActive ? styles.active : ''}`}
                onClick={() => handleSystemStateChange(true)}
              >
                시스템 활성화
              </button>
            </div>
            <p className={styles.statusMessage}>
              {isSystemActive ? '시스템 활성화 상태입니다' : '시스템 비활성화 상태입니다'}
            </p>
          </div>

          {/* 저장 버튼 */}
          <button
            className={`${styles.save} ${isDirty ? styles.active : ''}`}
            onClick={isDirty ? handleSave : undefined}
            disabled={!isDirty}
          >
            저장
          </button>
        </div>
      </div>
    </div>
  );
};

export default Set;