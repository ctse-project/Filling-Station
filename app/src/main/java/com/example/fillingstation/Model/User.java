package com.example.fillingstation.Model;

public class User {

    private String username;
    private String password;
    private String repassword;
    private String phoneNumber;

    public User() {

    }

    public User(String uname, String pword, String repword,String phoneNo){
        username = uname;
        password = pword;
        repassword = repword;
        phoneNumber = phoneNo;

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setUsername(String uname) {
        this.username = uname;
    }

    public void setPassword(String pword) {
        this.password = pword;
    }

    public void setRepassword(String repword) {
        this.repassword = repword;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


}
