package com.fudan.se.community.service;

import com.fudan.se.community.pojo.task.Task;
import com.fudan.se.community.pojo.task.group.VGroup;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author SY
 * @since 2022-04-28
 */
public interface VGroupService extends IService<VGroup> {
   void checkCompletion(int groupId);


    Integer getRoomId_groupId(Integer groupId);
    void submitTask_group(Integer userId, Integer groupId, MultipartFile file, HttpServletRequest request);
    Task getTask_groupId(Integer groupId);
    void checkUserInGroup(Integer userId, Integer groupId);
    void updateGroupInfo(Integer groupId, Integer groupLeader, String name);
    void assignEV4GroupUsers(Integer userId, Integer groupId, Map<Integer, Double> scores);
    void insert(VGroup vGroup);
}
