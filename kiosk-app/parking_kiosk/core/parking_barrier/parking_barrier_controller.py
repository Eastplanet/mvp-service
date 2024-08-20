# import RPi.GPIO as GPIO
from time import sleep
from gpiozero import AngularServo, LED, TonalBuzzer
from gpiozero.tones import Tone
from time import sleep
import threading

class ParkingBarrierController:
    def __init__(self):
        # 서보 모터 설정 (18번 핀)
        # self.servo = AngularServo(18, min_angle=0, max_angle=180, min_pulse_width=0.5/1000, max_pulse_width=2.5/1000)
        # self.led = LED(23)
        # self.buzzer = TonalBuzzer(24)  # 버저를 20번 핀에 연결
        pass

    def setServoAngle(self, angle):
        # self.servo.angle = angle
        pass

    def play_fur_elise(self):
        # notes = ['E5', 'D#5', 'E5', 'D#5', 'E5', 'B4', 'D5', 'C5', 'A4',
        #         'C4', 'E4', 'A4', 'B4',
        #         'E4', 'G#4', 'B4', 'C5',
        #         ]
        
        # durations = [0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.4,
        #             0.2, 0.2, 0.2, 0.4,
        #             0.2, 0.2, 0.2, 0.4,
        #             ]
        
        # for note, duration in zip(notes, durations):
        #     self.buzzer.play(Tone(note))
        #     sleep(duration)
        # self.buzzer.stop()
        pass

    def move_servo_up(self):
        # for i in range(0, 121, 30):
        #     self.led.on()
        #     self.setServoAngle(i)
        #     sleep(0.1)
        #     self.led.off()
        #     sleep(0.1)
        pass

    def move_servo_down(self):
        # for i in range(120, -1, -30):
        #     self.led.on()
        #     self.setServoAngle(i)
        #     sleep(0.1)
        #     self.led.off()
        #     sleep(0.1)
        pass

    def run_cycle(self):
        # 노래 재생 스레드 시작
        music_thread = threading.Thread(target=self.play_fur_elise)
        music_thread.start()

        # 서보 모터 상승
        print("차단막 올리기")
        self.move_servo_up()

        # 음악이 끝날 때까지 대기
        music_thread.join()

        sleep(5)  # 1초 대기

        # 노래 재생 스레드 다시 시작
        music_thread = threading.Thread(target=self.play_fur_elise)
        music_thread.start()

        # 서보 모터 하강
        print("차단막 내리기")
        self.move_servo_down()

        # 음악이 끝날 때까지 대기
        music_thread.join()

        sleep(1)  # 1초 대기