package com.fudan.se.community.controller.response;

import com.fudan.se.community.pojo.task.VClass;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ClassResponse {
    List<VClass> result;
}
