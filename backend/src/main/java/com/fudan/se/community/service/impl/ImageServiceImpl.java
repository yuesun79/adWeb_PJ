package com.fudan.se.community.service.impl;

import com.fudan.se.community.pojo.image.Image;
import com.fudan.se.community.mapper.ImageMapper;
import com.fudan.se.community.service.ImageService;
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
public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image> implements ImageService {

}