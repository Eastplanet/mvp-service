# gui/settlement_window.py

from PyQt5.QtWidgets import QDialog, QVBoxLayout, QPushButton, QLabel

class SettlementWindow(QDialog):
    def __init__(self, vehicle_number, main_window):
        super().__init__()
        self.vehicle_number = vehicle_number
        self.main_window = main_window
        self.initUI()

    def initUI(self):
        self.setWindowTitle('정산')
        layout = QVBoxLayout()

        self.label = QLabel(f'차량 번호: {self.vehicle_number}', self)
        self.label.setStyleSheet("font-size: 24px; padding: 10px;")

        self.amount_label = QLabel('금액: 1000원', self)
        self.amount_label.setStyleSheet("font-size: 24px; padding: 10px;")

        self.settle_button = QPushButton('정산', self)
        self.settle_button.setStyleSheet("font-size: 24px; padding: 10px;")
        self.settle_button.clicked.connect(self.settle)

        layout.addWidget(self.label)
        layout.addWidget(self.amount_label)
        layout.addWidget(self.settle_button)

        self.setLayout(layout)

    def settle(self):
        self.main_window.handle_exit()
        self.main_window.show_start_window()
        self.close()
