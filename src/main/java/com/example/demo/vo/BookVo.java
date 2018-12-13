package com.example.demo.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by 92992 on 2018/12/8.
 */
@Getter
@Setter
public class BookVo implements Serializable{
    private Integer bId;
    private String bIsbn;
    private String bTitle;
    private String bDescription;
    private String bReader;
    private String bAuthor;
}
