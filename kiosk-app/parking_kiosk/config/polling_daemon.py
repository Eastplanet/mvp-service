import threading
import time
import requests

class PollingDaemon(threading.Thread):
    def __init__(self, mqtt_client, server_url, poll_interval=5):
        super(PollingDaemon, self).__init__()
        self.mqtt_client = mqtt_client
        self.server_url = server_url
        self.poll_interval = poll_interval
        self.daemon = True
        self.running = True
    
    def run(self):
        while self.running:
            try:
                response = requests.get(self.server_url)
                if(response.status_code == 200 and response.json().get('data')):
                    print("Polling success")
                    # 작업이 있는 경우
                    data = response.json().get('data')
                    parking_bot_serial_number = data.get('parkingBotSerialNumber')
                    start = data.get('start')
                    end = data.get('end')
                    mqtt_message = {
                        "parkingBotSerialNumber": parking_bot_serial_number,
                        "start": start,
                        "end": end
                    }
                    self.mqtt_client.publish_message(mqtt_message)
                    
            except requests.RequestException as e:
                print(f"Error: polling failed - {e}")
                
            time.sleep(self.poll_interval)

    def stop(self):
        self.running = False
    