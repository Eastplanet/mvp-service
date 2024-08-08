import base64
from datetime import datetime
from PyQt6.QtWidgets import QWidget, QVBoxLayout, QLabel, QPushButton, QSpacerItem, QSizePolicy, QHBoxLayout, QFrame
from PyQt6.QtGui import QPixmap
from PyQt6.QtCore import Qt
from core.handlers import handle_exit
from gui.components.home_button import HomeButton

class SettlementPage(QWidget):
    def __init__(self, vehicle_info, main_window, parent=None):
        super(SettlementPage, self).__init__(parent)
        self.main_window = main_window
        self.vehicle_info = vehicle_info

        layout = QVBoxLayout()
        layout.setContentsMargins(40, 80, 40, 0)
        self.setLayout(layout)

        # 차량 정보 카드
        card = QFrame(self)
        card.setStyleSheet("""
            QFrame {
                background-color: white;
                border-radius: 5px;
                padding: 15px;
            }
        """)
        card_layout = QVBoxLayout(card)
        card_layout.setContentsMargins(0, 0, 0, 0)
        card_layout.setSpacing(5)

        header_layout = QHBoxLayout()
        if vehicle_info['image']:
            image_data = base64.b64decode(vehicle_info['image'])
            pixmap = QPixmap()
            pixmap.loadFromData(image_data)
        else:
            pixmap = QPixmap('parking_kiosk/gui/res/test-image1.png')  # 대체 이미지 경로

        pixmap = pixmap.scaled(100, 100, Qt.AspectRatioMode.KeepAspectRatio, Qt.TransformationMode.SmoothTransformation)
        
        image_label = QLabel(self)
        image_label.setPixmap(pixmap)
        image_label.setFixedSize(150, 100)
        image_label.setStyleSheet("border-radius: 10px;")
        header_layout.addWidget(image_label)

        info_layout = QVBoxLayout()
        plate_label = QLabel(vehicle_info['license_plate'], self)
        plate_label.setStyleSheet("font-size: 18px; color: black; font-weight: bold;")
        plate_label.setAlignment(Qt.AlignmentFlag.AlignLeft)
        duration_label = QLabel(vehicle_info['duration'], self)
        duration_label.setStyleSheet("font-size: 16px; color: black;")
        duration_label.setAlignment(Qt.AlignmentFlag.AlignLeft)
        info_layout.addWidget(plate_label)
        info_layout.addWidget(duration_label)
        header_layout.addLayout(info_layout)

        card_layout.addLayout(header_layout)
        
        # 구분선 추가
        line = QFrame(self)
        line.setFrameShape(QFrame.Shape.HLine)
        line.setFrameShadow(QFrame.Shadow.Sunken)
        line.setStyleSheet("color: gray;")
        card_layout.addWidget(line)

        # 정산 세부 정보
        details_layout = QVBoxLayout()
        details_layout.setSpacing(2)

        details = [
            ("입차일시", self.format_datetime(vehicle_info['entry_time'])),
            ("출차일시", self.format_datetime(vehicle_info['exit_time'])),
            ("주차시간", vehicle_info['duration']),
            ("요금종별", vehicle_info['fee_type']),
            ("주차요금", vehicle_info['parking_fee']),
            ("할인요금", vehicle_info['discount_fee']),
            ("정산요금", vehicle_info['total_fee'])  # 정산 요금 추가
        ]

        for label, value in details:
            detail_frame = QFrame(self)
            detail_frame.setStyleSheet("background-color: white; border-radius: 5px; padding: 2px;")
            detail_layout = QHBoxLayout(detail_frame)
            detail_layout.setContentsMargins(30, 0, 30, 0)

            detail_label = QLabel(label, self)
            detail_label.setStyleSheet("font-size: 14px; color: black; font-weight: bold;")
            detail_label.setAlignment(Qt.AlignmentFlag.AlignLeft)
            detail_value = QLabel(value, self)
            if label == "정산요금":
                detail_label.setStyleSheet("font-size: 18px; color: black; font-weight: bold;")
                detail_value.setStyleSheet("font-size: 18px; color: red; font-weight: bold;")
            else:
                detail_value.setStyleSheet("font-size: 14px; color: black; font-weight: bold;")
            detail_value.setAlignment(Qt.AlignmentFlag.AlignRight)
            detail_layout.addWidget(detail_label)
            detail_layout.addStretch()
            detail_layout.addWidget(detail_value)

            details_layout.addWidget(detail_frame)

        card_layout.addLayout(details_layout)
        layout.addWidget(card)

        # 정산하기 버튼
        settle_button = QPushButton("정산하기", self)
        settle_button.setFixedSize(200, 60)
        settle_button.setStyleSheet("""
            QPushButton {
                background-color: #FFB300; 
                color: white; 
                font-size: 20px; 
                border-radius: 5px;
            }
            QPushButton:hover {
                background-color: #FFA000;
            }
        """)
        settle_button.clicked.connect(self.confirm_settle)
        layout.addWidget(settle_button, alignment=Qt.AlignmentFlag.AlignCenter)

    def format_datetime(self, datetime_str):
        dt = datetime.fromisoformat(datetime_str)
        return dt.strftime("%m-%d %H:%M")

    def confirm_settle(self):
        # 정산 핸들러 실행
        success = handle_exit(self.vehicle_info['license_plate'])
        # 정산 완료 시 메인페이지로 전환
        if success:
            self.main_window.return_to_main()