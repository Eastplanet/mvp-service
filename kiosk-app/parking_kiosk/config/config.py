# config/config.py
RABBIT_MQ_HOST = "mvp-project.shop" # RabbitMQ 호스트 주소
RABBIT_Q_NAME = "task_queue" # RabbitMQ 큐 이름
MQTT_PORT = 1883 # MQTT 포트
MQTT_BROKER_IP = "localhost" # MQTT 브로커 주소
MQTT_TOPIC_SUB = "/parking/complete" # MQTT 구독 토픽
MQTT_TOPIC_PUB = "/parking/command" # MQTT 발행 토픽
MQTT_BROKER_PATH_WIN = "C:\\Program Files\\mosquitto\\mosquitto.exe" # Mosquitto 브로커 경로
MQTT_CONF_PATH_WIN = "C:\\Program Files\\mosquitto\\mosquitto.conf" # Mosquitto 설정 파일 경로
MQTT_BROKER_PATH_RASP = "/usr/sbin/mosquitto" # Mosquitto 브로커 경로
MQTT_CONF_PATH_RASP = "/etc/mosquitto/conf.d/external_access.conf" # Mosquitto 설정 파일 경로
SERVER_URL = "https://mvp-project.shop/api" # 서버 URL

API_KEY_LPR = "l7xx846db5f3bc1e48d29b7275a745d501c8" # LPR API 키
API_KEY = "" # 서버 API 키 (로그인 후 설정)

def set_api_key(api_key):
    global API_KEY
    API_KEY = api_key
    
def get_api_key():
    return API_KEY