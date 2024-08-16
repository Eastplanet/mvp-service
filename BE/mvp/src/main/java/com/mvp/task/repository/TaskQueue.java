package com.mvp.task.repository;

import com.mvp.task.dto.Task;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 작업 큐
 */
@Component
public class TaskQueue {
    private final Queue<Task> taskQueue = new ConcurrentLinkedQueue<>();
    private final Queue<Task> waitingQueue = new ConcurrentLinkedQueue<>();

    public void addTask(Task task) {
        taskQueue.add(task);
    }

    public void addWaitingTask(Task task) {
        waitingQueue.add(task);
    }

    public Task getNextTask() {
        return taskQueue.poll();
    }

    public Task getNextWaitingTask() {
        return waitingQueue.poll();
    }

    public boolean hasTasks() {
        return !taskQueue.isEmpty();
    }

    public boolean hasWaitingTasks() {
        return !waitingQueue.isEmpty();
    }
}
