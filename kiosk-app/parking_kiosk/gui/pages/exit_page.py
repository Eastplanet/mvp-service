from PyQt6.QtWidgets import QWidget, QVBoxLayout, QLabel, QHBoxLayout, QSpacerItem, QSizePolicy
from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QPushButton
from gui.components.keypad import Keypad
from gui.components.exit_number_plate import ExitNumberPlate

class ExitPage(QWidget):
    def __init__(self, main_window, parent=None):
        super(ExitPage, self).__init__(parent)
        self.main_window = main_window

        layout = QVBoxLayout()
        self.setLayout(layout)

        # 상단 라벨
        top_label = QLabel("차량번호를 입력하세요", self)
        top_label.setAlignment(Qt.AlignmentFlag.AlignCenter)
        top_label.setStyleSheet("color: white; font-size: 24px;")
        layout.addWidget(top_label, alignment=Qt.AlignmentFlag.AlignCenter)

        # 차량 번호 레이아웃
        self.number_plate_labels = ExitNumberPlate(parent=self)
        layout.addWidget(self.number_plate_labels, alignment=Qt.AlignmentFlag.AlignCenter)

        # 키패드
        self.keypad = Keypad(self.number_plate_labels, main_window=self.main_window, parent=self, use_number_keypad=True, mode='exit')
        layout.addWidget(self.keypad, alignment=Qt.AlignmentFlag.AlignCenter)

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
        self.main_window.stacked_widget.setCurrentWidget(self.main_window.main_button)