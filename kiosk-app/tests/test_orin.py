import paho.mqtt.client as mqtt

# 주차봇의 일련번호
PARKING_BOT_ID = "1000"

# MQTT 브로커 정보
MQTT_BROKER = "localhost"
MQTT_PORT = 1883
MQTT_TOPIC_COMMAND = "parking/commands"

def on_connect(client, userdata, flags, rc):
    print(f"Connected with result code {rc}")
    # 주차 명령을 수신할 토픽을 구독합니다.
    client.subscribe(MQTT_TOPIC_COMMAND)

def on_message(client, userdata, msg):
    message = msg.payload.decode()
    print(f"Received message: {message}")
    # 메시지를 파싱하여 주차봇의 명령을 처리합니다.
    process_command(message)

def process_command(message):
    # 예: "PARK 11가1111 2024-07-16T12:34:56"
    parts = message.split(" ")
    command = parts[0]
    license_plate = parts[1]
    entrance_time = parts[2]

    if command == "PARK":
        if PARKING_BOT_ID in message:
            print(f"Parking bot {PARKING_BOT_ID} is processing the command.")
            # 여기서 주차 명령을 처리하는 로직을 추가합니다.
            # 주차 작업을 완료하고 MQTT로 완료 메시지를 전송할 수 있습니다.
        else:
            print(f"Message not intended for this parking bot.")

def main():
    client = mqtt.Client()
    client.on_connect = on_connect
    client.on_message = on_message

    client.connect(MQTT_BROKER, MQTT_PORT, 60)

    client.loop_forever()

if __name__ == "__main__":
    main()
