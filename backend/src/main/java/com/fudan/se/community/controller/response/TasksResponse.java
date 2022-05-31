package com.fudan.se.community.controller.response;

import com.fudan.se.community.vm.GroupTask;
import com.fudan.se.community.vm.Task;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TasksResponse {
    List<Task> personal;
    List<GroupTask> group;

}
