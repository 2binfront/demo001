package com.example.demo001.dto;


public class UserDto {

    private String name;
    private String pwd;
    private long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }


    public void setId(long l) {
        this.id = l;
    }

    public long getId() {
        return  this.id;
    }
}