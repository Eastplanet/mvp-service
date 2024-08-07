from PyQt6.QtCore import QThread, pyqtSignal
from core.camera import Camera

class OcrThread(QThread):
    def __init__(self):
        super(OcrThread, self).__init__()
        self.camera = Camera()
    
    ocr_complete_signal = pyqtSignal(object)
    
    def run(self):
        ocr_result = self.camera.ocr_reader()
        self.ocr_complete_signal.emit(ocr_result)