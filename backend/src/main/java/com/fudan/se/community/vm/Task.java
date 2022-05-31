package com.fudan.se.community.vm;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
public class Task {
    // task + publisher_user
    private Integer id;
    private String name;
    private String description;
    private Integer ev;
    private Integer optional;
    private Integer teamSize;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Timestamp ddl;
    private Integer validity;

    private Integer publisherId;
    private String publisherName;
    private String publisherEmail;
    private String publisherPhone;
}
