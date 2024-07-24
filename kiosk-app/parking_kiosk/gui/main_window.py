# gui/main_window.py

from PyQt5.QtWidgets import QMainWindow, QStackedWidget
from gui.start_window import StartWindow
from gui.enter_window import EnterWindow
from gui.exit_window import ExitWindow
from gui.settlement_window import SettlementWindow
from core.handlers import handle_enter, handle_exit

class MainWindow(QMainWindow):
    def __init__(self):
        super().__init__()
        self.initUI()

    def initUI(self):
        self.setWindowTitle('Parking Kiosk')
        self.setGeometry(100, 100, 400, 300)

        self.stacked_widget = QStackedWidget()
        self.setCentralWidget(self.stacked_widget)

        self.start_window = StartWindow(self)
        self.enter_window = EnterWindow(self)
        self.exit_window = ExitWindow(self)

        self.stacked_widget.addWidget(self.start_window)
        self.stacked_widget.addWidget(self.enter_window)
        self.stacked_widget.addWidget(self.exit_window)

        self.show_start_window()

    def show_start_window(self):
        self.stacked_widget.setCurrentWidget(self.start_window)

    def show_enter_window(self):
        self.stacked_widget.setCurrentWidget(self.enter_window)

    def show_exit_window(self):
        self.stacked_widget.setCurrentWidget(self.exit_window)

    def show_settlement_window(self, vehicle_number):
        self.settlement_window = SettlementWindow(vehicle_number, self)
        self.settlement_window.exec_()

    def handle_enter(self):
        handle_enter()

    def handle_exit(self):
        handle_exit()
