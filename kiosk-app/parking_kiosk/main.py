import sys
from PyQt5.QtWidgets import QApplication
from gui.main_window import MainWindow
from core.mqtt_client import MQTTClient  # MQTT 클라이언트 임포트

def main():
    app = QApplication(sys.argv)
    
    # MQTT 클라이언트 초기화
    mqtt_client = MQTTClient(broker="192.168.30.151", port=1883, topic="/parking/enter")
    
    # MainWindow 인스턴스 생성 시 MQTT 클라이언트 전달
    window = MainWindow(mqtt_client)
    window.show()
    sys.exit(app.exec_())

if __name__ == '__main__':
    main()
