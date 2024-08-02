import asyncio
import datetime
import time
from PyQt6.QtWidgets import QMainWindow, QWidget, QVBoxLayout, QStackedWidget
from gui.components.main_button import MainButton
from gui.components.gif_widget import GifWidget
from gui.pages.settlement_page import SettlementPage
from gui.pages.exit_page import ExitPage
from gui.pages.entry_page import EntryPage
from gui.pages.vehicle_selection_page import VehicleSelectionPage
from core.handlers import handle_enter, handle_get_vehicles
from core.camera import Camera
from core.parking_barrier.parking_barrier_controller import ParkingBarrierController

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
        self.main_button = MainButton(self)
        self.main_button.entry_button.clicked.connect(self.show_enter_page)
        self.main_button.exit_button.clicked.connect(self.show_exit_page)
        self.stacked_widget.addWidget(self.main_button)

        # 입차 페이지
        self.entry_page = EntryPage(self)
        self.stacked_widget.addWidget(self.entry_page)
        
        # 출차 페이지
        self.exit_page = ExitPage(self)
        self.stacked_widget.addWidget(self.exit_page)
        
        # 대기 GIF
        self.gif_widget = GifWidget("parking_kiosk/gui/res/car-anime.gif", parent=self)
        self.gif_widget.move(self.rect().center() - self.gif_widget.rect().center())

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
        if success:
            self.barrier_control()
        self.return_to_main()
        
    def confirm_exit(self, license_plate):
        pass
    
    # TODO: 비동기로 작성해야할듯            
    def barrier_control(self):
        self.parking_barrier.upBarrier()
        time.sleep(3)
        self.parking_barrier.downBarrier()
        

        
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
        self.stacked_widget.setCurrentWidget(self.main_button)