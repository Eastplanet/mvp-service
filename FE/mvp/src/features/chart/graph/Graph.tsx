import React, { useState } from 'react';
import { Line } from 'react-chartjs-2';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
  Filler
} from 'chart.js';
import { ChartOptions } from 'chart.js';
import styles from './Graph.module.css';

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
  Filler
);

interface GraphProps {
  dailyRevenues: Array<{ date: string, revenue: number, parkingCount: number }>;
  monthlyRevenues: Array<{ date: string, revenue: number, parkingCount: number }>;
}

const Graph: React.FC<GraphProps> = ({ dailyRevenues, monthlyRevenues }) => {
  const [mode, setMode] = useState<'daily' | 'monthly'>('daily');

  const toggleMode = (selectedMode: 'daily' | 'monthly') => {
    setMode(selectedMode);
  };

  const formatDate = (date: string, mode: 'daily' | 'monthly') => {
    const dateObj = new Date(date);
    const year = dateObj.getFullYear();
    const month = (`0${dateObj.getMonth() + 1}`).slice(-2);
    const day = (`0${dateObj.getDate()}`).slice(-2);
    return mode === 'daily' ? `${year}-${month}-${day}` : `${year}-${month}`;
  };

  const data = mode === 'daily'
    ? {
        labels: dailyRevenues.map((entry) => formatDate(entry.date, 'daily')),
        datasets: [
          {
            label: '일별 매출',
            data: dailyRevenues.map((entry) => entry.revenue),
            fill: false,
            backgroundColor: 'rgba(75,192,192,0.4)',
            borderColor: 'rgba(75,192,192,1)',
            yAxisID: 'y',
          },
          {
            label: '일별 주차 대수',
            data: dailyRevenues.map((entry) => entry.parkingCount),
            fill: false,
            backgroundColor: 'rgba(255,159,64,0.4)',
            borderColor: 'rgba(255,159,64,1)',
            yAxisID: 'y1',
          }
        ],
      }
    : {
        labels: monthlyRevenues.map((entry) => formatDate(entry.date, 'monthly')),
        datasets: [
          {
            label: '월별 매출',
            data: monthlyRevenues.map((entry) => entry.revenue),
            fill: false,
            backgroundColor: 'rgba(153,102,255,0.4)',
            borderColor: 'rgba(153,102,255,1)',
            yAxisID: 'y',
          },
          {
            label: '월별 주차 대수',
            data: monthlyRevenues.map((entry) => entry.parkingCount),
            fill: false,
            backgroundColor: 'rgba(255,206,86,0.4)',
            borderColor: 'rgba(255,206,86,1)',
            yAxisID: 'y1',
          }
        ],
      };

  const options: ChartOptions<'line'> = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        display: true,
        position: 'top' as const,
        labels: {
          color: 'rgb(255, 99, 132)',
          font: {
            size: 14,
          }
        }
      },
      tooltip: {
        enabled: true,
        backgroundColor: 'rgba(0,0,0,0.7)',
        titleFont: {
          size: 16,
          weight: 'bold' as const,
        },
        bodyFont: {
          size: 14,
        },
        footerFont: {
          size: 12,
        },
        callbacks: {
          label: function(context) {
            let label = context.dataset.label || '';
            if (label) {
              label += ': ';
            }
            label += Math.round(context.parsed.y * 100) / 100;
            return label;
          }
        }
      },
    },
    scales: {
      x: {
        grid: {
          display: false,
        },
        ticks: {
          color: 'black',  // 날짜 색을 검은색으로 변경
          font: {
            size: 14,
          }
        }
      },
      y: {
        type: 'linear',
        display: true,
        position: 'left',
        grid: {
          color: 'rgba(200,200,200,0.2)',
        },
        ticks: {
          color: mode === 'daily' ? 'rgba(75,192,192,1)' : 'rgba(153,102,255,1)', // 일별, 월별 그래프 색깔 맞춤
          font: {
            size: 14,
          }
        }
      },
      y1: {
        type: 'linear',
        display: true,
        position: 'right',
        grid: {
          drawOnChartArea: false,
        },
        ticks: {
          color: mode === 'daily' ? 'rgba(255,159,64,1)' : 'rgba(255,206,86,1)', // 일별, 월별 그래프 색깔 맞춤
          font: {
            size: 14,
          }
        }
      },
    },
  };

  return (
    <div>
      <div className={styles.buttonContainer}>
        <button
          className={`${styles.toggleButton} ${styles.daily} ${mode === 'daily' ? styles.active : ''}`}
          onClick={() => toggleMode('daily')}
        >
          일별
        </button>
        <button
          className={`${styles.toggleButton} ${styles.monthly} ${mode === 'monthly' ? styles.active : ''}`}
          onClick={() => toggleMode('monthly')}
        >
          월별
        </button>
      </div>
      <div style={{ width: '800px', height: '400px' }}>
        <Line data={data} options={options} />
      </div>
    </div>
  );
};

export default Graph;
