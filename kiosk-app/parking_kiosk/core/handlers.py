# core/handlers.py

from config import config

def handle_enter(client):
    client.publish(config.MQTT_TOPIC_ENTER, "enter")
    print('입차 명령 전송 완료')

def handle_exit(client):
    client.publish(config.MQTT_TOPIC_EXIT, "exit")
    print('출차 명령 전송 완료')
