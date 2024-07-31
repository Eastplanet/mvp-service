import threading
import time
import Jetson.GPIO as GPIO
import torch
from motorControl import initializeMotors  # 모터와 서보 초기화를 위한 모듈
from lineTracing import lineTracking  # 라인 추적 모드를 위한 모듈

# 강화학습 모델 로드
model_path = 'path_to_trained_model.pth'  # 모델 파일 경로를 설정
model = torch.load(model_path)  # 모델을 파일에서 로드
model.eval()  # 모델을 평가 모드로 설정

# GPIO 초기 설정 (반복적인 초기화를 방지)
def setup_gpio():
    GPIO.cleanup()  # GPIO 핀을 정리하여 초기 상태로 설정
    # GPIO 핀 번호 설정
    L_PIN = 18
    C_PIN = 22
    R_PIN = 26

    # GPIO 핀 모드 설정
    GPIO.setmode(GPIO.BOARD)  # 핀 번호를 보드 모드로 설정
    GPIO.setup(L_PIN, GPIO.IN)  # 좌측 센서 핀을 입력 모드로 설정
    GPIO.setup(C_PIN, GPIO.IN)  # 중앙 센서 핀을 입력 모드로 설정
    GPIO.setup(R_PIN, GPIO.IN)  # 우측 센서 핀을 입력 모드로 설정
    print('GPIO setup complete!')  # 설정 완료 메시지 출력
    return L_PIN, C_PIN, R_PIN  # 설정된 핀 번호 반환

# 모터와 서보 초기화
motorHat, kit = initializeMotors()  # 모터와 서보 초기화
initServoAngle = 95  # 초기 서보 각도 설정
pan = [initServoAngle]  # 서보 각도 리스트 초기화

# 모드 변수 설정
mode = ['keyboard']  # 초기 모드를 'keyboard'로 설정 (필요 시 다른 모드로 변경)

def autonomous_driving(motorHat, kit, leftSensorPin, centerSensorPin, rightSensorPin, model, mode):
    try:
        while True:
            if mode[0] != 'autonomous':  # 현재 모드가 'autonomous'가 아니면 루프 종료
                break

            left = GPIO.input(leftSensorPin)  # 좌측 센서 값 읽기
            center = GPIO.input(centerSensorPin)  # 중앙 센서 값 읽기
            right = GPIO.input(rightSensorPin)  # 우측 센서 값 읽기

            # 상태(state) 정의 (여기서는 단순히 센서 값을 사용)
            state = torch.tensor([left, center, right], dtype=torch.float32)  # 센서 값을 텐서로 변환
            state = state.unsqueeze(0)  # 배치 차원을 추가

            # 모델 추론
            with torch.no_grad():  # 추론 과정에서는 그래디언트 계산을 하지 않음
                action = model(state).cpu().numpy()[0]  # 모델을 통해 행동 예측, numpy 배열로 변환

            # 행동(action) 적용
            throttle = action[0]  # 첫 번째 출력 값을 스로틀로 사용
            steering_angle = action[1]  # 두 번째 출력 값을 조향 각도로 사용

            # 스로틀과 조향 각도를 적절한 범위로 변환
            throttle = max(-1.0, min(1.0, throttle))  # 스로틀 값을 -1.0에서 1.0 사이로 제한
            steering_angle = max(initServoAngle - 30, min(initServoAngle + 30, initServoAngle + steering_angle * 30))  # 조향 각도를 제한

            motorHat.setThrottle(throttle)  # 모터 스로틀 설정
            kit.servo[0].angle = steering_angle  # 서보 각도 설정
            print(f'Throttle: {throttle}, Steering Angle: {steering_angle}')  # 디버그 정보 출력

            time.sleep(0.05)  # 짧은 지연 시간
    except KeyboardInterrupt:  # 키보드 인터럽트가 발생하면
        pass
    finally:
        motorHat.setThrottle(0)  # 모터 스로틀을 0으로 설정하여 정지
        kit.servo[0].angle = initServoAngle  # 서보 각도를 초기 값으로 설정
        GPIO.cleanup()  # GPIO 핀 정리
        print("Program stopped and motor stopped.")  # 종료 메시지 출력

try:
    # 메인 루프
    while True:
        if mode[0] == 'line':  # 모드가 'line'이면
            L_PIN, C_PIN, R_PIN = setup_gpio()  # GPIO 설정
            lineTracking(motorHat, kit, L_PIN, C_PIN, R_PIN, mode)  # 라인 추적 모드 실행
        elif mode[0] == 'autonomous':  # 모드가 'autonomous'이면
            L_PIN, C_PIN, R_PIN = setup_gpio()  # GPIO 설정
            autonomous_driving(motorHat, kit, L_PIN, C_PIN, R_PIN, model, mode)  # 자율 주행 모드 실행
        time.sleep(0.1)  # 짧은 지연 시간
except KeyboardInterrupt:  # 키보드 인터럽트가 발생하면
    pass
finally:
    motorHat.setThrottle(0)  # 모터 스로틀을 0으로 설정하여 정지
    kit.servo[0].angle = initServoAngle  # 서보 각도를 초기 값으로 설정
    GPIO.cleanup()  # GPIO 핀 정리
    print("Program stopped and motor stopped.")  # 종료 메시지 출력


    '''
    코드 설명
라이브러리 임포트 및 모델 로드:

필요한 라이브러리와 모듈을 임포트합니다.
강화학습 모델을 로드하고 평가 모드로 설정합니다.
GPIO 설정 함수 (setup_gpio):

GPIO 핀을 초기화하고, 센서 핀을 입력 모드로 설정합니다.
설정된 핀 번호를 반환합니다.
모터와 서보 초기화:

initializeMotors 함수를 사용하여 모터와 서보를 초기화합니다.
초기 서보 각도를 설정합니다.
모드 변수 설정:

초기 모드를 설정합니다.
자율 주행 함수 (autonomous_driving):

현재 모드가 autonomous일 때, 센서 데이터를 읽고 모델에 입력으로 제공합니다.
모델의 출력을 스로틀과 조향 각도로 변환하여 모터와 서보를 제어합니다.
메인 루프:

현재 모드를 확인하여 line 모드일 때는 lineTracking 함수를, autonomous 모드일 때는 autonomous_driving 함수를 실행합니다.
KeyboardInterrupt가 발생하면 모터와 서보를 초기 상태로 복귀시키고 GPIO를 정리합니다.
    '''
