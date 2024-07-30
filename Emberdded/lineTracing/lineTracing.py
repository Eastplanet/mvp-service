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

        while True:
            if mode[0] != 'line':
                break

            left = GPIO.input(leftSensorPin)
            center = GPIO.input(centerSensorPin)
            right = GPIO.input(rightSensorPin)
            print(left, center, right)
            if left == whiteLine and center == blackLine and right == whiteLine:
                motorHat.setThrottle(-0.65)  # Forward
                kit.servo[0].angle = initServoAngle
                print('Go Straigth')
            elif left == whiteLine and center == whiteLine and right == blackLine:
                motorHat.setThrottle(-1.0)
                kit.servo[0].angle = initServoAngle - steerServoAngle  # Turn right
                print('Turn Right')
            elif left == blackLine and center == whiteLine and right == whiteLine:
                motorHat.setThrottle(-1.0)
                kit.servo[0].angle = initServoAngle + steerServoAngle # Turn left
                print('Trun Left')
            else:
                motorHat.setThrottle(-0.6)
                # print('Stop')
            time.sleep(0.05)
    except KeyboardInterrupt:
        pass
    finally:
        motorHat.setThrottle(0)
        kit.servo[0].angle = initServoAngle
        GPIO.cleanup()
        print("Program stopped and motor stopped.")
