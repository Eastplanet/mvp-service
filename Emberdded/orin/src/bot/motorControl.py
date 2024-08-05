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

def rotate(motorHat, kit, action):
    if action == 'Rotate left':
        rotateLeft(motorHat, kit)
    else:
        rotateRight(motorHat, kit)

def rotateLeft(motorHat, kit):
    # 서보를 왼쪽으로 회전시키고 모터를 설정하여 2초간 유지
    kit.servo[0].angle = initServoAngle - steerServoAngle
    motorHat.setThrottle(-1.0)
    print('Rotate Left: Turning left for 2 seconds...')
    time.sleep(2)
    
    # 서보를 오른쪽으로 회전시키고 모터를 반대로 설정하여 2초간 유지
    kit.servo[0].angle = initServoAngle + steerServoAngle
    motorHat.setThrottle(0.82)
    print('Rotate Left: Turning right for 2 seconds...')
    time.sleep(2)
    
    motorHat.setThrottle(0)
    kit.servo[0].angle = initServoAngle
    print('Rotation complete')

def rotateRight(motorHat, kit):
    # 서보를 right쪽으로 회전시키고 모터를 설정하여 2초간 유지
    kit.servo[0].angle = initServoAngle + steerServoAngle
    motorHat.setThrottle(-1.0)
    print('Rotate Left: Turning right for 2 seconds...')
    time.sleep(2)
    
    # 서보를 left쪽으로 회전시키고 모터를 반대로 설정하여 2초간 유지
    kit.servo[0].angle = initServoAngle - steerServoAngle
    motorHat.setThrottle(0.75)
    print('Rotate Left: Turning left for 2 seconds...')
    time.sleep(2)
    
    motorHat.setThrottle(0)
    kit.servo[0].angle = initServoAngle
    print('Rotation complete')

def turnLeft(kit):
    kit.servo[0].angle = initServoAngle - steerServoAngle/3
    print('Turn Left')

def turnRight(kit):
    kit.servo[0].angle = initServoAngle + steerServoAngle/3
    print('Turn Right')

def turnStraight(kit):
    kit.servo[0].angle = initServoAngle
    print('Turn Straight')

def goStraight(motorHat):
    motorHat.setThrottle(-0.4)
    print('Go Straight')

def goBack(motorHat):
    motorHat.setThrottle(0.4)
    print('Go Back')

def stop(motorHat, kit):
    kit.servo[0].angle = initServoAngle
    motorHat.setThrottle(0.0)
    print('Stop')