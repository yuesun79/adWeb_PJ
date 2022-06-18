package com.fudan.se.community.service;

import com.fudan.se.community.pojo.task.Accept;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author SY
 * @since 2022-04-28
 */
public interface AcceptService extends IService<Accept> {
    Integer acceptTask_personal(Integer userId, Integer taskId);
    Integer submitTask_personal(Integer userId, Integer taskId, MultipartFile file, HttpServletRequest request);

    byte[] getFile(Integer userId, Integer taskId);
    void checkCompletion(int userId, int taskId);
}
