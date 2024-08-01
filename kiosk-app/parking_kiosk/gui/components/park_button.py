from PyQt6.QtWidgets import QPushButton, QWidget, QHBoxLayout
from PyQt6.QtCore import Qt

class ParkButton(QWidget):
    def __init__(self, parent=None):
        super(ParkButton, self).__init__(parent)
        self.layout = QHBoxLayout()
        self.setLayout(self.layout)
        
        self.button = QPushButton("자동 주차하기", self)
        self.button.setFixedSize(200, 60)
        self.button.setStyleSheet("""
            background-color: #FFB300; 
            color: white; 
            font-size: 20px; 
            border-radius: 5px;
        """)
        self.layout.addWidget(self.button, alignment=Qt.AlignmentFlag.AlignCenter)

    @property
    def clicked(self):
        return self.button.clicked