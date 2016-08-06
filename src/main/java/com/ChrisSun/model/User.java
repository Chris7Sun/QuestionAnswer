package com.ChrisSun.model;

/**
 * Created by Chris on 2016/7/24.
 */
public class User {
    private int id;//primary key
    private String name;//姓名
    private String password;//密码
    private String salt;//加密盐
    private String headUrl;//头像地址

    public User(){}
    public User(String name, String password, String salt, String headUrl){
        this.name = name;
        this.password = password;
        this.salt = salt;
        this.headUrl = headUrl;
    }

    @Override
    public String toString() {
        return id + "," + name + ":" + headUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }
}
