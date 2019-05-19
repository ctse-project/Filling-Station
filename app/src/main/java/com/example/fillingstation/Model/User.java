package com.example.fillingstation.Model;

public class User {

    private String username;
    private String password;
    private String email;
    private String phoneNumber;

    public User() {

    }

    public User(String uname, String pword,String email,String phoneNo){
        username = uname;
        password = pword;
        this.email = email;
        phoneNumber = phoneNo;

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String uname) {
        this.username = uname;
    }

    public void setPassword(String pword) {
        this.password = pword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


}
