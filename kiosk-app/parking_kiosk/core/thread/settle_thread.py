import time
from PyQt6.QtCore import QThread, pyqtSignal
from core.camera import Camera
from core.handlers import handle_exit

# 정산 스레드
class SettleThread(QThread):
    def __init__(self, license_plate):
        super(SettleThread, self).__init__()
        self.license_plate = license_plate
        
    settle_complete_signal = pyqtSignal(object)
    
    def run(self):
        response = handle_exit(self.license_plate)
        time.sleep(2)
        self.settle_complete_signal.emit(response)