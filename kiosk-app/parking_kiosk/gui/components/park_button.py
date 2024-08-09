from PyQt6.QtWidgets import QPushButton, QWidget, QHBoxLayout
from PyQt6.QtCore import Qt

class ParkButton(QWidget):
    def __init__(self, parent=None):
        super(ParkButton, self).__init__(parent)
        self.layout = QHBoxLayout()
        self.setLayout(self.layout)
        
        # 버튼 크기와 스타일을 1080x1920 해상도에 맞게 조정
        self.button = QPushButton("자동 주차하기", self)
        self.button.setFixedSize(300, 100)  # 버튼 크기를 키움
        self.button.setStyleSheet("""
            QPushButton {
                background-color: #FFB300; 
                color: white; 
                font-size: 36px;
                border-radius: 10px;
            }
            QPushButton:hover {
                background-color: #FFA000;
            }
        """)
        self.layout.addWidget(self.button, alignment=Qt.AlignmentFlag.AlignCenter)

    @property
    def clicked(self):
        return self.button.clicked
