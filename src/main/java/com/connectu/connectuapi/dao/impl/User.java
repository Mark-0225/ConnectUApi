package com.connectu.connectuapi.dao.impl;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
@Data
public class User  {
    @TableId
    private Integer userId;
    private String 	email;
    private String password;
    private String userName;
}
