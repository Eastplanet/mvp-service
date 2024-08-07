from PyQt6.QtWidgets import QWidget, QVBoxLayout, QPushButton, QApplication, QGraphicsDropShadowEffect
from PyQt6.QtCore import Qt, QPropertyAnimation, QRect

class AnimatedButton(QPushButton):
    def __init__(self, text, color, hover_color, parent=None):
        super(AnimatedButton, self).__init__(text, parent)
        self.default_color = color
        self.hover_color = hover_color
        self.setFixedSize(200, 60)
        self.setStyleSheet(f"""
            QPushButton {{
                background-color: {self.default_color}; 
                color: white; 
                font-size: 20px; 
                border-radius: 10px;
                border: 2px solid #FFF;
            }}
        """)
        self.setGraphicsEffect(self.create_shadow())
        self.animation = None

    def create_shadow(self):
        shadow = QGraphicsDropShadowEffect()
        shadow.setBlurRadius(10)
        shadow.setOffset(0, 0)
        shadow.setColor(Qt.GlobalColor.black)
        return shadow

    def enterEvent(self, event):
        self.animate_button(QRect(self.geometry().x(), self.geometry().y(), 190, 55))
        self.setStyleSheet(f"""
            QPushButton {{
                background-color: {self.hover_color}; 
                color: white; 
                font-size: 20px; 
                border-radius: 10px;
                border: 2px solid #FFF;
            }}
        """)
        super().enterEvent(event)

    def leaveEvent(self, event):
        self.animate_button(QRect(self.geometry().x(), self.geometry().y(), 200, 60))
        self.setStyleSheet(f"""
            QPushButton {{
                background-color: {self.default_color}; 
                color: white; 
                font-size: 20px; 
                border-radius: 10px;
                border: 2px solid #FFF;
            }}
        """)
        super().leaveEvent(event)

    def animate_button(self, geometry):
        if self.animation:
            self.animation.stop()

        self.animation = QPropertyAnimation(self, b"geometry")
        self.animation.setDuration(150)
        self.animation.setStartValue(self.geometry())
        self.animation.setEndValue(geometry)
        self.animation.start()

class MainButton(QWidget):
    def __init__(self, parent=None):
        super(MainButton, self).__init__(parent)

        layout = QVBoxLayout()
        self.setLayout(layout)

        # 입차 버튼
        self.entry_button = AnimatedButton("입차", "#FFB300", "#FFA000", self)
        layout.addWidget(self.entry_button, alignment=Qt.AlignmentFlag.AlignCenter)
        
        layout.addSpacing(30)

        # 출차 버튼
        self.exit_button = AnimatedButton("출차", "#FF0000", "#CC0000", self)
        layout.addWidget(self.exit_button, alignment=Qt.AlignmentFlag.AlignCenter)