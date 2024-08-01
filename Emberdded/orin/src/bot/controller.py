import time

class BotController:
    def __init__(self, mqtt_client):
        self.mqtt_client = mqtt_client

    def start_driving(self, message):
        start = message.get("start")
        end = message.get("end")
        print("Driving from {} to {}".format(start, end))
        time.sleep(2)
        self.complete_driving()
        
    def complete_driving(self):
        message = {
            "parkingBotSerialNumber": "1001",
            "status": "complete"
        }
        self.mqtt_client.publish(message)
        
        
        