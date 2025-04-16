package com.ravinder.api.post.scheduler;


import com.ravinder.api.post.scheduler.entity.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.concurrent.*;

@Component
public class TaskScheduler {

    private static final Logger logger = LoggerFactory.getLogger(TaskScheduler.class);

    private final ExecutorService executorService = Executors.newFixedThreadPool(4);

    private final PriorityBlockingQueue<Task> queue = new PriorityBlockingQueue<>(10, Comparator.comparingInt(Task::getPriority));

    private volatile boolean running = true;

    public TaskScheduler(){
        for (int i = 0; i < 4; i++){
            executorService.submit( () -> {
                while (running){
                    try{
                        Task task = queue.poll(1, TimeUnit.SECONDS);
                        if (task != null){
                            logger.info("Executing task: {}", task.getId());
                            task.getAction().run();
                            logger.info("Task completed: {}", task.getId());
                        }
                    } catch (InterruptedException e){
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            });
        }
    }

    public static TaskScheduler getInstance() {
        throw new UnsupportedOperationException("Use Spring injection instead");
    }

    public void scheduleTask(Task task){
        logger.info("Scheduling task: {}", task.getId());
        queue.add(task);
    }

    public void shutdown(){
        running = false;
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)){
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }


}
