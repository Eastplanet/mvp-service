import os
import base64
import cv2
from core.handlers import handle_lpr_api
from static.ko_en_mapper import ko_en_mapper

class Camera:
    def __init__(self):
        pass
    
    def capture_image(self):
        file_path = './result/captured_img.jpeg'
        cap = cv2.VideoCapture(0)
        ret, frame = cap.read()
        if not ret:
            raise Exception("웹캠에서 이미지를 가져올 수 없습니다.")

        cap.release()
        cv2.imwrite(file_path, frame)
        
        print(f"이미지 저장완료 : {file_path}")

    def encode_image_to_base64(self, image_buffer):
        image_base64 = base64.b64encode(image_buffer).decode('utf-8')
        return image_base64

    def ocr_reader(self):
        self.capture_image()
        

        # 이미지 리사이즈
        image = cv2.imread('./result/captured_img.jpeg')
        resized_img = self.resize_image(image)

        temp_file_path = './result/temp_image.jpeg'
        cv2.imwrite(temp_file_path, resized_img)

        with open(temp_file_path,'rb') as image_file:
            files = {
                'File': ('captured_img.jpeg', image_file, 'image/jpeg')
            }
            
            response_json = handle_lpr_api(files)
            
            if 'result' in response_json and 'objects' in response_json['result']:
                lp_string = response_json['result']['objects'][0]['lp_string']
                return self.kor_converter(lp_string)
            else:
                return None
         
    def resize_image(self, image, max_width=1024, max_height=1024):
        height, width = image.shape[:2]
        if width > max_width or height > max_height:
            scailing_factor = min(max_width / width, max_height / height)
            new_size = (int(width * scailing_factor), int(height * scailing_factor))
            resized_image = cv2.resized(image, new_size, interpolation=cv2.INTER_AREA)
            return resized_image
        return image

    def kor_converter(self, lp_string):
        for eng, kor in ko_en_mapper.items():
            lp_string = lp_string.replace(eng, kor + "-")
        return lp_string