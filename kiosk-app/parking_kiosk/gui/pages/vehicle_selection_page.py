from PyQt6.QtWidgets import QWidget, QVBoxLayout, QLabel, QSpacerItem, QSizePolicy, QPushButton, QScrollArea, QFrame, QHBoxLayout
from PyQt6.QtCore import Qt
from gui.components.vehicle_list_item import VehicleListItem
from gui.components.home_button import HomeButton

class VehicleSelectionPage(QWidget):
    def __init__(self, vehicles, main_window, parent=None):
        super(VehicleSelectionPage, self).__init__(parent)
        self.main_window = main_window

        layout = QVBoxLayout()
        layout.setContentsMargins(20, 20, 20, 20)
        layout.setSpacing(20)
        self.setLayout(layout)
        
        # 상단 라벨
        top_label = QLabel("차량을 선택하세요", self)
        top_label.setAlignment(Qt.AlignmentFlag.AlignCenter)
        top_label.setStyleSheet("color: white; font-size: 24px; font-weight: bold;")
        layout.addWidget(top_label, alignment=Qt.AlignmentFlag.AlignCenter)

        # Spacer
        top_spacer = QSpacerItem(20, 20, QSizePolicy.Policy.Minimum, QSizePolicy.Policy.Expanding)
        layout.addSpacerItem(top_spacer)

        # 스크롤 영역 설정
        scroll_area = QScrollArea()
        scroll_area.setWidgetResizable(True)
        scroll_area.setFrameShape(QFrame.Shape.NoFrame)
        scroll_area.setStyleSheet("""
            QScrollArea {
                border: none;
            }
            QScrollBar:vertical {
                border: none;
                background: #2E3348;
                width: 14px;
                margin: 0px 0px 0px 0px;
            }
            QScrollBar::handle:vertical {
                background: #FFB300;
                min-height: 20px;
                border-radius: 7px;
            }
            QScrollBar::add-line:vertical, QScrollBar::sub-line:vertical {
                height: 0px;
                subcontrol-position: none;
                subcontrol-origin: margin;
            }
            QScrollBar::add-page:vertical, QScrollBar::sub-page:vertical {
                background: none;
            }
        """)

        # 스크롤 영역 내의 위젯 설정
        scroll_widget = QWidget()
        scroll_layout = QVBoxLayout()
        scroll_layout.setContentsMargins(0, 0, 0, 0)
        scroll_layout.setSpacing(10)
        scroll_widget.setLayout(scroll_layout)

        # 차량 리스트 항목 추가
        for vehicle in vehicles:
            item = VehicleListItem(vehicle['image'], vehicle['licensePlate'], vehicle['entranceTime'], main_window, self)
            scroll_layout.addWidget(item)

        scroll_area.setWidget(scroll_widget)
        layout.addWidget(scroll_area)

        # Spacer
        bottom_spacer = QSpacerItem(20, 40, QSizePolicy.Policy.Minimum, QSizePolicy.Policy.Expanding)
        layout.addSpacerItem(bottom_spacer)