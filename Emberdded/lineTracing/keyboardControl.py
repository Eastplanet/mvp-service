# keyboard_control.py
from pynput import keyboard
from motorControl import initServoAngle, leftServoMaxAngle, rightServoMaxAngle, steerServoAngle

def onPress(key, motorHat, kit, pan, mode):
    try:
        if mode[0] == 'keyboard':
            if key.char == 'w':
                if pan[0] != initServoAngle:
                    motorHat.setThrottle(-1.0)
                else:
                    motorHat.setThrottle(-0.75)
                print(f'Go Straight!')
            elif key.char == 's':
                if pan[0] != initServoAngle:
                    motorHat.setThrottle(1.0)
                else:
                    motorHat.setThrottle(0.75)
                print(f'Back!')
            elif key.char == 'a':
                pan[0] = max(pan[0] - steerServoAngle, leftServoMaxAngle)
                kit.servo[0].angle = pan[0]
                print(f"Servo angle set to: {pan[0]}")
            elif key.char == 'd':
                pan[0] = min(pan[0] + steerServoAngle, rightServoMaxAngle)
                kit.servo[0].angle = pan[0]
                print(f"Servo angle set to: {pan[0]}")

        if key.char == 'l':
            print("Switching to line tracking mode")
            mode[0] = 'line'
        elif key.char == 'k':
            print("Switching to keyboard mode")
            mode[0] = 'keyboard'
    except AttributeError:
        if key == keyboard.Key.esc:
            return False

def onRelease(key, motorHat):
    try:
        # if hasattr(key, 'char') and key.char in ['w', 's']:
        motorHat.setThrottle(0)
    except AttributeError:
        pass

def startKeyboardListener(motorHat, kit, pan, mode):
    listener = keyboard.Listener(
        on_press=lambda key: onPress(key, motorHat, kit, pan, mode),
        on_release=lambda key: onRelease(key, motorHat))
    listener.start()
    listener.join()
