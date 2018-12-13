package com.example.demo.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 92992 on 2018/11/27.
 */
@Data
public class User implements  BaseEntity{
    private int id;
    private String email;
    private String userName;
    private String passWord;
    private String nickName;
    private double mark;
    private Date regTime;

    public User() {
    }

    public User(int id, String userName, double mark, String email, String nickName) {
        this.id = id;
        this.userName = userName;
        this.mark = mark;
        this.email = email;
        this.nickName = nickName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", nickName='" + nickName + '\'' +
                ", mark=" + mark +
                ", regTime=" + regTime +
                '}';
    }
}
