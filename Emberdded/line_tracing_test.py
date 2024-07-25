import Jetson.GPIO as GPIO
import time

# GPIO 핀 번호 설정
L_PIN = 18
C_PIN = 22
R_PIN = 26

# GPIO 핀 모드 설정
GPIO.setmode(GPIO.BOARD)
GPIO.setup(L_PIN, GPIO.IN)
GPIO.setup(C_PIN, GPIO.IN)
GPIO.setup(R_PIN, GPIO.IN)

try:
    while True:
        # 각 센서의 입력 값 읽기
        left = GPIO.input(L_PIN)
        center = GPIO.input(C_PIN)
        right = GPIO.input(R_PIN)

        # 센서 값 출력
        print(f"Left: {left}, Center: {center}, Right: {right}")
        
        # 0.1초 대기
        time.sleep(0.1)

except KeyboardInterrupt:
    print("프로그램 종료!")
finally:
    # GPIO 설정 초기화
    GPIO.cleanup()
