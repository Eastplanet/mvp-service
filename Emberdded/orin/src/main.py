import json
from mqtt.mqtt_client import MQTTClient
from bot.controller import BotController

if __name__ == "__main__":
    broker = "localhost"  # 브로커의 IP 주소
    port = 1883
    topic = "/parking/command"

    bot_controller = BotController()
    mqtt_client = MQTTClient(broker, port, topic, MQTTClient.on_message)
    mqtt_client.connect()

    try:
        while True:
            pass
    except KeyboardInterrupt:
        print("Disconnecting from broker")
        mqtt_client.disconnect()
