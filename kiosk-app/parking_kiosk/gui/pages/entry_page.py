from PyQt6.QtWidgets import QWidget, QVBoxLayout, QHBoxLayout
from PyQt6.QtCore import Qt
from gui.components.top_label import TopLabel
from gui.components.enter_number_plate import EnterNumberPlate
from gui.components.keypad import Keypad
from gui.components.park_button import ParkButton
from gui.components.home_button import HomeButton
from core.camera import Camera

# 입차 페이지
class EntryPage(QWidget):
    def __init__(self, main_window, parent=None):
        # 초기화
        super(EntryPage, self).__init__(parent)
        self.main_window = main_window
        self.camera = Camera()
        
        # 레이아웃 설정
        layout = QVBoxLayout()
        self.setLayout(layout)

        # 상단 라벨
        self.top_label = TopLabel(self)
        # 차량 번호 레이블
        self.number_plate_labels = EnterNumberPlate(self)
        # 키패드
        self.keypad = Keypad(self.number_plate_labels, main_window=self.main_window)
        # 자동주차하기 버튼
        self.park_button = ParkButton(self)

        # NumberPlateLabels에 키패드를 설정합니다.
        self.number_plate_labels.set_keypad(self.keypad)

        # 레이아웃에 추가
        layout.addWidget(self.top_label, alignment=Qt.AlignmentFlag.AlignCenter)
        layout.addWidget(self.number_plate_labels, alignment=Qt.AlignmentFlag.AlignCenter)
        layout.addWidget(self.keypad, alignment=Qt.AlignmentFlag.AlignCenter)
        layout.addWidget(self.park_button, alignment=Qt.AlignmentFlag.AlignCenter)

        # 자동주차하기 버튼 클릭 시 GIF 재생 및 메인 화면으로 돌아가기
        self.park_button.button.clicked.connect(self.on_park_button_clicked)
        
    def on_park_button_clicked(self):
        license_plate = self.number_plate_labels.get_all_label_text()
        self.main_window.confirm_enter(license_plate)