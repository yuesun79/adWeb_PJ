package com.fudan.se.community.dto;


import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RegisterDto {


    private String username;
    private String password;
    private String email;
    private String phoneNum;

}
