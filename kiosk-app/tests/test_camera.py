import cv2

for i in range(38):  # /dev/video0에서 /dev/video37까지 확인
    cap = cv2.VideoCapture(i)
    if cap.isOpened():
        ret, frame = cap.read()
        if ret:
            print(f"웹캠이 /dev/video{i}에 연결되었습니다.")
            cap.release()
            break
        cap.release()
    else:
        print(f"/dev/video{i} 장치가 열리지 않습니다.")
