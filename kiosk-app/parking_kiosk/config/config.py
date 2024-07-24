# config/config.py
# 외부 공용 브로커 사용 중
# 로컬 브로커로 변경해야함
MQTT_BROKER = "broker.hivemq.com"
MQTT_PORT = 1883
MQTT_TOPIC_ENTER = "parking/enter"
MQTT_TOPIC_EXIT = "parking/exit"
