#motorControl.py

from adafruit_motor import motor
from adafruit_pca9685 import PCA9685
from adafruit_servokit import ServoKit
import board
import busio
import time

initServoAngle = 122
steerServoAngle = 55
leftServoMaxAngle = initServoAngle - steerServoAngle
rightServoMaxAngle = initServoAngle + steerServoAngle

class PwmThrottleHat:
    def __init__(self, pwm, channel):
        self.pwm = pwm
        self.channel = channel
        self.pwm.frequency = 60  # Set frequency

    def setThrottle(self, throttle):
        pulse = int(0xFFFF * abs(throttle))  # Calculate 16-bit duty cycle

        if throttle > 0:
            self.pwm.channels[self.channel + 5].duty_cycle = pulse
            self.pwm.channels[self.channel + 4].duty_cycle = 0
            self.pwm.channels[self.channel + 3].duty_cycle = 0xFFFF
        elif throttle < 0:
            self.pwm.channels[self.channel + 5].duty_cycle = pulse
            self.pwm.channels[self.channel + 4].duty_cycle = 0xFFFF
            self.pwm.channels[self.channel + 3].duty_cycle = 0
        else:
            self.pwm.channels[self.channel + 5].duty_cycle = 0
            self.pwm.channels[self.channel + 4].duty_cycle = 0
            self.pwm.channels[self.channel + 3].duty_cycle = 0

def initializeMotors():
    i2c = busio.I2C(board.SCL, board.SDA)
    pca = PCA9685(i2c)
    pca.frequency = 60  # Set PCA9685 frequency

    motorHat = PwmThrottleHat(pca, channel=0)
    kit = ServoKit(channels=16, i2c=i2c, address=0x60)
    kit.servo[0].angle = initServoAngle  # Set initial servo position

    return motorHat, kit


def rotateLeft(motorHat, kit):
    # 서보를 왼쪽으로 회전시키고 모터를 설정하여 2초간 유지
    kit.servo[0].angle = initServoAngle - steerServoAngle
    motorHat.setThrottle(-1.0)
    time.sleep(1.3)
    
    # 서보를 오른쪽으로 회전시키고 모터를 반대로 설정하여 2초간 유지
    kit.servo[0].angle = initServoAngle + steerServoAngle
    motorHat.setThrottle(1.0)
    time.sleep(1.35)
    
    motorHat.setThrottle(0)
    kit.servo[0].angle = initServoAngle

    print("    \033[1;32mRotate Left (↶)\033[0m")

def rotateRight(motorHat, kit):
    # 서보를 right쪽으로 회전시키고 모터를 설정하여 2초간 유지
    kit.servo[0].angle = initServoAngle + steerServoAngle
    motorHat.setThrottle(-1.0)
    time.sleep(1.35)
    
    # 서보를 left쪽으로 회전시키고 모터를 반대로 설정하여 2초간 유지
    kit.servo[0].angle = initServoAngle - steerServoAngle
    motorHat.setThrottle(1.0)
    time.sleep(1.3)
    
    motorHat.setThrottle(0)
    kit.servo[0].angle = initServoAngle
    print("    \033[1;32mRotate Right (↷)\033[0m")


def rotate(motorHat, kit, action):
    if action == 'Rotate left':
        rotateLeft(motorHat, kit)
    else:
        rotateRight(motorHat, kit)

def turnLeft(kit, steerAngle = steerServoAngle*0.6):
    kit.servo[0].angle = initServoAngle - steerAngle

def turnRight(kit, steerAngle = steerServoAngle*0.6):
    kit.servo[0].angle = initServoAngle + steerAngle

def turnStraight(kit):
    kit.servo[0].angle = initServoAngle

def goStraight(motorHat, throttle=0.45):
    motorHat.setThrottle(-throttle)

def goBack(motorHat, throttle=0.4):
    motorHat.setThrottle(throttle)

def turnLeftGoStraight(kit, motorHat):
    turnLeft(kit)
    goStraight(motorHat, throttle=0.45)
    print("    \033[1;32mGo Left (↖)\033[0m")

def turnRightGoStraight(kit, motorHat):
    turnRight(kit)
    goStraight(motorHat, throttle=0.45)
    print("    \033[1;32mGo Right (↗)\033[0m")

def turnLeftGoBack(kit, motorHat):
    turnLeft(kit, steerAngle=steerServoAngle*0.65)
    goBack(motorHat, throttle=0.6)
    print("    \033[1;32mBack Left (↘)\033[0m")

def turnRightGoBack(kit, motorHat):
    turnRight(kit, steerAngle=steerServoAngle*0.65)
    goBack(motorHat, throttle=0.6)
    print("    \033[1;32mBack Right (↙)\033[0m")

def turnStraightGoStraight(kit, motorHat):
    turnStraight(kit)
    goStraight(motorHat, throttle=0.4)
    print("    \033[1;32mGo Straight (↑)\033[0m")

def turnStraightGoBack(kit, motorHat):
    turnStraight(kit)
    goBack(motorHat, throttle=0.4)
    print("    \033[1;32mBack Straight (↓)\033[0m")

def stop(kit, motorHat):
    kit.servo[0].angle = initServoAngle
    motorHat.setThrottle(0.0)
    print("    \033[1;31mStop (x)\033[0m")