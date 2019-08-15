package com.example.chenjy.myapplication;

/**
 * Created by chenjy on 2019/4/10.
 */

public class UserVO {
    String userId;
    String userName;
    String userDept;
    String albumUrl;

    public UserVO(String userId, String userName, String userDept) {
        this.userId = userId;
        this.userName = userName;
        this.userDept = userDept;
    }

    public UserVO(String userId, String userName, String userDept, String albumUrl) {
        this.userId = userId;
        this.userName = userName;
        this.userDept = userDept;
        this.albumUrl = albumUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserDept() {
        return userDept;
    }

    public void setUserDept(String userDept) {
        this.userDept = userDept;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userDept='" + userDept + '\'' +
                '}';
    }
}
