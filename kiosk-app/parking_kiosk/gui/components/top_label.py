from PyQt6.QtWidgets import QLabel
from PyQt6.QtCore import Qt

class TopLabel(QLabel):
    def __init__(self, parent=None):
        super(TopLabel, self).__init__(parent)
        self.setText("이 차량 번호가 맞나요?")
        self.setAlignment(Qt.AlignmentFlag.AlignCenter)
        self.setStyleSheet("color: white; font-size: 24px; font-weight: bold;")