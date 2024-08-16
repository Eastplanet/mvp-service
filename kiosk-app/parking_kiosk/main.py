import asyncio
import sys
from PyQt6.QtWidgets import QApplication
from qasync import QEventLoop
from gui.pages.main_window import MainWindow
from core.mqtt_client import MQTTClient  # MQTT 클라이언트 임포트
from config.mqtt_broker import MQTTBroker
from config.config import MQTT_PORT, MQTT_BROKER_IP, MQTT_TOPIC_PUB, MQTT_TOPIC_SUB, RABBIT_MQ_HOST, RABBIT_Q_NAME
from config.consumer_daemon import ConsumerDaemon
from core.handlers import kiosk_login
from PyQt6.QtGui import QFontDatabase
from PyQt6.QtGui import QFont

def main():
    mosquitto_process = MQTTBroker.start_mosquitto()

    app = QApplication(sys.argv)
    font_id = QFontDatabase.addApplicationFont("parking_kiosk/gui/res/fonts/GothicA1-Medium.ttf")
    if font_id == -1:
        print("Font load failed")
    else:
        font_family = QFontDatabase.applicationFontFamilies(font_id)[0]
        font = QFont(font_family)
        app.setFont(font)
        
    loop = QEventLoop(app)
    asyncio.set_event_loop(loop)

    # 서버에 인증키 가져오기
    kiosk_login()


    # MQTT 클라이언트 초기화
    mqtt_client = MQTTClient(broker=MQTT_BROKER_IP, port=MQTT_PORT, sub_topic=MQTT_TOPIC_SUB, pub_topic=MQTT_TOPIC_PUB)
    
    # 데몬 프로세스 시작
    consumer_daemon = ConsumerDaemon(mqtt_client, rabbitmq_host=RABBIT_MQ_HOST, queue_name=RABBIT_Q_NAME) 
    consumer_daemon.start()
    
    window = MainWindow()
    window.show()
    
    def on_exit():
        consumer_daemon.stop()
        consumer_daemon.join()
        MQTTBroker.stop_mosquitto(mosquitto_process)
    
    app.aboutToQuit.connect(on_exit)

    with loop:
        sys.exit(loop.run_forever())
    
if __name__ == '__main__':
    main()
