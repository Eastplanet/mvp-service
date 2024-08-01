import threading
import time
import Jetson.GPIO as GPIO
from motorControl import initializeMotors
from lineTracing import lineTracking
from keyboardControl import startKeyboardListener


# GPIO 초기 설정 (반복적인 초기화를 방지)
def setup_gpio():
    GPIO.cleanup()
    # GPIO 핀 번호 설정
    L_PIN = 18
    C_PIN = 22
    R_PIN = 26

    # GPIO 핀 모드 설정
    GPIO.setmode(GPIO.BOARD)
    GPIO.setup(L_PIN, GPIO.IN)
    GPIO.setup(C_PIN, GPIO.IN)
    GPIO.setup(R_PIN, GPIO.IN)
    print('GPIO setup complete!')
    return L_PIN, C_PIN, R_PIN

# 모터와 서보 초기화
motorHat, kit = initializeMotors()
initServoAngle = 95
pan = [initServoAngle]

# 모드 변수 설정
mode = ['keyboard']

# 키보드 리스너를 별도의 스레드에서 실행
listener_thread = threading.Thread(target=startKeyboardListener, args=(motorHat, kit, pan, mode))
listener_thread.start()

try:
    # 메인 루프
    while True:
        if mode[0] == 'line':
            L_PIN, C_PIN, R_PIN = setup_gpio()
            lineTracking(motorHat, kit, L_PIN, C_PIN, R_PIN, mode)
        time.sleep(0.1)
except KeyboardInterrupt:
    pass
finally:
    motorHat.setThrottle(0)
    kit.servo[0].angle = initServoAngle
    GPIO.cleanup()
    print("Program stopped and motor stopped.")
