# gui/main_window.py

from PyQt5.QtWidgets import QMainWindow, QStackedWidget
from gui.start_window import StartWindow
from gui.enter_window import EnterWindow
from gui.exit_window import ExitWindow
from gui.settlement_window import SettlementWindow
from core.handlers import handle_enter, handle_exit
from datetime import datetime
from core.camera import getVehicleImage
from core.ocr_reader import getLicensePlate
import paho.mqtt.client as mqtt

class MainWindow(QMainWindow):
    def __init__(self, mqtt_client):
        super().__init__()
        self.mqtt_client = mqtt_client
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

# 입차 명령 (이미지, 번호판, 시간)
    def handle_enter(self):
        image_path = getVehicleImage()
        license_plate = getLicensePlate(image_path)
        entrance_time = datetime.now()
        handle_enter(image_path, license_plate, entrance_time)

        # MQTT 메시지 발행
        mqtt_message = "1111"
        self.mqtt_client.publish_message(mqtt_message)

    def handle_exit(self):
        handle_exit()
