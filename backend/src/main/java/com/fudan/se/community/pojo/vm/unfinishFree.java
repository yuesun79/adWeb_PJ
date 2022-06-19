package com.fudan.se.community.pojo.vm;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fudan.se.community.pojo.task.Accept;
import com.fudan.se.community.pojo.task.group.VGroup;
import com.fudan.se.community.pojo.user.User;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.List;

@Data
@ToString
public class unfinishFree {
    List<unfinishTask> personalFree;
    List<unfinishGTask> groupFree;

    public unfinishFree( List<unfinishTask> personalFree, List<unfinishGTask> groupFree){
        this.personalFree=personalFree;
        this.groupFree=groupFree;
    }

}
