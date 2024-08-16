from PyQt6.QtWidgets import QLabel
from PyQt6.QtCore import Qt

class TopLabel(QLabel):
    def __init__(self, parent=None):
        super(TopLabel, self).__init__(parent)
        self.setText("이 차량 번호가 맞나요?")
        self.setAlignment(Qt.AlignmentFlag.AlignCenter)

        # 스타일 조정: 해상도에 맞게 폰트 크기를 48px로 설정
        self.setStyleSheet("color: white; font-size: 48px; font-weight: bold;")

        # 라벨의 크기를 고정하여 해상도에 맞게 조정
        self.setFixedHeight(200)  # 높이를 고정
