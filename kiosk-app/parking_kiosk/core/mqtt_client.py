# core/mqtt_client.py

import paho.mqtt.client as mqtt
from config import config

def on_connect(client, userdata, flags, rc):
    if rc == 0:
        print("Connected to MQTT Broker")
    else:
        print(f"Failed to connect, return code {rc}")

def setup_mqtt_client():
    client = mqtt.Client()
    client.on_connect = on_connect
    client.connect(config.MQTT_BROKER, config.MQTT_PORT, 60)
    return client
