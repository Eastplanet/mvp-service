# config/config.py
MQTT_PORT = 1883
MQTT_BROKER_IP = "192.168.30.151"
MQTT_TOPIC_SUB = "/parking/complete"
MQTT_TOPIC_PUB = "/parking/command"
MQTT_BROKER_PATH_WIN = "C:\\Program Files\\mosquitto\\mosquitto.exe"
MQTT_CONF_PATH_WIN = "C:\\Program Files\\mosquitto\\mosquitto.conf"
MQTT_BROKER_PATH_RASP = "/usr/sbin/mosquitto"
MQTT_CONF_PATH_RASP = "/etc/mosquitto/mosquitto.conf"