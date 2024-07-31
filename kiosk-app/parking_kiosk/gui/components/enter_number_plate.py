from PyQt6.QtWidgets import QWidget, QVBoxLayout, QHBoxLayout
from PyQt6.QtCore import Qt
from gui.components.clickable_label import ClickableLabel

class EnterNumberPlate(QWidget):
    def __init__(self, parent=None):
        super(EnterNumberPlate, self).__init__(parent)

        self.current_label_index = 0
        self.car_number_labels = []
        self.keypad = None  # 키패드 속성을 초기화합니다.

        # 레이아웃 설정
        layout = QVBoxLayout()
        layout1 = QHBoxLayout()
        layout2 = QHBoxLayout()
        layout.addLayout(layout1)
        layout.addLayout(layout2)
        self.setLayout(layout)

        for i in range(8):
            label = ClickableLabel(" ", self)
            label.setFixedSize(40, 40)
            label.setAlignment(Qt.AlignmentFlag.AlignCenter)
            if i == 3:  # 4번째 레이블은 '-'로 설정하고 수정 불가
                label.setText('-')
                label.setStyleSheet("border: 1px solid black; background-color: gray; font-size: 20px; border-radius: 3px; color: white;")
            else:
                label.setStyleSheet("border: 1px solid black; background-color: white; font-size: 20px; border-radius: 3px; color: black;")
                label.clicked.connect(self.label_clicked)
            if i < 4:
                layout1.addWidget(label)
            else:
                layout2.addWidget(label)
            self.car_number_labels.append(label)

        layout1.setSpacing(5)
        layout2.setSpacing(5)

        dummy_data = "12가3456"
        for i, char in enumerate(dummy_data):
            self.car_number_labels[i].setText(char)

    def label_clicked(self):
        label = self.sender()
        self.current_label_index = self.car_number_labels.index(label)
        if self.keypad:
            if self.current_label_index == 2:  # 3번째 레이블은 한글로 고정
                self.keypad.show_cheonjiin_keyboard()
            elif self.current_label_index == 3:  # 4번째 레이블은 수정 불가
                return
            else:  # 나머지 레이블은 숫자 키패드로 고정
                self.keypad.show_number_keyboard()

    def clear_label(self):
        if self.current_label_index != 3:  # 4번째 레이블은 수정 불가
            self.car_number_labels[self.current_label_index].setText("")
        
    def set_label_text(self, text):
        if self.current_label_index != 3:  # 4번째 레이블은 수정 불가
            self.car_number_labels[self.current_label_index].setText(text)

    def get_current_label_text(self):
        return self.car_number_labels[self.current_label_index].text()
        
    def set_keypad(self, keypad):
        self.keypad = keypad  # 키패드를 설정하는 메서드를 추가합니다.
        
    def get_all_label_text(self):
        return ''.join(label.text() for label in self.car_number_labels)
    
    def set_all_label_text(self, licensePlate):
        data = licensePlate
        
        if len(licensePlate) > 8:
            data = ""
            data += " " * 8
        
        for i, char in enumerate(data):
            self.car_number_labels[i].setText(char)
    