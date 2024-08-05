# getLidarData.py

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

        segment_angle_size = 0.5
        num_segments = int(360 / segment_angle_size)
        segment_max_values = []
        segment_angles = []

        for i in range(num_segments):
            segment_start_index = int(i * (num_ranges / num_segments))
            segment_end_index = int((i + 1) * (num_ranges / num_segments))
            segment_values = ranges[segment_start_index:segment_end_index]
            segment_values = [value for value in segment_values if value is not None]
            if segment_values:
                max_value = max(segment_values)
                segment_max_values.append(max_value)
                segment_angles.append((i * segment_angle_size, (i + 1) * segment_angle_size))

        if segment_max_values:
            min_of_max_values = min(segment_max_values)
            min_index = segment_max_values.index(min_of_max_values)
            min_angle_range = segment_angles[min_index]
            # print(min_of_max_values)
            queue.put(min_of_max_values)
        else:
            print('No valid distance found in any segment')
        
    except KeyError as e:
        print(f"KeyError: {e}")
    except json.JSONDecodeError as e:
        print(f"JSONDecodeError: {e}")

def on_error(ws, error):
    print(f"Error: {error}")

def on_close(ws, close_status_code, close_msg):
    print("LiDAR Connection closed!")

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
