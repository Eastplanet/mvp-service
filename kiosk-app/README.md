# 주차장 키오스크

## 디렉토리 구조

```
parking_kiosk
│
├── config
│   ├── config.py                # 설정 파일
│   ├── mqtt_broker.py           # MQTT 브로커 설정
│   ├── consumer_daemon.py       # 작업 큐 리스너 데몬 프로세스
│
├── core
│   ├── parking_barrier
│   │   └── parking_barrier_controller.py   # 주차 차단기 제어
│   ├── thread
│   │   ├── ocr_thread.py        # OCR 쓰레드
│   │   └── settle_thread.py     # 정산 처리 쓰레드
│   ├── camera.py                # 카메라 관련 기능
│   ├── handlers.py              # 요청 처리 핸들러
│   └── mqtt_client.py           # MQTT 클라이언트
│   
├── gui
│   ├── components               # UI 구성 요소
│   │   ├── clickable_label.py   # 클릭 가능한 레이블
│   │   ├── enter_number_plate.py # 입차 차량 번호판 입력
│   │   ├── exit_number_plate.py  # 출차 차량 번호판 입력
│   │   ├── error_dialog.py      # 오류 처리 모달
│   │   ├── gif_widget.py        # GIF 위젯
│   │   ├── homebutton.py        # 키패드
│   │   ├── keypad.py            # 키패드
│   │   ├── main_button.py       # 메인 버튼
│   │   ├── park_button.py       # 주차 버튼
│   │   ├── top_label.py         # 상단 레이블
│   │   └── vehicle_list_item.py # 차량 리스트 항목
│   ├── pages                    # UI 페이지
│   │   ├── entry_page.py        # 입차 페이지
│   │   ├── exit_page.py         # 출차 페이지
│   │   ├── main_window.py       # 메인 윈도우
│   │   ├── settlement_page.py   # 정산 페이지
│   │   └── vehicle_selection_page.py # 차량 선택 페이지
│   └── res                      # 리소스 파일
│
├── main.py                      # 메인 실행 파일
├── result                       # 결과 저장 폴더
├── requirements.txt             # 필요한 패키지 목록
└── setup.py                     # 설정 스크립트
```

## 설치 및 실행 방법

### 요구 사항

- Python 3.x
- 필요한 패키지는 `requirements.txt` 파일에 명시되어 있습니다.

### 설치

1. 리포지토리를 클론합니다:

    ```bash
    git clone <repository-url>
    cd parking_kiosk
    ```

2. 가상 환경을 생성하고 활성화합니다:

    ```bash
    python -m venv venv
    source venv/bin/activate   # Windows의 경우 `venv\Scripts\activate`
    ```

3. 필요한 패키지를 설치합니다:

    ```bash
    pip install -r requirements.txt
    ```

### 실행

메인 애플리케이션을 실행합니다:

```bash
python main.py
```

## 주요 기능

- **차량 입차**: OCR을 사용하여 차량 번호판을 인식하고, 입차를 처리합니다.
- **차량 출차**: 차량 번호판을 입력받아 출차를 처리합니다.
- **정산**: 정산 페이지에서 주차 요금을 계산하고 결제할 수 있습니다. (결제 시스템 x)
- **주차 차단기 제어**: 주차 차단기를 제어하여 차량의 입출차를 관리합니다.

## 개발 정보

- **개발 언어**: Python
- **GUI 라이브러리**: PyQt6
- **기타 라이브러리**: OpenCV, pika, paho-mqtt, requests