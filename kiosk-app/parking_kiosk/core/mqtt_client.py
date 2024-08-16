import json
import paho.mqtt.client as mqtt

from core.handlers import handle_task_complete

class MQTTClient:
    def __init__(self, broker, port, sub_topic, pub_topic):
        self.broker = broker
        self.port = port
        self.sub_topic = sub_topic
        self.pub_topic = pub_topic
        self.client = mqtt.Client(protocol=mqtt.MQTTv5)
        self.client.on_connect = self.on_connect
        self.client.on_message = self.on_message
        self.client.on_disconnect = self.on_disconnect

    def connect(self):
        self.client.connect(self.broker, self.port, 60)
        self.client.loop_start()

    def on_connect(self, client, userdata, flags, rc, properties=None):
        if rc == 0:
            print("Connected to MQTT Broker!")
            self.client.subscribe(self.sub_topic)
        else:
            print(f"Failed to connect, return code {rc}")

    def on_disconnect(self, client, userdata, rc):
        print("Disconnected from MQTT Broker")
        if rc != 0:
            print("Unexpected disconnection.")

    def on_message(self, client, userdata, msg):
        print(f"Received `{msg.payload.decode()}` from `{msg.topic}` topic")
        data = json.loads(msg.payload.decode())
        
        handle_task_complete(data)
        print("전송 완료!")
        
    def publish_message(self, message):
        if not self.client.is_connected():
            print("reconnect")
            self.connect()
        
        message_json = json.dumps(message)    
        
        result = self.client.publish(self.pub_topic, message_json)
        status = result[0]
        if status == 0:
            print(f"Sent `{message}` to topic `{self.pub_topic}`")
        else:
            print(f"Failed to send message to topic {self.pub_topic}")

    def disconnect(self):
        self.client.loop_stop()
        self.client.disconnect()
