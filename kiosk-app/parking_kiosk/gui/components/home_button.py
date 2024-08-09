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
        
        # 홈 버튼의 크기를 절대값으로 설정
        button_size = 80  # 홈 버튼의 크기
        icon_size = 60  # 아이콘 크기
        
        self.home_button = QPushButton(self)
        self.home_button.setIcon(QIcon("parking_kiosk/gui/res/home-icon.png"))  # 홈 아이콘 경로 설정
        self.home_button.setIconSize(QSize(icon_size, icon_size))  # 아이콘 크기 설정
        self.home_button.setFixedSize(button_size, button_size)  # 버튼 크기 설정
        self.home_button.setStyleSheet("border: none;")
        self.home_button.clicked.connect(self.go_home)
        
        layout.addWidget(self.home_button, alignment=Qt.AlignmentFlag.AlignLeft)

    def go_home(self):
        if self.main_window:
            self.main_window.return_to_main()
