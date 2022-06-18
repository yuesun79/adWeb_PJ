package com.fudan.se.community.controller.dto;


import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RegisterDto {


    private String username;
    private String password;
    private String email;
    private String phone_num;
    private String gender;
    private String register_date;

}
