from PyQt6.QtWidgets import QWidget, QHBoxLayout, QLabel, QVBoxLayout, QSpacerItem, QSizePolicy
from PyQt6.QtGui import QPixmap
from PyQt6.QtCore import Qt

class VehicleListItem(QWidget):
    def __init__(self, image_path, plate_number, duration, main_window, parent=None):
        super(VehicleListItem, self).__init__(parent)
        self.main_window = main_window

        # 흰색 배경의 빈 위젯 생성
        container_widget = QWidget(self)
        container_widget.setStyleSheet("background-color: white; border-radius: 15px; border: 1px solid white;")  # 테두리 색상을 흰색으로 설정

        # 외부 레이아웃을 빈 위젯의 레이아웃으로 설정
        outer_layout = QHBoxLayout(container_widget)
        container_widget.setLayout(outer_layout)
        
        # 차량 이미지
        pixmap = QPixmap(image_path).scaled(80, 80, Qt.AspectRatioMode.KeepAspectRatio, Qt.TransformationMode.SmoothTransformation)
        image_label = QLabel(self)
        image_label.setPixmap(pixmap)
        image_label.setFixedSize(100, 100)
        image_label.setStyleSheet("border-radius: 10px; background-color: white;")
        outer_layout.addWidget(image_label)

        # 차량 정보 레이아웃
        info_layout = QVBoxLayout()
        info_layout.setContentsMargins(10, 0, 10, 0)
        plate_label = QLabel(plate_number, self)
        plate_label.setStyleSheet("font-size: 18px; color: black; font-weight: bold;")
        duration_label = QLabel(duration, self)
        duration_label.setStyleSheet("font-size: 16px; color: black;")
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
            'image_path': 'parking_kiosk\\gui\\res\\test-image1.png',
            'plate_number': '19오 7777',
            'duration': '43분',
            'entry_time': '07-23 20:03',
            'exit_time': '07-23 20:46',
            'parking_duration': '00:43',
            'fee_type': '일반',
            'parking_fee': '3,500원',
            'discount_fee': '-1,000원',
            'total_fee': '2,500원'
        }
        
        # 클릭 이벤트 연결
        self.mousePressEvent = self.on_click
        
    def on_click(self, event):
        self.main_window.show_settlement_page(self.vehicle_info)
        
        
        
    
        