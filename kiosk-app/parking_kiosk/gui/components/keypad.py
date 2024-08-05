from PyQt6.QtWidgets import QWidget, QGridLayout, QPushButton, QStackedWidget, QVBoxLayout
from PyQt6.QtCore import Qt

class Keypad(QWidget):
    def __init__(self, number_plate_labels, parent=None, main_window=None, use_number_keypad=False, mode='entry'):
        super(Keypad, self).__init__(parent)
        self.number_plate_labels = number_plate_labels
        self.main_window = main_window
        self.use_number_keypad = use_number_keypad
        self.mode = mode  # 'entry' 또는 'exit' 모드를 설정합니다.

        layout = QVBoxLayout()
        self.setLayout(layout)

        if use_number_keypad or self.mode == 'exit':
            self.number_keypad = self.create_number_keyboard()
            layout.addWidget(self.number_keypad)
        else:
            self.stacked_widget = QStackedWidget()
            self.cheonjiin_keypad = self.create_cheonjiin_keyboard()
            self.number_keypad = self.create_number_keyboard()

            self.stacked_widget.addWidget(self.cheonjiin_keypad)
            self.stacked_widget.addWidget(self.number_keypad)

            layout.addWidget(self.stacked_widget)
            self.stacked_widget.setCurrentWidget(self.number_keypad)

    def keypad_clicked(self):
        button = self.sender()
        key = button.text()
        if key == '<-':
            self.number_plate_labels.clear_label()
        elif key == '확인':
            self.handle_confirm()  # '확인' 키 클릭 시 처리
        else:
            self.number_plate_labels.set_label_text(key)

    def handle_confirm(self):
        license_plate = self.number_plate_labels.get_all_label_text()
        self.number_plate_labels.reset_labels()
        if self.mode == 'entry':
            self.main_window.confirm_enter(license_plate)  # 입차 모드일 때만 GIF를 표시합니다.
        elif self.mode == 'exit':
            self.main_window.show_vehicle_selection_page(license_plate)  # 출차 모드일 때 차량 리스트 페이지를 표시합니다.

    def create_cheonjiin_keyboard(self):
        # 천지인 키패드를 생성합니다.
        widget = QWidget()
        layout = QGridLayout()
        widget.setLayout(layout)
        keys = ['ㅣ', '·', 'ㅡ', 'ㄱ ㄲ', 'ㄴ ㄹ', 'ㄷ ㄸ', 'ㅂ ㅃ', 'ㅅ ㅆ', 'ㅈ ㅉ', '<-', 'ㅇ ㅁ', '확인']
        positions = [(i, j) for i in range(4) for j in range(3)]
        for position, key in zip(positions, keys):
            button = QPushButton(key, self)
            button.setFixedSize(80, 60)
            button.setStyleSheet("""
                QPushButton {
                    background-color: white; 
                    font-size: 20px; 
                    border-radius: 5px;
                    color: black;
                }
                QPushButton:hover {
                    background-color: lightgray;
                }
            """)
            button.clicked.connect(self.keypad_clicked)
            layout.addWidget(button, *position)
        layout.setSpacing(5)
        return widget

    def create_number_keyboard(self):
        # 숫자 키패드를 생성합니다.
        widget = QWidget()
        layout = QGridLayout()
        widget.setLayout(layout)
        keys = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '<-', '0', '확인']
        positions = [(i, j) for i in range(4) for j in range(3)]
        for position, key in zip(positions, keys):
            button = QPushButton(key, self)
            button.setFixedSize(80, 60)
            button.setStyleSheet("""
                QPushButton {
                    background-color: white; 
                    font-size: 20px; 
                    border-radius: 5px;
                    color: black;
                }
                QPushButton:hover {
                    background-color: lightgray;
                }
            """)
            button.clicked.connect(self.keypad_clicked)
            layout.addWidget(button, *position)
        layout.setSpacing(5)
        return widget

    def show_cheonjiin_keyboard(self):
        if not self.use_number_keypad:
            self.stacked_widget.setCurrentWidget(self.cheonjiin_keypad)

    def show_number_keyboard(self):
        if not self.use_number_keypad:
            self.stacked_widget.setCurrentWidget(self.number_keypad)