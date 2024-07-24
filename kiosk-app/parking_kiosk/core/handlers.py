# core/handlers.py

import requests

def handle_enter():
    base_url = "http://localhost:8080/test"  # 서버 엔드포인트 URL을 입력하세요
    headers = {
        'Content-Type': 'application/json'
    }
    params = {
        'vehicle_id': 1,
        'parking_lot_id': 1
    }
    
    try:
        response = requests.get(base_url, headers=headers, params=params)
        response.raise_for_status()  # HTTP 요청에 대한 예외 처리
        
        try:
            response_data = response.json()  # 응답을 JSON으로 파싱 시도
            print(f"Success: {response.status_code}, {response_data}")
        except ValueError:
            print(f"Success: {response.status_code}, {response.text}")  # 응답이 JSON이 아닌 경우
        
    except requests.exceptions.RequestException as e:
        print(f"HTTP Request failed: {e}")

    print("입차 명령 전송 완료")

def handle_exit():
    print('출차 명령 전송 완료')
