import asyncio
import datetime
import time
from PyQt6.QtWidgets import QMainWindow, QWidget, QVBoxLayout, QStackedWidget
from PyQt6.QtCore import Qt, QTimer
from qasync import asyncSlot
from gui.components.main_button import MainButton
from gui.components.top_label import TopLabel
from gui.components.enter_number_plate import EnterNumberPlate
from gui.components.keypad import Keypad
from gui.components.park_button import ParkButton
from gui.components.gif_widget import GifWidget
from gui.pages.settlement_page import SettlementPage
from gui.pages.exit_page import ExitPage
from gui.pages.entry_page import EntryPage
from gui.pages.vehicle_selection_page import VehicleSelectionPage
from core.handlers import handle_enter, handle_exit, handle_get_vehicles
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
        self.main_button.entry_button.clicked.connect(self.start_entry_process)
        self.main_button.exit_button.clicked.connect(self.show_exit_page)
        self.stacked_widget.addWidget(self.main_button)

        # 입차 페이지
        self.entry_page = EntryPage(self)
        self.stacked_widget.addWidget(self.entry_page)
        
        # 출차 페이지
        self.exit_page = ExitPage(self)
        self.stacked_widget.addWidget(self.exit_page)

    # 입차 페이지
    # def show_entry_page(self):
    #     self.entry_page.number_plate_labels.set_all_label_text(self.camera.ocr_reader())
    #     self.stacked_widget.setCurrentWidget(self.entry_page)

    # 출차 페이지
    def show_exit_page(self):
        self.stacked_widget.setCurrentWidget(self.exit_page)
    
    # 비동기 차량 번호판 분석
    def start_entry_process(self):
        self.show_waiting_screen()
        asyncio.create_task(self.async_handle_entry())
        
    async def async_handle_entry(self):
        await asyncio.sleep(0)
        # OCR API
        # ocr_result = await asyncio.get_event_loop().run_in_executor(None, self.camera.ocr_reader)
        ocr_result = await self.camera.ocr_reader()
        # OCR 결과 처리 후 입차 화면으로 전환
        self.entry_page.number_plate_labels.set_all_label_text(ocr_result)
        self.stacked_widget.setCurrentWidget(self.entry_page)
    
    # 대기 화면
    def show_waiting_screen(self):
        self.gif_widget = GifWidget("parking_kiosk/gui/res/test.gif", duration=3000, parent=self)
        self.gif_widget.move(self.rect().center() - self.gif_widget.rect().center())
        self.gif_widget.start()
    
    
    async def async_handle_enter(self, license_plate):
        # 비동기 입차 처리
        success = await asyncio.get_event_loop().run_in_executor(None, handle_enter, "./result/temp_image.jpeg", license_plate, datetime.datetime.now())
        if success:
            await self.async_barrier_control()
        self.return_to_main()
    
    async def async_barrier_control(self):
        # 비동기 차단막 제어
        self.parking_barrier.upBarrier()
        await asyncio.sleep(3)
        self.parking_barrier.downBarrier()
        
    # 입차 처리
    def confirm_enter(self, license_plate):
        # TODO : 비동기적으로 수행되게
        asyncio.create_task(self.async_handle_enter(license_plate))
        self.show_gif_widget()
       
    def confirm_exit(self, license_plate):
        pass
        
    def show_settlement_page(self, vehicle_info):
        settlement_page = SettlementPage(vehicle_info, self)
        self.stacked_widget.addWidget(settlement_page)
        self.stacked_widget.setCurrentWidget(settlement_page)
        
    def show_gif_widget(self):
        gif_widget = GifWidget("parking_kiosk/gui/res/car-anime.gif", duration=3000, parent=self)
        gif_widget.move(self.rect().center() - gif_widget.rect().center())
        gif_widget.start()
        QTimer.singleShot(3000, self.return_to_main)

    def show_vehicle_selection_page(self, license_plate):
        vehicles = handle_get_vehicles(license_plate)
        
        vehicle_selection_page = VehicleSelectionPage(vehicles, self)
        self.stacked_widget.addWidget(vehicle_selection_page)
        self.stacked_widget.setCurrentWidget(vehicle_selection_page)
    
    def return_to_main(self):
        self.stacked_widget.setCurrentWidget(self.main_button)