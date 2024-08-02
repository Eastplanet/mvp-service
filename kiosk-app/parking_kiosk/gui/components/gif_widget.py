from PyQt6.QtWidgets import QWidget, QVBoxLayout, QLabel, QGraphicsDropShadowEffect
from PyQt6.QtCore import Qt, QTimer, QSize
from PyQt6.QtGui import QMovie, QColor

class GifWidget(QWidget):
    def __init__(self, gif_path, duration=0, parent=None):
        super(GifWidget, self).__init__(parent)
        self.setFixedSize(300, 400)  # 큰 박스 크기
        self.setWindowFlags(Qt.WindowType.FramelessWindowHint | Qt.WindowType.WindowStaysOnTopHint)
        self.setStyleSheet("background-color: white; border-radius: 15px;")

        layout = QVBoxLayout()
        self.setLayout(layout)

        # GIF 추가
        self.gif_label = QLabel(self)
        self.movie = QMovie(gif_path)
        self.movie.setScaledSize(QSize(200, 200))  # GIF 크기를 박스에 맞춰 축소
        self.gif_label.setMovie(self.movie)
        layout.addWidget(self.gif_label, alignment=Qt.AlignmentFlag.AlignCenter)

        # 그림자 효과 추가
        shadow = QGraphicsDropShadowEffect()
        shadow.setBlurRadius(15)
        shadow.setColor(QColor(0, 0, 0, 160))
        shadow.setOffset(0, 0)
        self.gif_label.setGraphicsEffect(shadow)

        # Close the widget after the specified duration
        QTimer.singleShot(duration, self.close)

    def start(self):
        self.movie.start()
        self.show()
        
    def stop(self):
        self.movie.stop()