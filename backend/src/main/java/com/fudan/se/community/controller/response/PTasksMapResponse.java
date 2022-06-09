package com.fudan.se.community.controller.response;

import com.fudan.se.community.vm.Task;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class PTasksMapResponse {
    List<Task> result;
}
