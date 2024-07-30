from adafruit_motor import motor
from adafruit_pca9685 import PCA9685
from adafruit_servokit import ServoKit
import board
import busio

initServoAngle = 95
steerServoAngle = 45
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
