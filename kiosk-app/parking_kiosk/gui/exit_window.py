# gui/exit_window.py

from PyQt5.QtWidgets import QWidget, QVBoxLayout, QPushButton, QLineEdit, QLabel, QListWidget, QListWidgetItem

class ExitWindow(QWidget):
    def __init__(self, main_window):
        super().__init__()
        self.main_window = main_window
        self.initUI()

    def initUI(self):
        layout = QVBoxLayout()

        self.label = QLabel('출차할 차량의 4자리 번호를 입력하세요:', self)
        self.label.setStyleSheet("font-size: 24px; padding: 10px;")

        self.number_input = QLineEdit(self)
        self.number_input.setStyleSheet("font-size: 24px; padding: 10px;")

        self.search_button = QPushButton('조회', self)
        self.search_button.setStyleSheet("font-size: 24px; padding: 10px;")
        self.search_button.clicked.connect(self.search_vehicles)

        self.vehicle_list = QListWidget(self)
        self.vehicle_list.setStyleSheet("font-size: 24px; padding: 10px;")
        self.vehicle_list.itemClicked.connect(self.select_vehicle)

        layout.addWidget(self.label)
        layout.addWidget(self.number_input)
        layout.addWidget(self.search_button)
        layout.addWidget(self.vehicle_list)

        self.setLayout(layout)

    def search_vehicles(self):
        self.vehicle_list.clear()
        self.vehicle_list.addItem(QListWidgetItem("11가1111"))
        self.vehicle_list.addItem(QListWidgetItem("12가1111"))

    def select_vehicle(self, item):
        vehicle_number = item.text()
        self.main_window.show_settlement_window(vehicle_number)
