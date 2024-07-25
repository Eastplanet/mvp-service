import paho.mqtt.client as mqtt

# MQTT 브로커 설정
MQTT_BROKER = "192.168.30.151"
MQTT_PORT = 1883
MQTT_TOPIC = "/parking/enter"

# MQTT 클라이언트 생성
client = mqtt.Client(protocol=mqtt.MQTTv5)

# 브로커에 연결되었을 때 호출되는 콜백 함수
def on_connect(client, userdata, flags, reason_code, properties):
    if reason_code == 0:
        print("Connected to MQTT Broker!")
        client.subscribe(MQTT_TOPIC)
    else:
        print(f"Failed to connect, reason code {reason_code}")

# 메시지를 수신했을 때 호출되는 콜백 함수
def on_message(client, userdata, msg):
    print(f"Received message: {msg.payload.decode()} on topic {msg.topic}")

# 콜백 함수 설정
client.on_connect = on_connect
client.on_message = on_message

# 브로커에 연결
client.connect(MQTT_BROKER, MQTT_PORT, 60)

# 네트워크 루프를 시작하여 메시지를 대기합니다.
client.loop_forever()
