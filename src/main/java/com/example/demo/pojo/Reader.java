package com.example.demo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wxy
 * @since 2018-12-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Reader implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "username", type = IdType.NONE)
    private String username;

    private String fullname;

    private String password;

    @TableField("regTime")
    private Date regTime;


}
