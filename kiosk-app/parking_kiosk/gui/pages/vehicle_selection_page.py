from PyQt6.QtWidgets import QWidget, QVBoxLayout, QLabel, QSpacerItem, QSizePolicy, QScrollArea, QFrame
from PyQt6.QtCore import Qt

from gui.components.vehicle_list_item import VehicleListItem
from gui.components.home_button import HomeButton

class VehicleSelectionPage(QWidget):
    def __init__(self, vehicles, main_window, parent=None):
        super(VehicleSelectionPage, self).__init__(parent)
        self.main_window = main_window

        layout = QVBoxLayout()
        layout.setContentsMargins(20, 40, 20, 20)  # 여백을 줄여서 스크롤 영역을 더 크게 만듦
        self.setLayout(layout)
        
        # 상단 라벨
        top_label = QLabel("차량을 선택하세요", self)
        top_label.setAlignment(Qt.AlignmentFlag.AlignCenter)
        top_label.setStyleSheet("color: white; font-size: 36px; font-weight: bold;")
        layout.addWidget(top_label, alignment=Qt.AlignmentFlag.AlignCenter)

        # 상단 Spacer (크기 축소)
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
                width: 20px;
                margin: 0px 0px 0px 0px;
            }
            QScrollBar::handle:vertical {
                background: #FFB300;
                min-height: 30px;
                border-radius: 10px;
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
        scroll_layout.setSpacing(20)  # 항목 간 간격 조정
        scroll_widget.setLayout(scroll_layout)

        # 차량 리스트 항목 추가
        for vehicle in vehicles:
            item = VehicleListItem(vehicle, main_window, self)
            scroll_layout.addWidget(item)
            scroll_layout.addWidget(item, alignment=Qt.AlignmentFlag.AlignCenter)

        scroll_area.setWidget(scroll_widget)
        
        # 스크롤 영역을 메인 레이아웃에 추가하고, 크기를 키움
        layout.addWidget(scroll_area, stretch=1)

        # 하단 Spacer (크기 축소)
        bottom_spacer = QSpacerItem(20, 20, QSizePolicy.Policy.Minimum, QSizePolicy.Policy.Expanding)
        layout.addSpacerItem(bottom_spacer)
