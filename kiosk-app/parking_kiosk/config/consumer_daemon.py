import pika
import json
import threading

# RabbitMQ Consumer Daemon
class ConsumerDaemon(threading.Thread):
    def __init__(self, mqtt_client, rabbitmq_host, queue_name):
        super().__init__()
        self.mqtt_client = mqtt_client
        self.rabbitmq_host = rabbitmq_host
        self.queue_name = queue_name
        self.connection = None
        self.channel = None
        self.running = True

    def run(self):
        self.connection = pika.BlockingConnection(pika.ConnectionParameters(host=self.rabbitmq_host))
        self.channel = self.connection.channel()
        self.channel.queue_declare(queue=self.queue_name, durable=True)
        self.channel.basic_qos(prefetch_count=1)
        self.channel.basic_consume(queue=self.queue_name, on_message_callback=self.on_message)

        print('Waiting for messages. To exit press CTRL+C')
        while self.running:
            self.connection.process_data_events(time_limit=1)

    def on_message(self, ch, method, properties, body):
        print("Received message from RabbitMQ")
        data = json.loads(body)
        parking_bot_serial_number = data.get('parkingBotSerialNumber')
        parked_vehicle_id = data.get('parkedVehicleId')
        start = data.get('start')
        end = data.get('end')
        type = data.get('type')
        mqtt_message = {
            "parkingBotSerialNumber": parking_bot_serial_number,
            "parked_vehicle_id" : parked_vehicle_id,
            "start": start,
            "end": end,
            "type": type
        }
        self.mqtt_client.publish_message(mqtt_message)
        ch.basic_ack(delivery_tag=method.delivery_tag)

    def stop(self):
        self.running = False
        if self.connection:
            self.connection.close()
