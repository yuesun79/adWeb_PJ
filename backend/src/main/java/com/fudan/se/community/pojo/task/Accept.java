package com.fudan.se.community.pojo.task;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.sql.Blob;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
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
@ApiModel(value="Accept对象", description="")
public class Accept implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private Integer taskId;

    private Integer process;

    private String file;

    public Accept(Integer process, String file) {
        this.process = process;
        this.file = file;
    }

    public Accept(Integer userId, Integer taskId) {
        this.userId = userId;
        this.taskId = taskId;
    }

    public Accept(Integer id, Integer userId, Integer taskId, Integer process, String file) {
        this.id = id;
        this.userId = userId;
        this.taskId = taskId;
        this.process = process;
        this.file = file;
    }
}
