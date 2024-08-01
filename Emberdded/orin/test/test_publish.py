# import paho.mqtt.client as mqtt
# import json

# def on_connect(client, userdata, flags, rc):
#     if rc == 0:
#         print("Connected successfully")
#     else:
#         print(f"Connect failed with code {rc}")

# def on_publish(client, userdata, mid):
#     print(f"Message {mid} published.")

# # MQTT 클라이언트 생성
# client = mqtt.Client()

# # 콜백 함수 설정
# client.on_connect = on_connect
# client.on_publish = on_publish

# # MQTT 브로커 연결
# client.connect("localhost", 1883, 60)

# # 메시지 발행
# command = {
#     "start_point": "출발점 정보",
#     "end_point": "도착점 정보"
# }

# client.publish("/parking/command", json.dumps(command))

# # 연결 유지
# client.loop_forever()
