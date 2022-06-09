package com.fudan.se.community.controller.response;

import com.fudan.se.community.pojo.task.group.VGroup;
import com.fudan.se.community.vm.GroupTask;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GTasksMapResponse {
    List<VGroup> result;
}
