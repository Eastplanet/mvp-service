import asyncio
import datetime
import threading
import time
from PyQt6.QtWidgets import QMainWindow, QWidget, QVBoxLayout, QStackedWidget, QLabel
from PyQt6.QtGui import QPixmap
from gui.components.main_button import MainButton
from gui.components.gif_widget import GifWidget
from gui.pages.settlement_page import SettlementPage
from gui.pages.exit_page import ExitPage
from gui.pages.entry_page import EntryPage
from gui.pages.vehicle_selection_page import VehicleSelectionPage
from core.handlers import handle_enter, handle_get_vehicles
from core.camera import Camera
from core.parking_barrier.parking_barrier_controller import ParkingBarrierController
from PyQt6.QtCore import QTimer
from PyQt6.QtCore import Qt
from PyQt6.QtCore import QSize

class MainWindow(QMainWindow):
    def __init__(self):
        super(MainWindow, self).__init__()
        self.setWindowTitle("주차장 키오스크")
        self.setGeometry(100, 100, 400, 600)
        self.setStyleSheet("background-color: #2E3348;")
        self.camera = Camera()
        self.parking_barrier = ParkingBarrierController()

        # 중앙 위젯 설정
        central_widget = QWidget(self)
        self.setCentralWidget(central_widget)

        # 스택 위젯 설정
        self.stacked_widget = QStackedWidget(self)
        layout = QVBoxLayout()
        layout.addWidget(self.stacked_widget)
        central_widget.setLayout(layout)

        # 메인 버튼 페이지
        self.main_button_page = QWidget(self)
        main_button_layout = QVBoxLayout(self.main_button_page)

        # 로고 추가
        self.logo_label = QLabel(self)
        self.logo_pixmap = QPixmap("parking_kiosk/gui/res/mvp-logo.png").scaled(QSize(200, 200), Qt.AspectRatioMode.KeepAspectRatio, Qt.TransformationMode.SmoothTransformation)
        self.logo_label.setPixmap(self.logo_pixmap)
        self.logo_label.setAlignment(Qt.AlignmentFlag.AlignCenter)
        main_button_layout.addWidget(self.logo_label, alignment=Qt.AlignmentFlag.AlignTop)

        # 메인 버튼 추가
        self.main_button = MainButton(self)
        self.main_button.entry_button.clicked.connect(self.show_enter_page)
        self.main_button.exit_button.clicked.connect(self.show_exit_page)
        main_button_layout.addWidget(self.main_button, alignment=Qt.AlignmentFlag.AlignTop)

        self.stacked_widget.addWidget(self.main_button_page)

        # 입차 페이지
        self.entry_page = EntryPage(self)
        self.stacked_widget.addWidget(self.entry_page)
        
        # 출차 페이지
        self.exit_page = ExitPage(self)
        self.stacked_widget.addWidget(self.exit_page)
        
    # 출차 페이지
    def show_exit_page(self):
        self.stacked_widget.setCurrentWidget(self.exit_page)
    
    # 비동기 차량 번호판 분석
    def show_enter_page(self):
        ocr_result = self.camera.ocr_reader()
        # OCR 결과 처리 후 입차 화면으로 전환
        self.entry_page.number_plate_labels.set_all_label_text(ocr_result)
        self.stacked_widget.setCurrentWidget(self.entry_page)
    
    # 입차 처리
    def confirm_enter(self, license_plate):
        success = handle_enter("./result/temp_image.jpeg", license_plate, datetime.datetime.now())
        self.gif_widget = GifWidget("parking_kiosk/gui/res/car-anime.gif", main_msg="입차가 진행됩니다", sub_msg="잠시만 기다려주세요..", duration=3000, parent=self)
        self.gif_widget.move(self.rect().center() - self.gif_widget.rect().center())
        if success:
            self.gif_widget.start()
            threading.Thread(target=self.barrier_control, args=(self.on_barrier_control_complete,)).start()
        
    def confirm_exit(self, license_plate):
        pass

    def barrier_control(self, callback):
        self.parking_barrier.upBarrier()
        time.sleep(3)
        self.parking_barrier.downBarrier()
        QTimer.singleShot(0, callback)

    def on_barrier_control_complete(self):
        QTimer.singleShot(0, self.gif_widget.stop)
        self.return_to_main()
    
    def show_settlement_page(self, vehicle_info):
        settlement_page = SettlementPage(vehicle_info, self)
        self.stacked_widget.addWidget(settlement_page)
        self.stacked_widget.setCurrentWidget(settlement_page)
        
    def show_vehicle_selection_page(self, license_plate):
        vehicles = handle_get_vehicles(license_plate)
        vehicle_selection_page = VehicleSelectionPage(vehicles, self)
        self.stacked_widget.addWidget(vehicle_selection_page)
        self.stacked_widget.setCurrentWidget(vehicle_selection_page)
    
    def return_to_main(self):
        self.stacked_widget.setCurrentWidget(self.main_button_page)