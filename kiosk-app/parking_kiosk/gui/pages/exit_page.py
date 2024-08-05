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

        # 상단 Spacer
        top_spacer = QSpacerItem(20, 40, QSizePolicy.Policy.Minimum, QSizePolicy.Policy.Expanding)
        layout.addSpacerItem(top_spacer)

        # 상단 라벨
        top_label = QLabel("차량번호를 입력하세요", self)
        top_label.setAlignment(Qt.AlignmentFlag.AlignCenter)
        top_label.setStyleSheet("color: white; font-size: 24px;")
        layout.addWidget(top_label, alignment=Qt.AlignmentFlag.AlignCenter)

        # 상단 Spacer
        layout.addSpacerItem(top_spacer)

        # 차량 번호 레이아웃
        self.number_plate_labels = ExitNumberPlate(parent=self)
        layout.addWidget(self.number_plate_labels, alignment=Qt.AlignmentFlag.AlignCenter)

        # 중간 Spacer
        middle_spacer = QSpacerItem(20, 20, QSizePolicy.Policy.Minimum, QSizePolicy.Policy.Expanding)
        layout.addSpacerItem(middle_spacer)

        # 키패드
        self.keypad = Keypad(self.number_plate_labels, main_window=self.main_window, parent=self, use_number_keypad=True, mode='exit')
        layout.addWidget(self.keypad, alignment=Qt.AlignmentFlag.AlignCenter)

        # 하단 Spacer
        bottom_spacer = QSpacerItem(20, 40, QSizePolicy.Policy.Minimum, QSizePolicy.Policy.Expanding)
        layout.addSpacerItem(bottom_spacer)

        # 하단 Spacer
        layout.addSpacerItem(bottom_spacer)

    def go_back(self):
        self.main_window.stacked_widget.setCurrentWidget(self.main_window.main_button)