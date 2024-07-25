import paho.mqtt.client as mqtt

class MQTTClient:
    def __init__(self, broker, port, topic):
        self.broker = broker
        self.port = port
        self.topic = topic
        self.client = mqtt.Client(protocol=mqtt.MQTTv5)
        self.client.on_connect = self.on_connect
        self.client.on_message = self.on_message
        self.client.on_disconnect = self.on_disconnect

    def connect(self):
        self.client.connect(self.broker, self.port, 60)
        self.client.loop_start()

    def on_connect(self, client, userdata, flags, rc):
        if rc == 0:
            print("Connected to MQTT Broker!")
        else:
            print(f"Failed to connect, return code {rc}")

    def on_disconnect(self, client, userdata, rc):
        print("Disconnected from MQTT Broker")
        if rc != 0:
            print("Unexpected disconnection.")

    def on_message(self, client, userdata, msg):
        print(f"Received message: {msg.payload.decode()} on topic {msg.topic}")

    def publish_message(self, message):
        if not self.client.is_connected():
            print("reconnect")
            self.connect()
        result = self.client.publish(self.topic, message)
        status = result[0]
        if status == 0:
            print(f"Sent `{message}` to topic `{self.topic}`")
        else:
            print(f"Failed to send message to topic {self.topic}")

    def disconnect(self):
        self.client.loop_stop()
        self.client.disconnect()
