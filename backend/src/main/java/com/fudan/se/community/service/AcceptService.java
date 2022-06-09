package com.fudan.se.community.service;

import com.fudan.se.community.pojo.task.Accept;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author SY
 * @since 2022-04-28
 */
public interface AcceptService extends IService<Accept> {
    Integer acceptTask(Integer userId, Integer taskId);
    Integer acceptTask_personal(Integer userId, Integer taskId);

    void submitTask_personal(Integer userId, Integer taskId, MultipartFile file, HttpServletRequest request);
}
