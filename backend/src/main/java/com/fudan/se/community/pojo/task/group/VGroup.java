package com.fudan.se.community.pojo.task.group;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.sql.Blob;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author SY
 * @since 2022-04-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="VGroup对象", description="")
public class VGroup implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private Integer taskId;

    private Integer groupLeader;

    private Integer process;

    private String file;


    public VGroup(Integer process, String file) {
        this.process = process;
        this.file = file;
    }

    public VGroup(Integer taskId) {
        this.taskId = taskId;
    }

    public VGroup(Integer id, String name, Integer taskId, Integer groupLeader, Integer process, String file) {
        this.id = id;
        this.name = name;
        this.taskId = taskId;
        this.groupLeader = groupLeader;
        this.process = process;
        this.file = file;
    }
}
