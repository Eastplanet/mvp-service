import React, { useState, useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { RootState, AppDispatch } from '../../store/store';
import { fetchMembers, updateMember, deleteMembersFromServer } from './membersSlice';
import styles from './Members.module.css';
import Sidebar from '../sidebar/Sidebar';

interface Member {
  id: number;
  name: string;
  car: string;
  phone: string;
  join_date: string;
  secession_date?: string;
}

const Members: React.FC = () => {
  const dispatch = useDispatch<AppDispatch>();
  const members = useSelector((state: RootState) => state.members.members);
  const [selectedIds, setSelectedIds] = useState<number[]>([]); // 선택된 항목의 ID를 저장
  const [editingMemberId, setEditingMemberId] = useState<number | null>(null);
  const [editingData, setEditingData] = useState<Member | null>(null);

  useEffect(() => {
    dispatch(fetchMembers());
  }, [dispatch]);

  const handleSelect = (id: number) => {
    setSelectedIds(prev => 
      prev.includes(id) ? prev.filter(selectedId => selectedId !== id) : [...prev, id]
    );
  };

  const handleDelete = () => {
    if (selectedIds.length > 0) {
      dispatch(deleteMembersFromServer(selectedIds));
      setSelectedIds([]);
    }
  };

  const handleEdit = (member: Member) => {
    setEditingMemberId(member.id);
    setEditingData({ ...member });
  };

  const handleSave = async (id: number) => {
    if (editingData) {
      try {
        const dataToSave = {
          ...editingData,
          secession_date: editingData.secession_date || ''
        };
        await dispatch(updateMember(dataToSave));
        setEditingMemberId(null);
        setEditingData(null);
      } catch (error) {
        console.error('업데이트 중 오류 발생:', error);
      }
    }
  };

  const getStatistics = () => {
    const now = new Date();
    const past30Days = new Date(now);
    past30Days.setDate(now.getDate() - 30);
    const future30Days = new Date(now);
    future30Days.setDate(now.getDate() + 30);

    const newMembers = members.filter(member =>
      new Date(member.join_date).getTime() > past30Days.getTime()
    );

    const recentExpired = members.filter(member =>
      member.secession_date && new Date(member.secession_date).getTime() < now.getTime() &&
      new Date(member.secession_date).getTime() > past30Days.getTime()
    );

    const expiringSoon = members.filter(member =>
      member.secession_date && new Date(member.secession_date).getTime() > now.getTime() &&
      new Date(member.secession_date).getTime() < future30Days.getTime()
    );

    return {
      total: members.length,
      newMembers: newMembers.length,
      recentExpired: recentExpired.length,
      expiringSoon: expiringSoon.length,
    };
  };

  const stats = getStatistics();

  return (
    <div className={styles.membersPage}>
      <Sidebar />
      <div className={styles.content}>
        <div className={styles.header}>
          <h1>Members</h1>
        </div>
        <div className={styles.summary}>
          <div className={styles.summaryItem}>
            <div className={styles.icon}>🏁</div>
            <div>전체 회원</div>
            <div>{stats.total}</div>
          </div>
          <div className={styles.summaryItem}>
            <div className={styles.icon}>🆕</div>
            <div>신규 회원</div>
            <div>{stats.newMembers}</div>
          </div>
          <div className={styles.summaryItem}>
            <div className={styles.icon}>🕒</div>
            <div>최근 만료</div>
            <div>{stats.recentExpired}</div>
          </div>
          <div className={styles.summaryItem}>
            <div className={styles.icon}>🔄</div>
            <div>만료 예정</div>
            <div>{stats.expiringSoon}</div>
          </div>
        </div>
        <div className={styles.membersTable}>
          <table>
            <thead>
              <tr>
                <th><input type="checkbox" onChange={() => setSelectedIds(members.map(member => member.id))} /></th>
                <th>Name</th>
                <th>Car</th>
                <th>Phone</th>
                <th>Join Date</th>
                <th>Secession Date</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {members.map((member) => (
                <tr key={member.id}>
                  <td><input type="checkbox" checked={selectedIds.includes(member.id)} onChange={() => handleSelect(member.id)} /></td>
                  <td>
                    {editingMemberId === member.id ? (
                      <input type="text" value={editingData?.name} onChange={(e) => setEditingData({...editingData!, id: editingMemberId!, name: editingData?.name || '', car: editingData?.car || '', phone: editingData?.phone || '', secession_date: editingData?.secession_date || ''})} />
                    ) : (
                      member.name
                    )}
                  </td>
                  <td>
                    {editingMemberId === member.id ? (
                      <input type="text" value={editingData?.car} onChange={(e) => setEditingData({...editingData!, id: editingMemberId!, name: editingData?.name || '', car: editingData?.car || '', phone: editingData?.phone || '', secession_date: editingData?.secession_date || ''})} />
                    ) : (
                      member.car
                    )}
                  </td>
                  <td>
                    {editingMemberId === member.id ? (
                      <input type="text" value={editingData?.phone} onChange={(e) => setEditingData({...editingData!, id: editingMemberId!, name: editingData?.name || '', car: editingData?.car || '', phone: editingData?.phone || '', secession_date: editingData?.secession_date || ''})} />
                    ) : (
                      member.phone
                    )}
                  </td>
                  <td>{member.join_date}</td>
                  <td>
                    {editingMemberId === member.id ? (
                      <input type="text" value={editingData?.secession_date || ''} onChange={(e) => setEditingData({...editingData!, id: editingMemberId!, name: editingData?.name || '', car: editingData?.car || '', phone: editingData?.phone || '', secession_date: editingData?.secession_date || ''})} />
                    ) : (
                      member.secession_date || 'N/A'
                    )}
                  </td>
                  <td>
                    {editingMemberId === member.id ? (
                      <button onClick={() => handleSave(member.id)}>Save</button>
                    ) : (
                      <button onClick={() => handleEdit(member)}>Edit</button>
                    )}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
        <div className={styles.actions}>
          <button className={styles.addButton}>추가</button>
          <button className={styles.deleteButton} onClick={handleDelete}>삭제</button>
        </div>
      </div>
    </div>
  );
};

export default Members;
