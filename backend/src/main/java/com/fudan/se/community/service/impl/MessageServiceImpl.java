package com.fudan.se.community.service.impl;

import com.fudan.se.community.pojo.task.message.Message;
import com.fudan.se.community.mapper.MessageMapper;
import com.fudan.se.community.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author SY
 * @since 2022-04-28
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

}
