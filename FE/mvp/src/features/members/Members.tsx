import React, { useState, useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { RootState, AppDispatch } from '../../store/store';
import { fetchMembers, updateMemberOnServer, deleteMembersFromServer } from './membersSlice';
import styles from './Members.module.css';
import Sidebar from '../sidebar/Sidebar';
import AddMembersModal from './add/AddMembersModal';
import totalMember from '../../assets/images/icons/total.png';
import newMember from '../../assets/images/icons/new.png';
import expiredMember from '../../assets/images/icons/expired.png';
import soonMember from '../../assets/images/icons/soon.png';
import { lstat } from 'fs';

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
  const [selectedCars, setSelectedCars] = useState<string[]>([]);
  const [editingMemberId, setEditingMemberId] = useState<number | null>(null);
  const [editingData, setEditingData] = useState<Member | null>(null);
  const [allSelected, setAllSelected] = useState<boolean>(false);
  const [showModal, setShowModal] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);
  const [searchTerm, setSearchTerm] = useState<string>('');
  const [currentPage, setCurrentPage] = useState<number>(1);
  const itemsPerPage = 5;

  useEffect(() => {
    dispatch(fetchMembers());
  }, [dispatch]);

  useEffect(() => {
    setAllSelected(members.length > 0 && selectedCars.length === members.length);
  }, [selectedCars, members]);

  // Select or deselect a single car
  const handleSelect = (car: string) => {
    setSelectedCars(prev =>
      prev.includes(car)
        ? prev.filter(selectedCar => selectedCar !== car)
        : [...prev, car]
    );
  };

  // Parse date string to Date object
  const parseDate = (dateStr: string) => {
    const date = new Date(dateStr);
    return isNaN(date.getTime()) ? new Date() : date;
  };

  // Format date to YYYY-MM-DD
  const formatDate = (date: Date) => {
    return date.toISOString().split('T')[0];
  };

  // Select or deselect all cars
  const handleSelectAll = () => {
    if (allSelected) {
      setSelectedCars([]);
    } else {
      setSelectedCars(members.map(member => member.car));
    }
    setAllSelected(!allSelected);
  };

  // Delete selected members
  const handleDelete = () => {
    if (selectedCars.length === 0) {
      alert("삭제할 회원을 먼저 체크해주세요.");
      return;
    }

    const confirmDelete = window.confirm("해당 회원을 정말 삭제하겠습니까?");
    if (confirmDelete) {
      dispatch(deleteMembersFromServer(selectedCars));
      setSelectedCars([]);
      setAllSelected(false);
    }
  };

  // Start editing a member
  const handleEdit = (member: Member) => {
    setEditingMemberId(member.id);
    setEditingData({ ...member });
  };

  // Save the edited member details
  const handleSave = async () => {
    if (editingData) {
      const { name, car, secession_date, phone, join_date } = editingData;

      if (phone.replace(/\D/g, '').length !== 11) {
        setError('전화번호를 전부 입력하세요.');
        return;
      }

      if (!name || !car || !secession_date || !phone) {
        setError('이름, 차량 번호, 만료 기간, 전화번호는 필수 항목입니다.');
        return;
      }

      try {
        const dataToSave = {
          ...editingData,
          secession_date: secession_date instanceof Date
            ? secession_date
            : parseDate(secession_date as string),
          join_date: join_date instanceof Date
            ? join_date
            : parseDate(join_date as string),
        };

        await dispatch(updateMemberOnServer(dataToSave));
        await dispatch(fetchMembers());
        setEditingMemberId(null);
        setEditingData(null);
        setError(null);
      } catch (error) {
        console.error('업데이트 중 오류 발생:', error);
      }
    }
  };

  // Cancel editing
  const handleCancel = () => {
    setEditingMemberId(null);
    setEditingData(null);
    setError(null);
  };

  // Open modal to add new member
  const handleAddMember = () => {
    setShowModal(true);
  };

  // Close the modal
  const handleCloseModal = () => {
    setShowModal(false);
  };

  // Handle input change for editing member data
  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>, field: keyof Member) => {
    if (editingData) {
      let value: string | Date = e.target.value;
      if (field === 'phone') {
        let numericValue = value.replace(/\D/g, '').slice(0, 11);
        const inputEvent = e.nativeEvent as InputEvent;
        if (inputEvent.inputType === 'deleteContentBackward') {
          setEditingData({ ...editingData, [field]: value });
          return;
        }

        if (numericValue.length > 6) {
          value = `${numericValue.slice(0, 3)}-${numericValue.slice(3, 7)}-${numericValue.slice(7, 11)}`;
        } else if (numericValue.length > 3) {
          value = `${numericValue.slice(0, 3)}-${numericValue.slice(3)}`;
        } else {
          value = numericValue;
        }

        setEditingData({ ...editingData, [field]: value });
      } else if (field === 'join_date' || field === 'secession_date') {
        value = new Date(value);
        setEditingData({ ...editingData, [field]: value });
      } else {
        setEditingData({ ...editingData, [field]: value });
      }
    }
  };

  // Calculate statistics for the members
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

  // Pagination logic
  const totalPages = Math.ceil(members.length / itemsPerPage);
  const pagesToShow = 5;
  const halfPagesToShow = Math.floor(pagesToShow / 2);
  const startPage = Math.max(1, currentPage - halfPagesToShow);
  const endPage = Math.min(totalPages, currentPage + halfPagesToShow);
  const filteredMembers = members.filter(member =>
    member.car.toLowerCase().includes(searchTerm.toLowerCase())
  );
  const paginatedMembers = filteredMembers.slice((currentPage - 1) * itemsPerPage, currentPage * itemsPerPage);

  // Handle search input change
  const handleSearchChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSearchTerm(e.target.value);
  };

  // Handle page change for pagination
  const handlePageChange = (page: number) => {
    setCurrentPage(page);
  };

  // Render pagination buttons
  const Pagination = () => (
    <div className={styles.pagination}>
      <button
        onClick={() => handlePageChange(Math.max(1, startPage - pagesToShow))}
        disabled={currentPage === 1}
      >
        &lt;
      </button>
      {Array.from({ length: endPage - startPage + 1 }, (_, index) => (
        <button
          key={startPage + index}
          onClick={() => handlePageChange(startPage + index)}
          className={currentPage === startPage + index ? styles.activePage : ''}
        >
          {startPage + index}
        </button>
      ))}
      <button
        onClick={() => handlePageChange(Math.min(totalPages, endPage + pagesToShow))}
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
      <div className={styles.page}>
        <div className={styles.content}>
          <div className={styles.summary}>
            <div className={styles.summaryItem}>
              <div className={styles.icon}>
                <img src={totalMember} alt="totalMember" />
              </div>
              <div>
                <p className={styles.item}>전체 회원</p>
                <p className={styles.stat}>{stats.total}</p>
              </div>
            </div>
            <div className={styles.summaryItem}>
              <div className={styles.icon}>
                <img src={newMember} alt="newMember" />
              </div>
              <div>
                <p className={styles.item}>신규 회원</p>
                <p className={styles.stat}>{stats.newMembers}</p>
              </div>
            </div>
            <div className={styles.summaryItem}>
              <div className={styles.icon}>
                <img src={expiredMember} alt="expiredMember" />
              </div>
              <div>
                <p className={styles.item}>최근 만료</p>
                <p className={styles.stat}>{stats.recentExpired}</p>
              </div>
            </div>
            <div className={styles.summaryItem}>
              <div className={styles.icon}>
                <img src={soonMember} alt="soonMember" />
              </div>
              <div>
                <p className={styles.item}>만료 예정</p>
                <p className={styles.stat}>{stats.expiringSoon}</p>
              </div>
            </div>
          </div>

          <div className={styles.searchContainer}>
            <input
              className={styles.search}
              type="text"
              placeholder='차량 번호 검색'
              value={searchTerm}
              onChange={handleSearchChange}
            />
          </div>

          {error && <div className={styles.errorMessage}>{error}</div>}

          <div className={styles.membersTable}>
            <div className={styles.tableHead}>
              <div>
                <input
                  type="checkbox"
                  checked={allSelected}
                  onChange={handleSelectAll}
                />
              </div>
              <div className={styles.name}>이름</div>
              <div className={styles.car}>차량 번호</div>
              <div className={styles.phone}>연락처</div>
              <div className={styles.date}>가입 날짜</div>
              <div className={styles.date}>만료 날짜</div>
              <div className={styles.action}>수정</div>
            </div>
            <div className={styles.tableBodyContainer}>
              {paginatedMembers.map((member) => (
                <div className={styles.tableBody} key={member.id}>
                  <div>
                    <input
                      type="checkbox"
                      checked={selectedCars.includes(member.car)}
                      onChange={() => handleSelect(member.car)}
                    />
                  </div>
                  <div className={styles.name}>
                    {editingMemberId === member.id ? (
                      <input
                        type="text"
                        value={editingData?.name || ''}
                        onChange={(e) => handleInputChange(e, 'name')}
                      />
                    ) : (
                      member.name
                    )}
                  </div>
                  <div className={styles.car}>
                    {editingMemberId === member.id ? (
                      <input
                        type="text"
                        value={editingData?.car || ''}
                        onChange={(e) => handleInputChange(e, 'car')}
                      />
                    ) : (
                      member.car
                    )}
                  </div>
                  <div className={styles.phone}>
                    {editingMemberId === member.id ? (
                      <input
                        type="text"
                        value={editingData?.phone || ''}
                        onChange={(e) => handleInputChange(e, 'phone')}
                        maxLength={13}
                      />
                    ) : (
                      member.phone
                    )}
                  </div>
                  <div className={styles.date}>
                    {formatDate(member.join_date)}
                  </div>
                  <div className={styles.date}>
                    {editingMemberId === member.id ? (
                      <input
                        type="date"
                        value={
                          editingData?.secession_date
                            ? editingData.secession_date.toISOString().split('T')[0]
                            : ''
                        }
                        onChange={(e) => handleInputChange(e, 'secession_date')}
                      />
                    ) : (
                      member.secession_date
                        ? formatDate(member.secession_date)
                        : 'N/A'
                    )}
                  </div>
                  <div>
                    {editingMemberId === member.id ? (
                      <button className={styles.editButton} onClick={handleSave}>Save</button>
                    ) : (
                      <button className={styles.editButton} onClick={() => handleEdit(member)}>Edit</button>
                    )}
                  </div>
                </div>
              ))}
            </div>
            <div>
              <Pagination />
            </div>
            <div className={styles.actionButtons}>
              <button className={styles.addButton} onClick={handleAddMember}>추가</button>
              <button className={styles.deleteButton} onClick={handleDelete}>삭제</button>
            </div>
          </div>

          {showModal && <AddMembersModal onClose={handleCloseModal} />}
        </div>
      </div>
    </div>
  );
};

export default Members;