import json
import paho.mqtt.client as mqtt

class MQTTClient:
    def __init__(self, broker, port, topic, message_callback):
        self.broker = broker
        self.port = port
        self.topic = topic
        self.client = mqtt.Client()
        self.client.on_connect = self.on_connect
        self.client.on_message = message_callback

    def connect(self):
        self.client.connect(self.broker, self.port, 60)
        self.client.loop_start()

    def on_connect(self, client, userdata, flags, rc):
        if rc == 0:
            print("Connected to MQTT Broker!")
            self.client.subscribe(self.topic)
        else:
            print(f"Failed to connect, return code {rc}")

    def on_message(client, userdata, msg):
        command = json.loads(msg.payload.decode())
        start_point = command.get("start_point")
        end_point = command.get("end_point")
        print(f"Start point: {start_point}")
        print(f"End point: {end_point}")

    def disconnect(self):
        self.client.loop_stop()
        self.client.disconnect()
