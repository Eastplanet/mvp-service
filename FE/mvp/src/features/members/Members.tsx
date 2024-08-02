import React, { useState, useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { RootState, AppDispatch } from '../../store/store';
import { fetchMembers, updateMember, deleteMembersFromServer, deleteMember } from './membersSlice';
import styles from './Members.module.css';
import Sidebar from '../sidebar/Sidebar';
import AddMembersModal from './add/AddMembersModal';

interface Member {
  id: number;
  name: string;
  car: string;
  phone: string;
  join_date: Date;
  secession_date?: Date;
}

const Members: React.FC = () => {
  const dispatch = useDispatch<AppDispatch>();
  const members = useSelector((state: RootState) => state.members.members);
  const [selectedIds, setSelectedIds] = useState<number[]>([]);
  const [editingMemberId, setEditingMemberId] = useState<number | null>(null);
  const [editingData, setEditingData] = useState<Member | null>(null);
  const [allSelected, setAllSelected] = useState<boolean>(false);
  const [showModal, setShowModal] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);
  // 페이지
  const [currentPage, setCurrentPage] = useState<number>(1);
  const itemsPerPage = 5;


  useEffect(() => {
    dispatch(fetchMembers());
  }, [dispatch]);

  useEffect(() => {
    setAllSelected(members.length > 0 && selectedIds.length === members.length);
  }, [selectedIds, members]);

  const handleSelect = (id: number) => {
    setSelectedIds(prev => 
      prev.includes(id) ? prev.filter(selectedId => selectedId !== id) : [...prev, id]
    );
  };

  const parseDate = (dateStr: string) => {
    const date = new Date(dateStr);
    return isNaN(date.getTime()) ? new Date() : date;
  };

  const formatDate = (date: Date) => {
    return date.toISOString().split('T')[0];
  };

  const handleSelectAll = () => {
    if (allSelected) {
      setSelectedIds([]);
    } else {
      setSelectedIds(members.map(member => member.id));
    }
    setAllSelected(!allSelected);
  };

  const handleDelete = () => {
    if (selectedIds.length > 0) {
      // dispatch(deleteMembersFromServer(selectedIds));
      dispatch(deleteMember(selectedIds));
      setSelectedIds([]);
      setAllSelected(false);
    }
  };

  const handleEdit = (member: Member) => {
    setEditingMemberId(member.id);
    setEditingData({ ...member });
  };

  const handleSave = async () => {
    if (editingData) {
      const { name, car, secession_date, join_date } = editingData;
      if (!name || !car || !secession_date) {
        setError('이름, 차량 번호, 만료 기간은 필수 항목입니다.');
        return;
      }
  
      try {
        // Date 타입으로 처리
        const dataToSave = {
          ...editingData,
          secession_date: secession_date instanceof Date ? secession_date : parseDate(secession_date as string),
          join_date: join_date instanceof Date ? join_date : parseDate(join_date as string),
        };
  
        await dispatch(updateMember(dataToSave));
        setEditingMemberId(null);
        setEditingData(null);
        setError(null);
      } catch (error) {
        console.error('업데이트 중 오류 발생:', error);
      }
    }
  };

  const handleAddMember = () => {
    setShowModal(true);
  };

  const handleCloseModal = () => {
    setShowModal(false);
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>, field: keyof Member) => {
    if (editingData) {
      const value = (field === 'join_date' || field === 'secession_date')
        ? new Date(e.target.value)
        : e.target.value;
        
      setEditingData({ ...editingData, [field]: value });
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

  // 페이지넘버
  const paginatedMembers = members.slice((currentPage - 1) * itemsPerPage, currentPage * itemsPerPage);
  const totalPages = Math.ceil(members.length / itemsPerPage);

  const handlePageChange = (page: number) => {
    setCurrentPage(page);
  };
  
  


  const Pagination = () => (
    <div className={styles.pagination}>
      <button 
        onClick={() => handlePageChange(currentPage - 1)} 
        disabled={currentPage === 1}
      >
        &lt;
      </button>
      {Array.from({ length: totalPages }, (_, index) => (
        <button 
          key={index + 1}
          onClick={() => handlePageChange(index + 1)}
          className={currentPage === index + 1 ? styles.activePage : ''}
        >
          {index + 1}
        </button>
      ))}
      <button 
        onClick={() => handlePageChange(currentPage + 1)} 
        disabled={currentPage === totalPages}
      >
        &gt;
      </button>
    </div>
  );

  

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
            <div>
              <p className={styles.item}>전체 회원</p>
              <p className={styles.stat}>{stats.total}</p>
            </div>
          </div>
          <div className={styles.summaryItem}>
            <div className={styles.icon}>🆕</div>
            <div>
              <p>신규 회원</p>
              <p>{stats.newMembers}</p>
            </div>
          </div>
          <div className={styles.summaryItem}>
            <div className={styles.icon}>🕒</div>
            <div>
              <p>최근 만료</p>
              <p>{stats.recentExpired}</p>
            </div>
          </div>
          <div className={styles.summaryItem}>
            <div className={styles.icon}>🔄</div>
            <div>
              <p>만료 예정</p>
              <p>{stats.expiringSoon}</p>
            </div>
          </div>
        </div>

        {/* 표 */}
        <div className={styles.membersTable}>     

          <div className={styles.tableHead}>
            <td><input type="checkbox" checked={allSelected} onChange={handleSelectAll} /></td>
            <td className={styles.name}>Name</td>
            <td className={styles.car}>Car</td>
            <td className={styles.phone}>Phone</td>
            <td className={styles.date}>Join Date</td>
            <td className={styles.date}>Secession Date</td>
            <td>Actions</td>
          </div>
                  
          {paginatedMembers.map((member) => (
          <div className={styles.tableBody} key={member.id}>
            <td>
              <input type="checkbox" checked={selectedIds.includes(member.id)} onChange={() => handleSelect(member.id)} />
            </td>
            <td className={styles.name}>
              {editingMemberId === member.id ? (
                <input
                  type="text"
                  value={editingData?.name || ''} 
                  onChange={(e) => handleInputChange(e, 'name')} 
                />
              ) : (
                member.name
              )}
            </td>
            <td className={styles.car}>
              {editingMemberId === member.id ? (
                <input 
                  type="text" 
                  value={editingData?.car || ''} 
                  onChange={(e) => handleInputChange(e, 'car')} 
                />
              ) : (
                member.car
              )}
            </td>
            <td className={styles.phone}>
              {editingMemberId === member.id ? (
                <input 
                  type="text" 
                  value={editingData?.phone || ''} 
                  onChange={(e) => handleInputChange(e, 'phone')} 
                />
              ) : (
                member.phone
              )}
            </td>
            <td className={styles.date}>{formatDate(member.join_date)}</td>
            <td className={styles.date}>
              {editingMemberId === member.id ? (
                <input 
                  type="date" 
                  value={editingData?.secession_date ? editingData.secession_date.toISOString().split('T')[0] : ''} 
                  onChange={(e) => handleInputChange(e, 'secession_date')} 
                />
              ) : (
                member.secession_date ? formatDate(member.secession_date) : 'N/A'
              )}
            </td>
            <td>
              {editingMemberId === member.id ? (
                <button className={styles.editButton} onClick={handleSave}>Save</button>
              ) : (
                <button className={styles.editButton} onClick={() => handleEdit(member)}>Edit</button>
              )}
            </td>
          </div>
        ))}
          
          
          {error && <div className={styles.error}>{error}</div>}
        </div>
        <Pagination />
        <div className={styles.actions}>
          <button className={styles.addButton} onClick={handleAddMember}>추가</button>
          <button className={styles.deleteButton} onClick={handleDelete}>삭제</button>
        </div>
      </div>
      {showModal && <AddMembersModal onClose={handleCloseModal} />}
    </div>
  );
};

export default Members;
