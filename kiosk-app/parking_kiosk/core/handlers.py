# core/handlers.py

import base64
import json
import os
import requests
from config.config import SERVER_URL, get_api_key, set_api_key

def handle_enter(image_path, license_plate, entrance_time):
    print('입차 명령 전송 시작')
    url = SERVER_URL + "/parking-bot/enter"  # 서버 엔드포인트 URL을 입력하세요

    # 이미지 파일을 읽어서 Base64로 인코딩
    with open(image_path, 'rb') as image_file:
        image_data = base64.b64encode(image_file.read()).decode('utf-8')
    
    # JSON 데이터 구성
    payload = {
        'image': image_data,
        'licensePlate': license_plate,
        'entranceTime': entrance_time.isoformat()  # datetime 객체를 ISO 포맷 문자열로 변환
    }
    
    headers = {
        'Content-Type': 'application/json',
        'API-KEY': get_api_key()
    }

    # POST 요청 전송
    response = requests.post(url, data=json.dumps(payload), headers=headers)
        
    print(f"Status Code: {response.status_code}")
    print(f"Response: {response.text}")
    # os.remove('./result/temp_image.jpeg')
    
    return response.json()
    
def handle_exit(licensePlate):
    url = SERVER_URL + "/parking-bot/exit/" + licensePlate
    headers = {
        'API-KEY': get_api_key()
    }
    response = requests.delete(url, headers=headers)
    
    if response.status_code == 200:
        return True
    else:
        return False

# 차량 정보 가져오기
def handle_get_vehicles(license_plate):
    url = SERVER_URL + "/parked-vehicle?backNum=" + license_plate
    headers = {
        'API-KEY': get_api_key()
    }
    response = requests.get(url, headers=headers)
    
    vehicles = []
    if response.status_code == 200:
        vehicles = response.json().get('data')
    
    print(vehicles)
    
    return vehicles

def kiosk_login():
    url = SERVER_URL + "/manager/login"
    
    headers = {
        'Content-Type': 'application/json'
    }
    
    payload = {
        'email': 'kiosk',
        'password': '1234'
    }
    
    response = requests.post(url, data=json.dumps(payload), headers=headers)
    set_api_key(response.json().get('data').get('apiKey'))
    