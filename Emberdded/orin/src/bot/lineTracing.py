 # lineTracing.py

import sys
import time
import Jetson.GPIO as GPIO
from . import motorControl
from . import utils
from . import mapSetting

# 상수 정의
blackLine = 1
whiteLine = 0


class ExitProgramException(Exception):
    """프로그램 종료를 위한 custom exception"""
    pass


def isOverElapsedTime(elapsedTime, threshold=1):
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


def startDriving(startNode, endNode):
    # 모터 초기화, GPIO 설정
    motorHat, kit = motorControl.initializeMotors()
    L_PIN, C_PIN, R_PIN = setupGpio()

    path = mapSetting.findShortestPathToGoal(mapSetting.mapGrid, startNode, endNode, 'up')
    print(path)
    curStep = 0
    prevDirection = 'straight'
    startTime = updateTime = time.time()
    isUpdated = False
    print(f'{startNode} -> {endNode}')

    # 방향에 따라 호출할 함수 딕셔너리
    turnFunctions = {
        'left': motorControl.turnLeft,
        'right': motorControl.turnRight,
        'straight': motorControl.turnStraight
    }

    try:
        if GPIO.getmode() is None:
            GPIO.setmode(GPIO.BOARD)

        while True:
            currentTime = time.time()
            elapsedTime = currentTime - updateTime
            totalElapsedTime = currentTime - startTime
            print(f"Total elapsed time: {totalElapsedTime:.2f} sec, [{elapsedTime:.2f}] sec")
            print(f'Current Step{curStep} : {path[curStep]}')

            if totalElapsedTime >= 50:
                raise ExitProgramException

            right = GPIO.input(L_PIN)  # 반대로 연결함
            center = GPIO.input(C_PIN)
            left = GPIO.input(R_PIN)  # 반대로 연결함
            
            # 상태 표시를 위한 심볼
            statusSymbols = {blackLine: "☐", whiteLine: "◼"}
            print(f'[ {statusSymbols[left]} {statusSymbols[center]} {statusSymbols[right]} ]')

            if left == whiteLine and center == blackLine and right == whiteLine:        # ◼ ☐ ◼
                motorControl.turnStraight(kit)
                prevDirection = 'straight'
            elif left == whiteLine and center == whiteLine and right == blackLine:      # ◼ ◼ ☐
                motorControl.turnRight(kit)
                prevDirection = 'right'
            elif left == blackLine and center == whiteLine and right == whiteLine:      # ☐ ◼ ◼
                motorControl.turnLeft(kit)
                prevDirection = 'left'
            elif left == whiteLine and center == whiteLine and right == whiteLine:      # ◼ ◼ ◼
                if path[curStep] == 'Stop':
                    motorControl.stop(motorHat, kit)
                    raise ExitProgramException
                turnFunctions[prevDirection](kit)
            else:
                # 교차로 처리 로직
                if isOverElapsedTime(elapsedTime) and not isUpdated:
                    print("\t\033[1;33m[Detect] InterSection!\033[0m")
                    curStep += 1
                    isUpdated = True
                    print('step++')
                    print(curStep, len(path), path[curStep])
                    if curStep < len(path):
                        if 'Rotate' in path[curStep]:
                            motorControl.rotate(motorHat, kit, path[curStep])
                            updateTime = time.time()
                            curStep += 1
                            isUpdated = False
                            print('step++')
                            print(curStep, len(path), path[curStep])
                    
                        elif path[curStep-1] == 'Straight' and path[curStep] == 'Straight':
                            if left == whiteLine and center == blackLine and right == blackLine:  # ◼ ☐ ☐
                                motorControl.turnRight(kit)
                                prevDirection = 'right'
                            elif left == blackLine and center == blackLine and right == whiteLine:  # ☐ ☐ ◼
                                motorControl.turnLeft(kit)
                                prevDirection = 'left'
                            elif left == blackLine and center == blackLine and right == blackLine:  # ☐ ☐ ☐
                                motorControl.turnStraight(kit)
                                prevDirection = 'straight'
                            time.sleep(0.2) # 교차로를 지나기 위해
                            updateTime = time.time()
                            curStep += 1
                            isUpdated = False
                            print('step++')
                            # print(curStep, len(path), path[curStep])
                    
                elif left == whiteLine and center == blackLine and right == blackLine:  # ◼ ☐ ☐
                    motorControl.turnRight(kit)
                    prevDirection = 'right'
                elif left == blackLine and center == blackLine and right == whiteLine:  # ☐ ☐ ◼
                    motorControl.turnLeft(kit)
                    prevDirection = 'left'
                elif left == blackLine and center == blackLine and right == blackLine:  # ☐ ☐ ☐
                    motorControl.turnStraight(kit)
                    prevDirection = 'straight'
            motorControl.goStraight(motorHat)
            time.sleep(0.05)
            print()
    except KeyboardInterrupt:
        pass
    except ExitProgramException:
        print("Exiting program...")
    finally:
        motorControl.stop(motorHat, kit)
        GPIO.cleanup()
        print("Program stopped and motor stopped.")
