package com.mvp.task.service;

import com.mvp.task.dto.Task;
import com.mvp.task.repository.TaskQueue;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final RabbitTemplate rabbitTemplate;
    private final TaskQueue taskQueue;
    public void sendMessage(Task taskDTO){
        rabbitTemplate.convertAndSend("task_queue", taskDTO);
    }

    public void addWaitingTask(Task task) {
        taskQueue.addWaitingTask(task);
    }

    public boolean hasWaitingTasks() {
        return taskQueue.hasWaitingTasks();
    }

    public Task getNextWaitingTask() {
        return taskQueue.getNextWaitingTask();
    }
}