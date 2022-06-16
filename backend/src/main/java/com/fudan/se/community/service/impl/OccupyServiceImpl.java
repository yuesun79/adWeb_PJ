package com.fudan.se.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fudan.se.community.exception.BadRequestException;
import com.fudan.se.community.mapper.RoomMapper;
import com.fudan.se.community.pojo.task.group.Occupy;
import com.fudan.se.community.mapper.OccupyMapper;
import com.fudan.se.community.pojo.task.group.VGroup;
import com.fudan.se.community.service.OccupyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudan.se.community.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author SY
 * @since 2022-04-28
 */
@Service
public class OccupyServiceImpl extends ServiceImpl<OccupyMapper, Occupy> implements OccupyService {


    @Autowired
    OccupyMapper occupyMapper;
    @Autowired
    RoomMapper roomMapper;
    @Override
    public void romove(int groupId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("id",1);//相当于where id=1

        List<Occupy> list = occupyMapper.selectList(wrapper);
        int roomId =list.get(0).getRoomId();
        //删除occupy
        HashMap<String, Object> map = new HashMap<>();
        map.put("group_id", groupId);
        int result = occupyMapper.deleteByMap(map);
        if (result<0){
            throw new BadRequestException("cant remove room");
        }

    }

    @Override
    public void insert(Occupy occupy) {

        int influenceRows = baseMapper.insert(occupy);
        if (influenceRows==0) {
            throw new BadRequestException("occupy setup fails");
        }
    }
}
