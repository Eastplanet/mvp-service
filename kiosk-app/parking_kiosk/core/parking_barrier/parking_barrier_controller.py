import RPi.GPIO as GPIO
from time import sleep

class ParkingBarrierController:
    def __init__(self):
        GPIO.setmode(GPIO.BOARD)
        GPIO.setup(12, GPIO.OUT)
        self.p = GPIO.PWM(12, 50)
    
    # TODO: 차단바를 올리는 코드 작성
    def upBarrier(self):
        self.p.start(0)
        self.p.ChangeDutyCycle(4)
        sleep(1)
        print("차단바를 올립니다.")
    # TODO: 차단바를 내리는 코드 작성
    def downBarrier(self):
        self.p.ChangeDutyCycle(10)
        sleep(1)
        print("차단바를 내립니다.")
        self.p.stop()