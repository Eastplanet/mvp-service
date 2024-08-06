from PyQt6.QtWidgets import QWidget, QVBoxLayout, QHBoxLayout, QLabel, QPushButton, QSpacerItem, QSizePolicy, QGraphicsDropShadowEffect
from PyQt6.QtCore import Qt
from PyQt6.QtGui import QColor, QPixmap

class ErrorDialog(QWidget):
    def __init__(self, message, parent=None):
        super(ErrorDialog, self).__init__(parent)
        self.setWindowTitle("오류")
        self.setFixedSize(350, 200)
        self.setWindowFlags(Qt.WindowType.Dialog | Qt.WindowType.FramelessWindowHint)
        self.setAttribute(Qt.WidgetAttribute.WA_TranslucentBackground)  # 투명 배경 설정
        self.image_path = "parking_kiosk/gui/res/warning.png"
        
        # 메인 레이아웃
        main_layout = QVBoxLayout(self)
        main_layout.setContentsMargins(10, 10, 10, 10)

        # 컨테이너 위젯 (둥근 테두리와 그림자 효과를 적용할 위젯)
        container = QWidget(self)
        container.setStyleSheet("background-color: white; border-radius: 10px;")
        layout = QVBoxLayout(container)
        layout.setContentsMargins(20, 20, 20, 20)

        # 그림자 효과 추가
        shadow = QGraphicsDropShadowEffect()
        shadow.setBlurRadius(15)
        shadow.setColor(QColor(0, 0, 0, 160))
        shadow.setOffset(0, 0)
        container.setGraphicsEffect(shadow)

        # 이미지와 메시지를 담을 레이아웃
        message_layout = QHBoxLayout()

        # 에러 이미지
        error_image_label = QLabel(self)
        pixmap = QPixmap(self.image_path)
        pixmap = pixmap.scaled(50, 50, Qt.AspectRatioMode.KeepAspectRatio, Qt.TransformationMode.SmoothTransformation)
        error_image_label.setPixmap(pixmap)
        error_image_label.setFixedSize(50, 50)
        error_image_label.setAlignment(Qt.AlignmentFlag.AlignCenter)
        message_layout.addWidget(error_image_label, alignment=Qt.AlignmentFlag.AlignLeft)

        # 오류 메시지
        error_label = QLabel(message, self)
        error_label.setStyleSheet("font-size: 16px; color: red; font-weight: bold;")
        error_label.setAlignment(Qt.AlignmentFlag.AlignCenter)
        message_layout.addWidget(error_label, alignment=Qt.AlignmentFlag.AlignCenter)

        layout.addLayout(message_layout)

        # Spacer
        layout.addSpacerItem(QSpacerItem(20, 40, QSizePolicy.Policy.Minimum, QSizePolicy.Policy.Expanding))

        # 확인 버튼
        ok_button = QPushButton("확인", self)
        ok_button.setFixedSize(100, 40)
        ok_button.setStyleSheet("""
            QPushButton {
                background-color: #FFB300; 
                color: white; 
                font-size: 16px; 
                border-radius: 5px;
            }
            QPushButton:hover {
                background-color: #FFA000;
            }
        """)
        ok_button.clicked.connect(self.close)
        layout.addWidget(ok_button, alignment=Qt.AlignmentFlag.AlignCenter)

        # 메인 레이아웃에 컨테이너 위젯 추가
        main_layout.addWidget(container)
        self.setLayout(main_layout)

    def show_error(self):
        self.show()