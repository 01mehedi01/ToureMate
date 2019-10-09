package com.example.user.touremate.DataBase;

/**
 * Created by User on 4/2/2018.
 */

public class LoginPojoClass {
    private  String UserName;
    private String UserPassword;
    private String UserPhoneNumber;
    private int ID;


    public LoginPojoClass() {
    }

    public LoginPojoClass(int ID) {
        this.ID = ID;
    }

    public LoginPojoClass(String userName, String userPassword) {
        UserName = userName;
        UserPassword = userPassword;
    }

    public LoginPojoClass(String userName, String userPassword, String userPhoneNumber, int ID) {
        UserName = userName;
        UserPassword = userPassword;
        UserPhoneNumber = userPhoneNumber;
        this.ID = ID;
    }

    public LoginPojoClass(String userName, String userPassword, String userPhoneNumber) {
        UserName = userName;
        UserPassword = userPassword;
        UserPhoneNumber = userPhoneNumber;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

    public String getUserPhoneNumber() {
        return UserPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        UserPhoneNumber = userPhoneNumber;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
