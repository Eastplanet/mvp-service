import sys
from PyQt6.QtWidgets import QApplication
from gui.pages.main_window import MainWindow
from core.mqtt_client import MQTTClient  # MQTT 클라이언트 임포트
from config.mqtt_broker import MQTTBroker
from config.config import MQTT_PORT, MQTT_BROKER_IP, MQTT_TOPIC_COMMAND
from config.polling_daemon import PollingDaemon

def main():
    mosquitto_process = MQTTBroker.start_mosquitto()
    
    app = QApplication(sys.argv)
    
    # MQTT 클라이언트 초기화
    mqtt_client = MQTTClient(broker=MQTT_BROKER_IP, port=MQTT_PORT, topic=MQTT_TOPIC_COMMAND)
    
    # 데몬 프로세스 시작
    polling_daemon = PollingDaemon(mqtt_client, server_url="http://localhost:8080/parking-bot/poll", poll_interval=5)
    polling_daemon.start()
    
    window = MainWindow()
    window.show()
    
    def on_exit():
        polling_daemon.stop()
        polling_daemon.join()
        MQTTBroker.stop_mosquitto(mosquitto_process)
    
    app.aboutToQuit.connect(on_exit)
    
    sys.exit(app.exec())

if __name__ == '__main__':
    main()
