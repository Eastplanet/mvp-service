from PyQt6.QtWidgets import QWidget, QVBoxLayout, QPushButton, QGraphicsDropShadowEffect
from PyQt6.QtCore import Qt

class HoverButton(QPushButton):
    def __init__(self, text, color, hover_color, parent=None):
        super(HoverButton, self).__init__(text, parent)
        self.default_color = color
        self.hover_color = hover_color

        # 버튼 크기와 폰트 크기 설정
        self.setFixedSize(400, 100)  # 버튼 크기를 절대값으로 설정
        self.setStyleSheet(f"""
            QPushButton {{
                background-color: {self.default_color}; 
                color: white; 
                font-size: 30px;
                border-radius: 9px;
                border: 3px solid #FFF;
            }}
        """)
        self.setGraphicsEffect(self.create_shadow())

    def create_shadow(self):
        shadow = QGraphicsDropShadowEffect()
        shadow.setBlurRadius(15)  # 그림자 효과 절대값 설정
        shadow.setOffset(0, 0)
        shadow.setColor(Qt.GlobalColor.black)
        return shadow

    def enterEvent(self, event):
        self.setStyleSheet(f"""
            QPushButton {{
                background-color: {self.hover_color}; 
                color: white; 
                font-size: 30px; 
                border-radius: 9px; 
                border: 3px solid #FFF;
                font-weight: bold;
            }}
        """)
        super().enterEvent(event)

    def leaveEvent(self, event):
        self.setStyleSheet(f"""
            QPushButton {{
                background-color: {self.default_color}; 
                color: white; 
                font-size: 24px; 
                border-radius: 9px; 
                border: 3px solid #FFF;
                font-weight: bold;
            }}
        """)
        super().leaveEvent(event)

class MainButton(QWidget):
    def __init__(self, parent=None):
        super(MainButton, self).__init__(parent)

        layout = QVBoxLayout()
        self.setLayout(layout)

        # 입차 버튼
        self.entry_button = HoverButton("입차", "#FFB300", "#FFA000", self)
        layout.addWidget(self.entry_button, alignment=Qt.AlignmentFlag.AlignCenter)
        
        layout.addSpacing(100)  # 버튼 간의 간격 절대값 설정

        # 출차 버튼
        self.exit_button = HoverButton("출차", "#FF0000", "#CC0000", self)
        layout.addWidget(self.exit_button, alignment=Qt.AlignmentFlag.AlignCenter)
