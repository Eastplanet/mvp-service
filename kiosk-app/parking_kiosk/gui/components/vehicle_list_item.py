import base64
from datetime import datetime
from PyQt6.QtWidgets import QWidget, QHBoxLayout, QLabel, QVBoxLayout, QSpacerItem, QSizePolicy
from PyQt6.QtGui import QPixmap
from PyQt6.QtCore import Qt

class VehicleListItem(QWidget):
    def __init__(self, imageBase64, licensePlate, entranceTimeStr, main_window, parent=None):
        super(VehicleListItem, self).__init__(parent)
        self.main_window = main_window
        self.imageBase64 = imageBase64
        self.licensePlate = licensePlate
        try:
            self.entranceTime = datetime.strptime(entranceTimeStr, '%Y-%m-%dT%H:%M:%S.%f')
        except ValueError:
            self.entranceTime = datetime.strptime(entranceTimeStr, '%Y-%m-%dT%H:%M:%S')
        
        # 입차 시간과 현재 시간의 차이 계산
        self.duration = datetime.now() - self.entranceTime
        hours, remainder = divmod(self.duration.seconds, 3600)
        minutes = remainder // 60
        duration_str = f"{hours}시간 {minutes}분"
        
        # 흰색 배경의 빈 위젯 생성
        container_widget = QWidget(self)
        container_widget.setStyleSheet("""
            background-color: white;
            border-radius: 15px;
            border: 1px solid #ddd;
            padding: 10px;
        """)

        # 외부 레이아웃을 빈 위젯의 레이아웃으로 설정
        outer_layout = QHBoxLayout(container_widget)
        container_widget.setLayout(outer_layout)
        
        # Base64 이미지를 QPixmap으로 변환 또는 대체 이미지 사용
        if imageBase64:
            image_data = base64.b64decode(imageBase64)
            pixmap = QPixmap()
            pixmap.loadFromData(image_data)
        else:
            pixmap = QPixmap('parking_kiosk/gui/res/test-image1.png')  # 대체 이미지 경로

        pixmap = pixmap.scaled(80, 80, Qt.AspectRatioMode.KeepAspectRatio, Qt.TransformationMode.SmoothTransformation)

        # 차량 이미지
        image_label = QLabel(self)
        image_label.setPixmap(pixmap)
        image_label.setFixedSize(100, 100)
        image_label.setStyleSheet("border-radius: 10px; background-color: white;")
        outer_layout.addWidget(image_label)
        
        # 차량 정보 레이아웃
        info_layout = QVBoxLayout()
        info_layout.setContentsMargins(10, 0, 10, 0)
        plate_label = QLabel(licensePlate, self)
        plate_label.setStyleSheet("font-size: 18px; color: #333; font-weight: bold;")
        duration_label = QLabel(duration_str, self)
        duration_label.setStyleSheet("font-size: 16px; color: #666;")
        info_layout.addWidget(plate_label)
        info_layout.addWidget(duration_label)
        outer_layout.addLayout(info_layout)

        # Spacer를 추가하여 항목 간의 간격을 조정합니다.
        spacer = QSpacerItem(20, 20, QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Minimum)
        outer_layout.addSpacerItem(spacer)

        # 전체 레이아웃을 설정하여 container_widget을 추가
        main_layout = QVBoxLayout(self)
        main_layout.addWidget(container_widget)
        self.setLayout(main_layout)
        
        # vehicle info
        self.vehicle_info = {
            'image': imageBase64,
            'license_plate': licensePlate,
            'duration': duration_str,
            'entry_time': entranceTimeStr,
            'exit_time': datetime.now().isoformat(),
            'fee_type': '일반',
            'parking_fee': '3,500원',
            'discount_fee': '-1,000원',
            'total_fee': '2,500원'
        }
        
        # 클릭 이벤트 연결
        self.mousePressEvent = self.on_click
        
    def on_click(self, event):
        self.main_window.show_settlement_page(self.vehicle_info)