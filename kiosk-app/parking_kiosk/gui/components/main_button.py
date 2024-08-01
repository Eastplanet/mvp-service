from PyQt6.QtWidgets import QWidget, QVBoxLayout, QPushButton
from PyQt6.QtCore import Qt

class MainButton(QWidget):
    def __init__(self, parent=None):
        super(MainButton, self).__init__(parent)

        layout = QVBoxLayout()
        self.setLayout(layout)

        # 입차 버튼
        self.entry_button = QPushButton("입차", self)
        self.entry_button.setFixedSize(200, 60)
        self.entry_button.setStyleSheet("""
            background-color: #FFB300; 
            color: white; 
            font-size: 20px; 
            border-radius: 10px;
        """)
        layout.addWidget(self.entry_button, alignment=Qt.AlignmentFlag.AlignCenter)

        # 출차 버튼
        self.exit_button = QPushButton("출차", self)
        self.exit_button.setFixedSize(200, 60)
        self.exit_button.setStyleSheet("""
            background-color: #FF0000; 
            color: white; 
            font-size: 20px; 
            border-radius: 10px;
        """)
        layout.addWidget(self.exit_button, alignment=Qt.AlignmentFlag.AlignCenter)