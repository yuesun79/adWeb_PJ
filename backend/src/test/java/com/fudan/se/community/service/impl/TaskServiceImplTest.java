package com.fudan.se.community.service.impl;

import com.fudan.se.community.vm.Task;
import com.fudan.se.community.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@Slf4j
@SpringBootTest
class TaskServiceImplTest {
    @Autowired
    TaskService taskService;
    @Test
    void findTask_id() {
        Integer id = 2;
        Task task = taskService.findTask_id(id);
        log.info(task.toString());
    }
    @Test
    void retrieveAllTasks_class() {
        Integer classId = 3;
        taskService.retrieveAllTasks_class(classId);
    }

}