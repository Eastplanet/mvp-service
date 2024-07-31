import time
import Jetson.GPIO as GPIO
from motorControl import initServoAngle, leftServoMaxAngle, rightServoMaxAngle, steerServoAngle

blackLine = 1
whiteLine = 0
import Jetson.GPIO as GPIO


def lineTracking(motorHat, kit, leftSensorPin, centerSensorPin, rightSensorPin, mode):
    try:
        if GPIO.getmode() is None:
            GPIO.setmode(GPIO.BOARD)
        prev = 'straight'
        while True:
            if mode[0] != 'line':
                break

            left = GPIO.input(leftSensorPin)
            center = GPIO.input(centerSensorPin)
            right = GPIO.input(rightSensorPin)
            print(left, center, right)
            if left == whiteLine and center == blackLine and right == whiteLine:
                motorHat.setThrottle(-0.35)  # Forward
                kit.servo[0].angle = initServoAngle
                prev = 'straight'
                print('Go Straigth')
            elif left == whiteLine and center == whiteLine and right == blackLine:
                motorHat.setThrottle(-1.0)
                kit.servo[0].angle = initServoAngle - steerServoAngle  # Turn right
                prev = 'right'
                print('Turn Right')
            elif left == blackLine and center == whiteLine and right == whiteLine:
                motorHat.setThrottle(-1.0)
                kit.servo[0].angle = initServoAngle + steerServoAngle # Turn left
                prev = 'left'
                print('Trun Left')
            else:
                if prev == 'straight':
                    motorHat.setThrottle(-0.35)  # Forward
                    kit.servo[0].angle = initServoAngle
                    prev = 'straight'
                elif prev == 'left':
                    motorHat.setThrottle(-1.0)
                    kit.servo[0].angle = initServoAngle + steerServoAngle # Turn left
                    prev = 'left'
                else:
                    motorHat.setThrottle(-1.0)
                    kit.servo[0].angle = initServoAngle + steerServoAngle # Turn left
                    prev = 'left'
                print('Continue')                     
                # print('Stop')
            time.sleep(0.05)
    except KeyboardInterrupt:
        pass
    finally:
        motorHat.setThrottle(0)
        kit.servo[0].angle = initServoAngle
        GPIO.cleanup()
        print("Program stopped and motor stopped.")
