import numpy as np
import websocket
import json
import math
from multiprocessing import Queue

def on_message(ws, message, queue):
    data = json.loads(message)
    try:
        scan_data = data['msg']
        angle_min = scan_data['angle_min']
        angle_increment = scan_data['angle_increment']
        ranges = scan_data['ranges']
        num_ranges = len(ranges)

        # 라디안을 도 단위로 변환
        angle_increment_deg = angle_increment * (180 / math.pi)
        segment_angle_size_deg = angle_increment_deg * 9
        num_segments = 360.0 / segment_angle_size_deg  # 2*pi 라디안을 360도로 변환
        segment_median_values = []
        segment_angles = []

        for i in range(int(num_segments)):
            segment_start_index = int(i * (num_ranges / num_segments))
            segment_end_index = int((i + 1) * (num_ranges / num_segments))
            segment_values = ranges[segment_start_index:segment_end_index]
            segment_values = [value for value in segment_values if value is not None]
            if segment_values:
                average_value = np.average(segment_values)
                segment_median_values.append(average_value)
                segment_angles.append((i * segment_angle_size_deg, (i + 1) * segment_angle_size_deg))

        if segment_median_values:
            min_value = min(segment_median_values)
            min_index = segment_median_values.index(min_value)
            min_angle_range = segment_angles[min_index]
            
            if not queue.empty():
                queue.get()  # 기존 값을 제거

            queue.put(min_value)
            # print(f'Queue : {list(queue.queue)}', end="")
            # print(min_value)
            # print(f'minDist : {min_value:.2f}m | {min_angle_range[0]:.2f}~{min_angle_range[1]:.2f}deg')
        else:
            print('No valid distance found in any segment')     
        
    except KeyError as e:
        print(f"KeyError: {e}")
    except json.JSONDecodeError as e:
        print(f"JSONDecodeError: {e}")

def on_error(ws, error):
    print(f"Error: {error}")

def on_close(ws, close_status_code, close_msg):
    print(f'\033[1;33m[Warning]\033[0m LiDAR Connection closed!')

def on_open(ws):
    print("LiDAR Connection opened!")
    subscribe_message = json.dumps({
        "op": "subscribe",
        "topic": "/scan"
    })
    ws.send(subscribe_message)

def start_lidar(queue):
    websocket.enableTrace(False)
    ws = websocket.WebSocketApp("ws://localhost:9090/",
                                on_open=on_open,
                                on_message=lambda ws, msg: on_message(ws, msg, queue),
                                on_error=on_error,
                                on_close=on_close)
    ws.run_forever()

if __name__ == "__main__":
    queue = Queue()
    start_lidar(queue)
