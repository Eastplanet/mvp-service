# lineTracing.py

import sys
import time
import Jetson.GPIO as GPIO
from multiprocessing import Process, Queue
from . import motorControl
from . import utils
from . import mapSetting
from .getLidarData import start_lidar  # getLidarData의 start_lidar 함수 import

# 상수 정의
blackLine = 1
whiteLine = 0


class ExitProgramException(Exception):
    """프로그램 종료를 위한 custom exception"""
    pass


def isOverElapsedTime(elapsedTime, threshold=1.0):
    """경과 시간이 주어진 임계값을 초과했는지 확인"""
    return elapsedTime >= threshold


# GPIO 초기 설정 (반복적인 초기화를 방지)
def setupGpio():
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


def startLineTracing(startNode, endNode, queue):
    # 모터 초기화, GPIO 설정
    motorHat, kit = motorControl.initializeMotors()
    L_PIN, C_PIN, R_PIN = setupGpio()

    initialCarDirection = 'up'
    if startNode >= 4 and endNode == 0:
        initialCarDirection = 'down'
    print('Path Generating..')
    path = mapSetting.findShortestPathToGoal(mapSetting.mapGrid, startNode, endNode, initialCarDirection)
    if path:
        if endNode == 0:
            path[0] = 'Back'
            if path[1] == 'Rotate right':
                path[1] = 'Rotate left'
            elif path[1] == 'Rotate left':
                path[1] = 'Rotate right'
            elif startNode == 4:
                path[1:4] = 'Back', 'Back', 'Back'
        print(f'Path Generating Complete! -> Path : {path}')
    else:
        print(f'\033[1;31m[Error]\033[0m Path Generation Fail')
        raise ExitProgramException

    curStep = 0
    prevDirection = 'GO_STRAIGHT'
    startTime = updateTime = time.time()
    isUpdated = isObject = isIntersection = False

    # 상태 표시를 위한 심볼
    statusSymbols = {blackLine: '☐', whiteLine: '◼'}
    # 방향에 따라 호출할 함수 딕셔너리
    turnFunctions = {
        'GO_LEFT': motorControl.turnLeftGoStraight,
        'GO_RIGHT': motorControl.turnRightGoStraight,
        'GO_STRAIGHT': motorControl.turnStraightGoStraight,
        'BACK_LEFT': motorControl.turnLeftGoBack,
        'BACK_RIGHT': motorControl.turnRightGoBack,
        'BACK_STRAIGHT': motorControl.turnStraightGoBack
    }

    try:
        if GPIO.getmode() is None:
            GPIO.setmode(GPIO.BOARD)

        while True:
            currentTime = time.time()
            elapsedTime = currentTime - updateTime
            totalElapsedTime = currentTime - startTime
            print(f'\033[1mTotal elapsed time: {totalElapsedTime:.2f} sec, [{elapsedTime:.2f}] sec\033[0m')
            print(f'  -  \033[1mStep {curStep} : {path[curStep]}\033[0m')

            # 라이다 데이터 읽기
            if not queue.empty():
                lidar_data = queue.get()
                print(lidar_data)
                if lidar_data <= 0.38:
                    print("\033[1;31m[Warning!]Obstacle detected! Distance: {:.2f}\033[0m".format(lidar_data), end='\n\n')
                    isObject = True
                elif isObject == True:
                    isObject = False

            if isObject:
                motorControl.stop(kit, motorHat)
                time.sleep(0.1)
                continue

            if totalElapsedTime >= 40:
                raise ExitProgramException

            right = GPIO.input(L_PIN)  # 반대로 연결함
            center = GPIO.input(C_PIN)
            left = GPIO.input(R_PIN)  # 반대로 연결함
            
            print(f'\033[1m[ {statusSymbols[left]} {statusSymbols[center]} {statusSymbols[right]} ] \033[0m', end=' -> ')

            if left == whiteLine and center == blackLine and right == whiteLine:        # ◼ ☐ ◼
                if path[curStep] == 'Straight' or (path[curStep] == 'Stop' and path[curStep-1] == 'Straight'):
                    motorControl.turnStraightGoStraight(kit, motorHat)
                    prevDirection = 'GO_STRAIGHT'
                elif path[curStep] == 'Back' or (path[curStep] == 'Stop' and path[curStep-1] == 'Back'):
                    motorControl.turnStraightGoBack(kit, motorHat)
                    prevDirection = 'BACK_STRAIGHT'
            elif left == whiteLine and center == whiteLine and right == blackLine:      # ◼ ◼ ☐
                if path[curStep] == 'Straight' or (path[curStep] == 'Stop' and path[curStep-1] == 'Straight'):
                    motorControl.turnRightGoStraight(kit, motorHat)
                    prevDirection = 'GO_RIGHT'
                elif path[curStep] == 'Back' or (path[curStep] == 'Stop' and path[curStep-1] == 'Back'):
                    motorControl.turnLeftGoBack(kit, motorHat)
                    prevDirection = 'BACK_LEFT'
            elif left == blackLine and center == whiteLine and right == whiteLine:      # ☐ ◼ ◼
                if path[curStep] == 'Straight' or (path[curStep] == 'Stop' and path[curStep-1] == 'Straight'):
                    motorControl.turnLeftGoStraight(kit, motorHat)
                    prevDirection = 'GO_LEFT'
                elif path[curStep] == 'Back' or (path[curStep] == 'Stop' and path[curStep-1] == 'Back'):
                    motorControl.turnRightGoBack(kit, motorHat)
                    prevDirection = 'BACK_RIGHT'
            elif left == whiteLine and center == blackLine and right == blackLine:      # ◼ ☐ ☐
                # if path[curStep] == 'Straight' or (path[curStep] == 'Stop' and path[curStep-1] == 'Straight'):
                if (path[curStep] == 'Stop' and path[curStep-1] == 'Straight') or (curStep == len(path) -2 and path[curStep] == 'Straight'): 
                    motorControl.turnLeftGoStraight(kit, motorHat)
                    prevDirection = 'GO_LEFT'
                else:
                    isIntersection = True
            elif left == blackLine and center == blackLine and right == whiteLine:      # ☐ ☐ ◼
                # if not (curStep == 0 and path[curStep] == 'Straight') or (path[curStep] == 'Stop' and path[curStep-1] == 'Straight'):
                if (path[curStep] == 'Stop' and path[curStep-1] == 'Straight') or (curStep == len(path) -2 and path[curStep] == 'Straight'):
                    motorControl.turnRightGoStraight(kit, motorHat)
                    prevDirection = 'GO_RIGHT'
                else:
                    isIntersection = True
            elif (left == whiteLine and center == whiteLine and right == whiteLine):
                turnFunctions[prevDirection](kit, motorHat)

            if (left == blackLine and center == blackLine and right == blackLine) or isIntersection:      # ☐ ☐ ☐ -> 교차로
                if path[curStep] == 'Stop' and isOverElapsedTime(elapsedTime, threshold=0.1):
                    motorControl.stop(kit, motorHat)
                    raise ExitProgramException
                
                if isOverElapsedTime(elapsedTime) and not isUpdated:
                    if path[curStep] != 'Stop':
                        curStep += 1
                    isUpdated = True

                    if curStep < len(path):
                        if 'Rotate' in path[curStep]:
                            motorControl.rotate(motorHat, kit, path[curStep])
                            updateTime = time.time()
                            curStep += 1
                            isUpdated = False

                        else:
                            # time.sleep(0.2) # 교차로를 지나기 위해
                            updateTime = time.time()
                            if path[curStep] != 'Stop':
                                curStep += 1
                            isUpdated = False
                else:
                    if curStep == 0 and path[curStep] == 'Back':
                        prevDirection = 'BACK_STRAIGHT'
                    turnFunctions[prevDirection](kit, motorHat)
                print("\n\033[1;33m[Detect!] InterSection!\033[0m")
                isIntersection = False
            time.sleep(0.1)
            print()
    except KeyboardInterrupt:
        pass
    except ExitProgramException:
        print("Exiting program...")
    finally:
        motorControl.stop(kit, motorHat)
        GPIO.cleanup()
        print("Program stopped and motor stopped.")