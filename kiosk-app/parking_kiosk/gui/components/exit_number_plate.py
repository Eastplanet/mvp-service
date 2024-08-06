from PyQt6.QtWidgets import QWidget, QHBoxLayout
from PyQt6.QtCore import Qt
from gui.components.clickable_label import ClickableLabel

class ExitNumberPlate(QWidget):
    def __init__(self, parent=None):
        super(ExitNumberPlate, self).__init__(parent)

        layout = QHBoxLayout()
        self.setLayout(layout)

        self.car_number_labels = []
        self.current_index = 0  # 현재 입력 중인 인덱스를 추적합니다.

        for i in range(4):
            label = ClickableLabel(" ", self)
            label.setFixedSize(55, 55)
            label.setAlignment(Qt.AlignmentFlag.AlignCenter)
            label.setStyleSheet("border: 1px solid black; background-color: white; font-size: 20px; border-radius: 5px; color: black;")
            layout.addWidget(label)
            self.car_number_labels.append(label)
        
        layout.setSpacing(5)
        self.update_current_label_color()

    def update_current_label_color(self):
        for i, label in enumerate(self.car_number_labels):
            if i == self.current_index:
                label.setStyleSheet("border: 1px solid black; background-color: lightblue; font-size: 20px; border-radius: 5px; color: black;")
            else:
                label.setStyleSheet("border: 1px solid black; background-color: white; font-size: 20px; border-radius: 5px; color: black;")

    def set_label_text(self, text):
        if self.current_index < len(self.car_number_labels):
            self.car_number_labels[self.current_index].setText(text)
            self.current_index += 1
            self.update_current_label_color()

    def clear_label(self):
        if self.current_index > 0:
            self.current_index -= 1
            self.car_number_labels[self.current_index].setText(" ")
            self.update_current_label_color()

    def reset_labels(self):
        for label in self.car_number_labels:
            label.setText(" ")
        self.current_index = 0
        self.update_current_label_color()
        
    def get_all_label_text(self):
        return ''.join(label.text() for label in self.car_number_labels)
