import threading
import time
import Jetson.GPIO as GPIO
from multiprocessing import Queue, Process
from .lineTracing import startLineTracing
from .getLidarData import start_lidar

class BotController:
    def __init__(self, mqtt_client):
        self.mqtt_client = mqtt_client
        # self.lidar_queue = Queue()
        # self.lidar_process = Process(target=start_lidar, args=(self.lidar_queue,))
        # self.lidar_process.start()

    # TODO : 주행 로직 추가
    def startDriving(self, message):
        # startNode = message.get("start")
        # endNode = message.get("end")
        startNode = 0
        endNode = 6
        
        print("Driving from {} to {}".format(startNode, endNode))
        lidar_queue = Queue()
        time.sleep(2)
        # 라이다 데이터를 수집하는 스레드를 생성하고 시작합니다.
        lidar_thread = threading.Thread(target=start_lidar, args=(lidar_queue,), daemon=True)
        lidar_thread.start()
        startLineTracing(startNode, endNode, lidar_queue)
        # lidar_queue2 = Queue()
        # # 라이다 데이터를 수집하는 스레드를 생성하고 시작합니다.
        # lidar_thread2 = threading.Thread(target=start_lidar, args=(lidar_queue2,), daemon=True)
        # lidar_thread2.start()
        # startLineTracing(endNode, startNode, lidar_queue2)
        self.completeDriving(message)
        time.sleep(2)

        
    def completeDriving(self, message):
        self.mqtt_client.publish(message)