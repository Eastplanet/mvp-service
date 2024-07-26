import sys
from PyQt5.QtWidgets import QApplication
from gui.main_window import MainWindow
from core.mqtt_client import MQTTClient  # MQTT 클라이언트 임포트
from config.mqtt_broker import MQTTBroker
from config.config import MQTT_PORT, MQTT_BROKER_IP, MQTT_TOPIC_COMMAND

def main():
    mosquitto_process = MQTTBroker.start_mosquitto()
    
    app = QApplication(sys.argv)
    
    # MQTT 클라이언트 초기화
    mqtt_client = MQTTClient(broker=MQTT_BROKER_IP, port=MQTT_PORT, topic=MQTT_TOPIC_COMMAND)
    
    # MainWindow 인스턴스 생성 시 MQTT 클라이언트 전달
    window = MainWindow(mqtt_client)
    window.show()
    sys.exit(app.exec_())

if __name__ == '__main__':
    main()
