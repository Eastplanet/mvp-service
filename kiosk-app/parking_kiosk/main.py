# main.py

import sys
from PyQt5.QtWidgets import QApplication
from gui.main_window import MainWindow
from core.mqtt_client import setup_mqtt_client

def main():
    mqtt_client = setup_mqtt_client()
    mqtt_client.loop_start()  # 비동기 방식으로 MQTT 클라이언트 시작

    app = QApplication(sys.argv)
    window = MainWindow(mqtt_client)
    window.show()
    sys.exit(app.exec_())

if __name__ == '__main__':
    main()
