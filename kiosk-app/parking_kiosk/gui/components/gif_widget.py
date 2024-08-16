import sys
from PyQt6.QtWidgets import QApplication, QWidget, QVBoxLayout, QLabel, QGraphicsDropShadowEffect
from PyQt6.QtCore import Qt, QTimer, QSize
from PyQt6.QtGui import QMovie, QColor

class GifWidget(QWidget):
    def __init__(self, gif_path, main_msg, sub_msg, duration=3000, parent=None):
        super(GifWidget, self).__init__(parent)
        self.setFixedSize(300, 400)  # 큰 박스 크기
        self.setWindowFlags(Qt.WindowType.FramelessWindowHint | Qt.WindowType.WindowStaysOnTopHint)
        self.setAttribute(Qt.WidgetAttribute.WA_TranslucentBackground)

        container = QWidget(self)
        container.setFixedSize(260, 280)
        container.setStyleSheet("background-color: white; border-radius: 15px;")

        layout = QVBoxLayout(container)
        layout.setContentsMargins(10, 10, 10, 10)
        self.setLayout(QVBoxLayout())
        self.layout().addWidget(container)

        # 그림자 효과 추가
        shadow = QGraphicsDropShadowEffect()
        shadow.setBlurRadius(15)
        shadow.setColor(QColor(0, 0, 0, 160))
        shadow.setOffset(0, 0)
        container.setGraphicsEffect(shadow)

        # 메시지 추가
        main_message_label = QLabel(main_msg, self)
        main_message_label.setAlignment(Qt.AlignmentFlag.AlignCenter)
        main_message_label.setStyleSheet("font-size: 20px; color: black;")
        layout.addWidget(main_message_label, alignment=Qt.AlignmentFlag.AlignTop)

        # GIF 추가
        self.gif_label = QLabel(self)
        self.movie = QMovie(gif_path)
        self.movie.setScaledSize(QSize(200, 200))  # GIF 크기를 박스에 맞춰 축소
        self.gif_label.setMovie(self.movie)
        layout.addWidget(self.gif_label, alignment=Qt.AlignmentFlag.AlignCenter)

        # 서브 메시지 추가
        sub_message_label = QLabel(sub_msg, self)
        sub_message_label.setAlignment(Qt.AlignmentFlag.AlignCenter)
        sub_message_label.setStyleSheet("font-size: 16px; color: black;")
        layout.addWidget(sub_message_label, alignment=Qt.AlignmentFlag.AlignBottom)

        # 지정된 시간 후에 애니메이션 중지 및 창 닫기
        QTimer.singleShot(duration, self.stop)

    def start(self):
        self.movie.start()
        self.show()

    def stop(self):
        self.movie.stop()
        self.close()