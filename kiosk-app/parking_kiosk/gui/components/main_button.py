from PyQt6.QtWidgets import QWidget, QVBoxLayout, QHBoxLayout, QLabel, QGraphicsDropShadowEffect, QPushButton
from PyQt6.QtGui import QPixmap
from PyQt6.QtCore import Qt, QSize

class HoverButton(QPushButton):
    def __init__(self, text, icon_path, color, hover_color, parent=None):
        super(HoverButton, self).__init__(parent)
        self.default_color = color
        self.hover_color = hover_color

        self.setFixedSize(600, 300)  # 버튼 크기 설정

        # 레이아웃 설정
        layout = QHBoxLayout(self)
        layout.setContentsMargins(40, 20, 40, 20)  # 테두리 안쪽 여백 설정
        layout.setSpacing(10)  # 이미지와 텍스트 사이의 간격

        # 이미지 라벨
        self.image_label = QLabel(self)
        pixmap = QPixmap(icon_path).scaled(200, 200, Qt.AspectRatioMode.KeepAspectRatio)
        self.image_label.setPixmap(pixmap)
        self.image_label.setStyleSheet(f"""
            background-color: {self.default_color};
            padding: 10px;
        """)
        layout.addWidget(self.image_label)

        # 텍스트 라벨
        self.text_label = QLabel(text, self)
        self.text_label.setStyleSheet(f"""
            QLabel {{
                color: white;
                font-size: 120px;
                background-color: {self.default_color};
                padding: 10px;
            }}
        """)
        self.text_label.setAlignment(Qt.AlignmentFlag.AlignCenter)
        layout.addWidget(self.text_label)

        self.setGraphicsEffect(self.create_shadow())
        self.setStyleSheet(f"""
            QPushButton {{
                background-color: {self.default_color}; 
                border-radius: 15px;
                border: 3px solid #FFF;
            }}
        """)

    def create_shadow(self):
        shadow = QGraphicsDropShadowEffect()
        shadow.setBlurRadius(15)
        shadow.setOffset(0, 0)
        shadow.setColor(Qt.GlobalColor.black)
        return shadow

    def enterEvent(self, event):
        self.setStyleSheet(f"""
            QPushButton {{
                background-color: {self.hover_color}; 
                border-radius: 15px;
                border: 3px solid #FFF;
            }}
        """)
        self.image_label.setStyleSheet(f"""
            background-color: {self.hover_color};
            padding: 10px;
        """)
        self.text_label.setStyleSheet(f"""
            QLabel {{
                color: white;
                font-size: 120px;
                background-color: {self.hover_color};
                padding: 10px;
            }}
        """)
        super().enterEvent(event)

    def leaveEvent(self, event):
        self.setStyleSheet(f"""
            QPushButton {{
                background-color: {self.default_color}; 
                border-radius: 15px;
                border: 3px solid #FFF;
            }}
        """)
        self.image_label.setStyleSheet(f"""
            background-color: {self.default_color};
            padding: 10px;
        """)
        self.text_label.setStyleSheet(f"""
            QLabel {{
                color: white;
                font-size: 120px;
                background-color: {self.default_color};
                padding: 10px;
            }}
        """)
        super().leaveEvent(event)

class MainButton(QWidget):
    def __init__(self, parent=None):
        super(MainButton, self).__init__(parent)

        layout = QVBoxLayout()
        self.setLayout(layout)

        # 입차 버튼
        self.entry_button = HoverButton("입 차", "parking_kiosk/gui/res/entrance-image.png", "#4689A7", "#3D7B93", self)
        layout.addWidget(self.entry_button, alignment=Qt.AlignmentFlag.AlignCenter)
        
        layout.addSpacing(100)

        # 출차 버튼
        self.exit_button = HoverButton("출 차", "parking_kiosk/gui/res/exit-image.png", "#FFB300", "#FFA000", self)
        layout.addWidget(self.exit_button, alignment=Qt.AlignmentFlag.AlignCenter)