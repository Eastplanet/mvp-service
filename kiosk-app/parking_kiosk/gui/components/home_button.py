from PyQt6.QtWidgets import QWidget, QHBoxLayout, QPushButton
from PyQt6.QtGui import QIcon
from PyQt6.QtCore import QSize
from PyQt6.QtCore import Qt

class HomeButton(QWidget):
    def __init__(self, parent=None, main_window=None):
        super(HomeButton, self).__init__(parent)
        self.main_window = main_window
        
        layout = QHBoxLayout()
        self.setLayout(layout)
        
        self.home_button = QPushButton(self)
        self.home_button.setIcon(QIcon("parking_kiosk/gui/res/home-icon.png"))  # 홈 아이콘 경로 설정
        self.home_button.setIconSize(QSize(40, 40))
        self.home_button.setFixedSize(40, 40)
        self.home_button.setStyleSheet("border: none;")
        self.home_button.clicked.connect(self.go_home)
        
        layout.addWidget(self.home_button, alignment=Qt.AlignmentFlag.AlignLeft)

    def go_home(self):
        if self.main_window:
            self.main_window.return_to_main()