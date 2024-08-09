import time

class BotController:
    def __init__(self, mqtt_client):
        self.mqtt_client = mqtt_client

    # TODO : 주행 로직 추가
    def start_driving(self, message):
        start = message.get("start")
        end = message.get("end")
        
        print("Driving from {} to {}".format(start, end))
        time.sleep(10)
        self.complete_driving(message)
        
    def complete_driving(self, message):
        self.mqtt_client.publish(message)
        
        
        