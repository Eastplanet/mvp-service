# core/handlers.py

import base64
import json
import os
import requests
from config.config import API_KEY_LPR, SERVER_URL, get_api_key, set_api_key
from urllib3.exceptions import MaxRetryError

# 연결 예외 처리
def handle_connection_excetpion():
    response = {
        'status': None,
        'data': None,
        'message': '인터넷 연결 상태를 확인해주세요.'
    }
    
    return response

def handle_lpr_api(files):
    try:
        url = 'https://apis.openapi.sk.com/sigmeta/lpr/v1'
        headers = {
                'accept': 'application/json',
                'appKey': API_KEY_LPR
            }
            
        response = requests.post(url, headers=headers, files=files)
            
        response_json = response.json()
        return response_json
    except (MaxRetryError, requests.ConnectionError) as e: 
        return handle_connection_excetpion()
    
def handle_task_complete(data):
    print(data)
    
    print("작업 완료 처리 시작")
    
    try:
        url = SERVER_URL + "/parking-bot/status/complete"
        
        headers = {
            'Content-Type': 'application/json',
            'API-KEY': get_api_key()
        }
        
        payload = {
            'parkingBotSerialNumber': data.get('parkingBotSerialNumber'),
            'parkedVehicleId': data.get('parked_vehicle_id'),
            'start': data.get('start'),
            'end': data.get('end'),
            'type': data.get('type')
        }
        
        response = requests.patch(url, data=json.dumps(payload), headers=headers)

        while(response.status_code != 200):
            response = requests.patch(url, data=json.dumps(payload), headers=headers)
    except (MaxRetryError, requests.ConnectionError) as e: 
        return handle_connection_excetpion()

def handle_enter(image_path, license_plate, entrance_time):
    try:
        print('입차 명령 전송 시작')
        url = SERVER_URL + "/parking-bot/enter"  # 서버 엔드포인트 URL을 입력하세요

        # 이미지 파일을 읽어서 Base64로 인코딩
        with open(image_path, 'rb') as image_file:
            image_data = base64.b64encode(image_file.read()).decode('utf-8')
        
        print(f"Image Data: {image_data}")
        
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
        
        return response.json()
    except (MaxRetryError, requests.ConnectionError) as e: 
        return handle_connection_excetpion()
        
def handle_exit(licensePlate):
    try:
        url = SERVER_URL + "/parking-bot/exit/" + licensePlate
        headers = {
            'API-KEY': get_api_key()
        }
        response = requests.delete(url, headers=headers)
        return response.json() 
    except (MaxRetryError, requests.ConnectionError) as e: 
        return handle_connection_excetpion()
    
# 차량 정보 가져오기
def handle_get_vehicles(license_plate):
    try:
        url = SERVER_URL + "/parked-vehicle?backNum=" + license_plate
        headers = {
            'API-KEY': get_api_key()
        }
        response = requests.get(url, headers=headers)
        
        return response.json()
    except (MaxRetryError, requests.ConnectionError, requests.exceptions.ChunkedEncodingError) as e: 
        return handle_connection_excetpion()

# 최초 로그인
def kiosk_login():
    try:
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
    except (MaxRetryError, requests.ConnectionError) as e: 
        return handle_connection_excetpion()
    