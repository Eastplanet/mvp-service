from config.config import MQTT_BROKER, MQTT_PORT, MQTT_TOPIC_PUB, MQTT_TOPIC_SUB
from mqtt.mqtt_client import MQTTClient
from bot.controller import BotController

def main():
    bot_controller = BotController(None) # 컨트롤러 객체
    mqtt_client = MQTTClient(MQTT_BROKER, MQTT_PORT, MQTT_TOPIC_PUB, MQTT_TOPIC_SUB, bot_controller) # MQTT 클라이언트 객체
    bot_controller.mqtt_client = mqtt_client
    mqtt_client.connect()

    try:
        while True:
            pass
    except KeyboardInterrupt:
        print("Disconnecting from broker")
        mqtt_client.disconnect()

if __name__ == "__main__":
    main()
