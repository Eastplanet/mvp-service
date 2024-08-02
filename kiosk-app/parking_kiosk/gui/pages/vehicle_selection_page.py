from PyQt6.QtWidgets import QWidget, QVBoxLayout, QLabel, QSpacerItem, QSizePolicy, QPushButton
from PyQt6.QtCore import Qt
from gui.components.vehicle_list_item import VehicleListItem

class VehicleSelectionPage(QWidget):
    def __init__(self, vehicles, main_window, parent=None):
        super(VehicleSelectionPage, self).__init__(parent)

        layout = QVBoxLayout()
        self.setLayout(layout)

        # 상단 라벨
        top_label = QLabel("차량을 선택하세요", self)
        top_label.setAlignment(Qt.AlignmentFlag.AlignCenter)
        top_label.setStyleSheet("color: white; font-size: 24px;")
        layout.addWidget(top_label, alignment=Qt.AlignmentFlag.AlignCenter)

        # 차량 리스트 항목 추가
        for vehicle in vehicles:
            item = VehicleListItem(vehicle['image'], vehicle['licensePlate'], vehicle['entranceTime'], main_window, self)
            layout.addWidget(item)
            layout.addSpacing(10)  # 항목 간의 간격 추가

        # Spacer
        spacer = QSpacerItem(20, 40, QSizePolicy.Policy.Minimum, QSizePolicy.Policy.Expanding)
        layout.addSpacerItem(spacer)

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