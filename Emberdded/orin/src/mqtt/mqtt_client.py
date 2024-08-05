import json
import paho.mqtt.client as mqtt
from config.config import SERIAL_NUMBER

# command에 대해서 sub
# complete에 대해서 pub
class MQTTClient:
    def __init__(self, broker, port, pub_topic, sub_topic, bot_controller):
        self.broker = broker
        self.port = port
        self.pub_topic = pub_topic
        self.sub_topic = sub_topic
        self.bot_controller = bot_controller
        self.client = mqtt.Client(protocol=mqtt.MQTTv5)
        self.client.on_connect = self.on_connect
        self.client.on_message = self.on_message

    def connect(self):
        self.client.connect(self.broker, self.port, 60)
        self.client.loop_start()

    def on_connect(self, client, userdata, flags, rc, properties=None):
        if rc == 0:
            print("브로커 연결 성공!")
            self.client.subscribe(self.sub_topic)
        else:
            print(f"연결 실패 : {rc}")

    def on_message(self, client, userdata, msg):
        command = json.loads(msg.payload.decode())
        
        serial_number = command.get("parkingBotSerialNumber")
        if(serial_number != SERIAL_NUMBER):
            return
        # TODO : 작업 내용을 추가합니다.
        self.bot_controller.start_driving(command)
        
    def publish(self, message):
        print("Publishing message" + str(message))
        self.client.publish(self.pub_topic, json.dumps(message))

    def disconnect(self):
        self.client.loop_stop()
        self.client.disconnect()
