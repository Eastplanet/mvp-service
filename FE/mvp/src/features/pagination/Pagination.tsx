import React, { useState } from 'react';
import styles from './Members.module.css'; // 이 경로가 맞는지 확인하세요.

interface PaginationProps {
  currentPage: number;
  totalPages: number;
  onPageChange: (page: number) => void;
}

const Pagination: React.FC<PaginationProps> = ({ currentPage, totalPages, onPageChange }) => {
  const [pageRangeStart, setPageRangeStart] = useState<number>(1);
  const [pageRangeEnd, setPageRangeEnd] = useState<number>(Math.min(totalPages, 5));

  const handlePageChange = (page: number) => {
    onPageChange(page);
    updatePageRange(page);
  };

  const updatePageRange = (page: number) => {
    const pagesToShow = 5;
    if (page < pageRangeStart) {
      setPageRangeStart(Math.max(1, page - Math.floor(pagesToShow / 2)));
      setPageRangeEnd(Math.min(totalPages, page + Math.floor(pagesToShow / 2)));
    } else if (page > pageRangeEnd) {
      setPageRangeEnd(Math.min(totalPages, page + Math.floor(pagesToShow / 2)));
      setPageRangeStart(Math.max(1, page - Math.floor(pagesToShow / 2)));
    }
  };

  return (
    <div className={styles.pagination}>
      <button 
        onClick={() => handlePageChange(currentPage - 1)} 
        disabled={currentPage === 1}
      >
        &lt;
      </button>
      {pageRangeStart > 1 && (
        <>
          <button onClick={() => handlePageChange(1)}>1</button>
          {pageRangeStart > 2 && <span>...</span>}
        </>
      )}
      {Array.from({ length: pageRangeEnd - pageRangeStart + 1 }, (_, index) => (
        <button 
          key={pageRangeStart + index}
          onClick={() => handlePageChange(pageRangeStart + index)}
          className={currentPage === pageRangeStart + index ? styles.activePage : ''}
        >
          {pageRangeStart + index}
        </button>
      ))}
      {pageRangeEnd < totalPages && (
        <>
          {totalPages - pageRangeEnd > 1 && <span>...</span>}
          <button onClick={() => handlePageChange(totalPages)}>{totalPages}</button>
        </>
      )}
      <button 
        onClick={() => handlePageChange(currentPage + 1)} 
        disabled={currentPage === totalPages}
      >
        &gt;
      </button>
    </div>
  );
};

export default Pagination;
