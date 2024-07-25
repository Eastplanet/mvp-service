# core/handlers.py

import base64
import json
import requests

def handle_enter(image_path, license_plate, entrance_time):
    url = "http://localhost:8080/test/enter"  # 서버 엔드포인트 URL을 입력하세요

    # 이미지 파일을 읽어서 Base64로 인코딩
    with open(image_path, 'rb') as image_file:
        image_data = base64.b64encode(image_file.read()).decode('utf-8')
    
    # JSON 데이터 구성
    payload = {
        'image': image_data,
        'license_plate': license_plate,
        'entrance_time': entrance_time.isoformat()  # datetime 객체를 ISO 포맷 문자열로 변환
    }
    
    headers = {
        'Content-Type': 'application/json'
    }

    # POST 요청 전송
    response = requests.post(url, data=json.dumps(payload), headers=headers)
    
    print(f"Status Code: {response.status_code}")
    print(f"Response: {response.text}")

def handle_exit():
    print('출차 명령 전송 완료')
