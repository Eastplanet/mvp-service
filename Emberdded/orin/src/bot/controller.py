import threading
import time
import Jetson.GPIO as GPIO
from multiprocessing import Queue, Process
from .lineTracing import startLineTracing
from .getLidarData import start_lidar

class BotController:
    def __init__(self, mqtt_client):
        self.mqtt_client = mqtt_client
        self.lidar_queue = Queue()
        self.lidar_process = Process(target=start_lidar, args=(self.lidar_queue,))
        self.lidar_process.start()

    # TODO : 주행 로직 추가
    def startDriving(self, message):
        startNode = message.get("start")
        endNode = message.get("end")
        
        print("Driving from {} to {}".format(startNode, endNode))
        time.sleep(2)
        startLineTracing(startNode, endNode, self.lidar_queue)
        self.completeDriving()
        time.sleep(2)

        
    def completeDriving(self):
        message = {
            "parkingBotSerialNumber": "1001",
            "status": "complete"
        }
        self.mqtt_client.publish(message)