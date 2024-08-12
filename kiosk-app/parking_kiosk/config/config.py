# config/config.py
RABBIT_MQ_HOST = "mvp-project.shop"
RABBIT_Q_NAME = "task_queue"
MQTT_PORT = 1883
MQTT_BROKER_IP = "localhost"
MQTT_TOPIC_SUB = "/parking/complete"
MQTT_TOPIC_PUB = "/parking/command"
MQTT_BROKER_PATH_WIN = "C:\\Program Files\\mosquitto\\mosquitto.exe"
MQTT_CONF_PATH_WIN = "C:\\Program Files\\mosquitto\\mosquitto.conf"
MQTT_BROKER_PATH_RASP = "/usr/sbin/mosquitto"
MQTT_CONF_PATH_RASP = "/etc/mosquitto/mosquitto.conf"
SERVER_URL = "https://mvp-project.shop/api"

API_KEY = ""

def set_api_key(api_key):
    global API_KEY
    API_KEY = api_key
    
def get_api_key():
    return API_KEY