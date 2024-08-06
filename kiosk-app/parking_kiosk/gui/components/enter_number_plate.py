from PyQt6.QtWidgets import QWidget, QVBoxLayout, QHBoxLayout, QSpacerItem, QSizePolicy
from PyQt6.QtCore import Qt
from gui.components.clickable_label import ClickableLabel

class EnterNumberPlate(QWidget):
    def __init__(self, parent=None):
        super(EnterNumberPlate, self).__init__(parent)

        self.current_label_index = 0
        self.car_number_labels = []
        self.keypad = None  # 키패드 속성을 초기화합니다.

        # 전체 레이아웃 설정
        main_layout = QVBoxLayout()
        self.setLayout(main_layout)

        # 레이아웃 설정
        row1_layout = QHBoxLayout()
        row2_layout = QHBoxLayout()

        # 첫 번째 행의 레이아웃
        layout1 = QHBoxLayout()
        layout1.setAlignment(Qt.AlignmentFlag.AlignLeft)
        layout1.setSpacing(5)
        for i in range(4):
            label = ClickableLabel(" ", self)
            label.setFixedSize(40, 40)
            label.setAlignment(Qt.AlignmentFlag.AlignCenter)
            if i == 3:  # 4번째 레이블은 '-'로 설정하고 수정 불가
                label.setText('-')
                label.setStyleSheet("border: 1px solid black; background-color: gray; font-size: 20px; border-radius: 3px; color: white;")
            else:
                label.setStyleSheet("border: 1px solid black; background-color: white; font-size: 20px; border-radius: 3px; color: black;")
                label.clicked.connect(self.label_clicked)
            layout1.addWidget(label)
            self.car_number_labels.append(label)

        # 두 번째 행의 레이아웃
        layout2 = QHBoxLayout()
        layout2.setAlignment(Qt.AlignmentFlag.AlignRight)
        layout2.setSpacing(5)
        for i in range(4, 8):
            label = ClickableLabel(" ", self)
            label.setFixedSize(40, 40)
            label.setAlignment(Qt.AlignmentFlag.AlignCenter)
            label.setStyleSheet("border: 1px solid black; background-color: white; font-size: 20px; border-radius: 3px; color: black;")
            label.clicked.connect(self.label_clicked)
            layout2.addWidget(label)
            self.car_number_labels.append(label)

        # 첫 번째 행
        row1_layout.addLayout(layout1)
        main_layout.addLayout(row1_layout)

        # 두 번째 행
        row2_layout.addSpacerItem(QSpacerItem(80, 40, QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Minimum))
        row2_layout.addLayout(layout2)
        main_layout.addLayout(row2_layout)

        self.update_current_label_color()

    def update_current_label_color(self):
        for i, label in enumerate(self.car_number_labels):
            if i == self.current_label_index and i != 3:  # 4번째 레이블은 수정 불가
                label.setStyleSheet("border: 1px solid black; background-color: lightblue; font-size: 20px; border-radius: 3px; color: black;")
            elif i == 3:
                label.setStyleSheet("border: 1px solid black; background-color: gray; font-size: 20px; border-radius: 3px; color: white;")
            else:
                label.setStyleSheet("border: 1px solid black; background-color: white; font-size: 20px; border-radius: 3px; color: black;")

    def label_clicked(self):
        label = self.sender()
        self.current_label_index = self.car_number_labels.index(label)
        self.update_current_label_color()
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
            if self.current_label_index == 4:
                self.current_label_index -= 2
            elif self.current_label_index > 0:
                self.current_label_index -= 1
            else:
                self.current_label_index = 0
        self.update_current_label_color()
        
    def set_label_text(self, text):
        if self.current_label_index != 3:  # 4번째 레이블은 수정 불가
            self.car_number_labels[self.current_label_index].setText(text)
            self.update_current_label_color()
            if self.current_label_index == 2:
                self.current_label_index += 2
            elif self.current_label_index < 7:
                self.current_label_index += 1
            else:
                self.current_label_index = 7
        self.update_current_label_color()
        
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
        self.update_current_label_color()
    
    def reset_labels(self):
        for label in self.car_number_labels:
            label.setText(" ")
        self.current_label_index = 0
        self.update_current_label_color()
        if self.keypad:
            self.keypad.show_number_keyboard()
