import asyncio
import sys
from PyQt6.QtWidgets import QApplication
from qasync import QEventLoop
from gui.pages.main_window import MainWindow
from core.mqtt_client import MQTTClient  # MQTT 클라이언트 임포트
from config.mqtt_broker import MQTTBroker
from config.config import MQTT_PORT, MQTT_BROKER_IP, MQTT_TOPIC_PUB, MQTT_TOPIC_SUB, SERVER_URL
from config.polling_daemon import PollingDaemon
from core.handlers import kiosk_login

def main():
    mosquitto_process = MQTTBroker.start_mosquitto()
    
    app = QApplication(sys.argv)
    loop = QEventLoop(app)
    asyncio.set_event_loop(loop)
    
    # 서버에 인증키 가져오기
    kiosk_login()
    
    
    # MQTT 클라이언트 초기화
    mqtt_client = MQTTClient(broker=MQTT_BROKER_IP, port=MQTT_PORT, sub_topic=MQTT_TOPIC_SUB, pub_topic=MQTT_TOPIC_PUB)
    server_url = SERVER_URL + "/api/parking-bot/poll"
    
    # 데몬 프로세스 시작
    polling_daemon = PollingDaemon(mqtt_client, server_url=server_url, poll_interval=5)
    polling_daemon.start()
    
    window = MainWindow()
    window.show()
    
    def on_exit():
        polling_daemon.stop()
        polling_daemon.join()
        MQTTBroker.stop_mosquitto(mosquitto_process)
    
    app.aboutToQuit.connect(on_exit)

    with loop:
        sys.exit(loop.run_forever())
    
if __name__ == '__main__':
    main()
