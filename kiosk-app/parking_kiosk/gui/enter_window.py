# gui/enter_window.py

from PyQt5.QtWidgets import QWidget, QVBoxLayout, QPushButton, QLabel

class EnterWindow(QWidget):
    def __init__(self, main_window):
        super().__init__()
        self.main_window = main_window
        self.initUI()

    def initUI(self):
        layout = QVBoxLayout()

        self.label = QLabel('번호판: 11가1111', self)
        self.label.setStyleSheet("font-size: 24px; padding: 10px;")

        self.confirm_button = QPushButton('확인', self)
        self.confirm_button.setStyleSheet("font-size: 24px; padding: 10px;")
        self.confirm_button.clicked.connect(self.confirm)

        layout.addWidget(self.label)
        layout.addWidget(self.confirm_button)

        self.setLayout(layout)

    def confirm(self):
        self.main_window.handle_enter()
        self.main_window.show_start_window()
