# mqtt broker 프로세스 실행 함수
import subprocess
import os
import signal
import time
from config.config import MQTT_BROKER_PATH_WIN, MQTT_CONF_PATH_WIN
from config.config import MQTT_BROKER_PATH_RASP, MQTT_CONF_PATH_RASP

class MQTTBroker:
    def start_mosquitto():
        # Mosquitto 브로커를 백그라운드에서 실행
        mosquitto_process = subprocess.Popen([MQTT_BROKER_PATH_WIN, "-c", MQTT_CONF_PATH_WIN], stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        # mosquitto_process = subprocess.Popen(['sudo', MQTT_BROKER_PATH_RASP, '-c', MQTT_CONF_PATH_RASP], stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        print("broker execute")
        return mosquitto_process

    def stop_mosquitto(mosquitto_process):
        # Mosquitto 브로커 프로세스 종료
        mosquitto_process.terminate()
        try:
            mosquitto_process.wait(timeout=5)
        except subprocess.TimeoutExpired:
            mosquitto_process.kill()