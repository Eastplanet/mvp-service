import base64
from PyQt6.QtWidgets import QWidget, QVBoxLayout, QLabel, QPushButton, QSpacerItem, QSizePolicy, QHBoxLayout
from PyQt6.QtGui import QPixmap
from PyQt6.QtCore import Qt
from PyQt6.QtCore import QTimer
from gui.components.gif_widget import GifWidget

class SettlementPage(QWidget):
    def __init__(self, vehicle_info, main_window, parent=None):
        super(SettlementPage, self).__init__(parent)
        self.main_window = main_window

        layout = QVBoxLayout()
        self.setLayout(layout)

        # 상단 라벨
        top_label = QLabel("차량을 선택하세요", self)
        top_label.setAlignment(Qt.AlignmentFlag.AlignCenter)
        top_label.setStyleSheet("color: white; font-size: 24px;")
        layout.addWidget(top_label, alignment=Qt.AlignmentFlag.AlignCenter)

        # 정산 정보 컨테이너
        container_widget = QWidget(self)
        container_widget.setStyleSheet("background-color: white; border-radius: 15px; border: 1px solid white;")
        container_layout = QVBoxLayout(container_widget)

        # 차량 이미지 및 정보
        if vehicle_info['image']:
            image_data = base64.b64decode(vehicle_info['image'])
            pixmap = QPixmap()
            pixmap.loadFromData(image_data)
        else:
            pixmap = QPixmap('parking_kiosk/gui/res/placeholder.png')  # 대체 이미지 경로

        pixmap = pixmap.scaled(80, 80, Qt.AspectRatioMode.KeepAspectRatio, Qt.TransformationMode.SmoothTransformation)


        header_layout = QHBoxLayout()
        image_label = QLabel(self)
        image_label.setPixmap(pixmap)
        image_label.setFixedSize(100, 100)
        image_label.setStyleSheet("border-radius: 10px; background-color: white;")
        header_layout.addWidget(image_label)

        info_layout = QVBoxLayout()
        plate_label = QLabel(vehicle_info['license_plate'], self)
        plate_label.setStyleSheet("font-size: 18px; color: black; font-weight: bold;")
        duration_label = QLabel(vehicle_info['duration'], self)
        duration_label.setStyleSheet("font-size: 16px; color: black;")
        info_layout.addWidget(plate_label)
        info_layout.addWidget(duration_label)
        header_layout.addLayout(info_layout)

        container_layout.addLayout(header_layout)

        # 정산 세부 정보
        details_layout = QVBoxLayout()

        details = [
            ("입차일시", vehicle_info['entry_time']),
            ("출차일시", vehicle_info['exit_time']),
            ("주차시간", vehicle_info['duration']),
            ("요금종별", vehicle_info['fee_type']),
            ("주차요금", vehicle_info['parking_fee']),
            ("할인요금", vehicle_info['discount_fee']),
            ("정산요금", vehicle_info['total_fee'])
        ]

        for label, value in details:
            detail_layout = QHBoxLayout()
            detail_label = QLabel(label, self)
            detail_label.setStyleSheet("font-size: 16px; color: black;")
            detail_value = QLabel(value, self)
            detail_value.setStyleSheet("font-size: 16px; color: black;")
            detail_layout.addWidget(detail_label)
            detail_layout.addWidget(detail_value)
            details_layout.addLayout(detail_layout)

        container_layout.addLayout(details_layout)
        layout.addWidget(container_widget)

        # Spacer
        spacer = QSpacerItem(20, 40, QSizePolicy.Policy.Minimum, QSizePolicy.Policy.Expanding)
        layout.addSpacerItem(spacer)

        # 정산하기 버튼
        settle_button = QPushButton("정산하기", self)
        settle_button.setFixedSize(200, 60)
        settle_button.setStyleSheet("""
            background-color: #FFB300; 
            color: white; 
            font-size: 20px; 
            border-radius: 5px;
        """)
        settle_button.clicked.connect(self.show_gif_widget)
        layout.addWidget(settle_button, alignment=Qt.AlignmentFlag.AlignCenter)

        # 뒤로가기 버튼
        # back_button = QPushButton(self)
        # back_button.setFixedSize(60, 60)
        # back_button.setStyleSheet("""
        #     background-color: #FFB300; 
        #     font-size: 30px; 
        #     border-radius: 30px;
        # """)
        # back_button.setText("↩")
        # back_button.clicked.connect(self.go_back)
        # layout.addWidget(back_button, alignment=Qt.AlignmentFlag.AlignRight)

    def go_back(self):
        self.parent().stacked_widget.setCurrentWidget(self.parent().exit_page)
        
    def show_gif_widget(self):
        gif_widget = GifWidget("parking_kiosk/gui/res/car-anime.gif", duration=3000, parent=self)
        gif_widget.move(self.rect().center() - gif_widget.rect().center())
        gif_widget.start()
        QTimer.singleShot(3000, self.return_to_main)
        
    def return_to_main(self):
        self.main_window.stacked_widget.setCurrentWidget(self.main_window.main_button)