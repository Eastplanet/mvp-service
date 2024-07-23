# gui/start_window.py

from PyQt5.QtWidgets import QWidget, QVBoxLayout, QPushButton

class StartWindow(QWidget):
    def __init__(self, main_window):
        super().__init__()
        self.main_window = main_window
        self.initUI()

    def initUI(self):
        layout = QVBoxLayout()

        self.enter_button = QPushButton('입차', self) # html
        self.enter_button.setStyleSheet("font-size: 24px; padding: 10px;") # css
        self.enter_button.clicked.connect(self.show_enter_window) # js

        self.exit_button = QPushButton('출차', self)
        self.exit_button.setStyleSheet("font-size: 24px; padding: 10px;")
        self.exit_button.clicked.connect(self.show_exit_window)

        layout.addWidget(self.enter_button)
        layout.addWidget(self.exit_button)

        self.setLayout(layout)

    def show_enter_window(self):
        self.main_window.show_enter_window()

    def show_exit_window(self):
        self.main_window.show_exit_window()
