import RPi.GPIO as GPIO
from time import sleep
from gpiozero import AngularServo, TonalBuzzer, LED
import raspberry_setting

# Define the melody (tones in Hz)
melody = [
    659, 622, 659, 622, 659, 494, 587, 523,
    440, None, 262, 330, 440, 494, None, 330, 415,
    494, 523, None, 330, 659, 622, 659, 622,
    659, 494, 587, 523, 440, None, 262, 330,
    440, 494, None, 330, 523, 494, 440
]

# Define note durations (in seconds)
note_durations = [
    0.125, 0.125, 0.125, 0.125, 0.125, 0.125, 0.125, 0.125,
    0.25, 0.25, 0.125, 0.125, 0.125, 0.125, 0.25, 0.125, 0.125,
    0.125, 0.125, 0.25, 0.125, 0.125, 0.125, 0.125, 0.125, 0.125,
    0.125, 0.125, 0.25, 0.25, 0.125, 0.125, 0.125, 0.125, 0.25,
    0.125, 0.125, 0.125, 0.25
]

class ParkingBarrierController:
    def __init__(self):
        self.servo = AngularServo(18, min_angle=0, max_angle=180, min_pulse_width=0.5/1000, max_pulse_width=2.5/1000)
        self.buzzer = TonalBuzzer(12)
        self.led = LED(16)
        self.note_index = 0
    
    def turnOnLED(self):
        try:
            self.led.on()
            sleep(0.1)
        except Exception as e:
            print(f"Error turning on LED: {e}")

    def turnOffLED(self):
        try:
            self.led.off()
            sleep(0.1)
        except Exception as e:
            print(f"Error turning off LED: {e}")

    def setServoAngle(self, angle):
        try:
            self.servo.angle = angle  # Convert to -90 to 90 degrees range
            sleep(0.1)
        except Exception as e:
            print(f"Error setting servo angle: {e}")

    def init(self):
        try:
            self.servo.angle = 0  # Set the servo angle to 0 degrees before exiting
            self.led.off()
            self.buzzer.stop()
        except Exception as e:
            print(f"Error during initialization: {e}")

    def upBarrier(self):
        self.note_index = 0
        for angle in range(0, 91, 5):
            self.setServoAngle(angle)
            self.turnOnLED()
            self.play_current_note()
            self.note_index += 1
            self.turnOffLED()
        self.init()
        print("차단바를 올립니다.")

    def downBarrier(self):
        self.note_index = 19
        for angle in range(90, -1, -5):
            self.setServoAngle(angle)
            self.turnOnLED()
            self.play_current_note()
            self.note_index += 1
            self.turnOffLED()
        self.init()
        print("차단바를 내립니다.")

    def play_current_note(self):
        if self.note_index < len(melody):
            note = melody[self.note_index]
            duration = note_durations[self.note_index] / 2
            try:
                if note is not None:
                    self.buzzer.play(Tone(note))
                sleep(duration)
                self.buzzer.stop()
            except Exception as e:
                print(f"Error playing note: {e}")