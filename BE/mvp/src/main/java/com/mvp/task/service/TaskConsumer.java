package com.mvp.task.service;

import com.mvp.task.dto.Task;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class TaskConsumer {
    @RabbitListener(queues = "task_queue")
    public void consumeMessage(Task task){
        System.out.println("Message received: " + task);
    }
}
