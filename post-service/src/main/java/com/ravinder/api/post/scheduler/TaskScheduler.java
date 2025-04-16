package com.ravinder.api.post.scheduler;


import com.ravinder.api.post.scheduler.entity.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

@Component
public class TaskScheduler {

    private static final Logger logger = LoggerFactory.getLogger(TaskScheduler.class);

    private final ExecutorService executorService = Executors.newFixedThreadPool(4);

    private final PriorityBlockingQueue<Task> queue = new PriorityBlockingQueue<>(10, Comparator.comparingInt(Task::getPriority));

    private volatile boolean running = true;


}
