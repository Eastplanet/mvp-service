## 주차장 키오스크 애플리케이션

이 프로젝트는 PyQt와 MQTT를 사용하여 만든 주차장 키오스크 애플리케이션입니다. 애플리케이션은 차량의 입차 및 출차를 처리하며, 화면 전환을 동적으로 관리합니다.

### 주요 기능

1. **시작 화면**: 차량의 입차 및 출차를 선택할 수 있습니다.
2. **입차 화면**: 차량 번호판을 인식하여 사용자가 확인할 수 있습니다.
3. **출차 화면**: 차량의 뒷 4자리 번호를 입력받아 일치하는 차량 목록을 조회합니다.
4. **정산 화면**: 선택한 차량의 금액을 확인하고 정산할 수 있습니다.

### 디렉토리 구조

```
parking_kiosk/
├── main.py
├── config/
│   └── config.py
├── core/
│   ├── mqtt_client.py
│   ├── camera.py
│   ├── handlers.py
│   └── ocr_reader.py
├── gui/
│   ├── main_window.py
│   ├── start_window.py
│   ├── enter_window.py
│   ├── exit_window.py
│   └── settlement_window.py
├── resources/
├── requirements.txt
└── README.md
```

### 설정 파일

`config/config.py` 파일은 MQTT 브로커 설정을 포함합니다.

### 핵심 로직

`core/mqtt_client.py` 파일은 MQTT 클라이언트를 설정하고 연결 및 메시지 처리 로직을 포함합니다.

`core/handlers.py` 파일은 입차 및 출차 명령을 처리하는 함수를 포함합니다.

`core/camera.py` 번호판 인식을 위한 카메라 촬용 함수를 포함합니다.

`core/ocr_reader.py` OCR을 활용해 번호판 -> 문자열 함수를 포함합니다.

### GUI 구성

`gui/start_window.py` 파일은 시작 화면으로, 입차 및 출차 버튼이 있습니다.

`gui/enter_window.py` 파일은 입차 화면으로, 차량 번호판을 인식하여 사용자에게 확인을 요청합니다.

`gui/exit_window.py` 파일은 출차 화면으로, 차량의 뒷 4자리 번호를 입력받아 일치하는 차량 목록을 조회합니다.

`gui/settlement_window.py` 파일은 정산 화면으로, 선택한 차량의 금액을 확인하고 정산할 수 있습니다.

`gui/main_window.py` 파일은 메인 윈도우로, `QStackedWidget`을 사용하여 화면 전환을 관리합니다.

### 설치 및 실행

1. 이 저장소를 클론합니다.

   ```bash
   git clone <repository_url>
   cd parking_kiosk
   ```

2. 필요한 패키지를 설치합니다.

   ```bash
   pip install -r requirements.txt
   ```

3. 애플리케이션을 실행합니다.

   ```bash
   python main.py
   ```

### 요구 사항

- Python 3.7 이상
- PyQt5
- paho-mqtt

### 사용 방법

1. **시작 화면**에서 "입차" 또는 "출차" 버튼을 클릭합니다.
2. **입차 화면**에서 차량 번호판을 확인하고 "확인" 버튼을 클릭합니다.
3. **출차 화면**에서 차량의 뒷 4자리 번호를 입력하고 "조회" 버튼을 클릭합니다.
4. 일치하는 차량 목록에서 차량을 선택하고 정산 화면으로 이동합니다.
5. **정산 화면**에서 금액을 확인하고 "정산" 버튼을 클릭합니다.
