# mqtt broker 프로세스 실행 함수
import subprocess
import os
import signal
import time
from config.config import MQTT_BROKER_PATH, MQTT_CONF_PATH

class MQTTBroker:
    def start_mosquitto():
        # Mosquitto 브로커를 백그라운드에서 실행
        mosquitto_process = subprocess.Popen([MQTT_BROKER_PATH, "-c", MQTT_CONF_PATH], stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        print("broker execute")
        return mosquitto_process

    def stop_mosquitto(mosquitto_process):
        # Mosquitto 브로커 프로세스 종료
        mosquitto_process.terminate()
        try:
            mosquitto_process.wait(timeout=5)
        except subprocess.TimeoutExpired:
            mosquitto_process.kill()