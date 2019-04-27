package com.example.fillingstation.Model;

public class Station {

    private String latlng;
    private String username;
    private String chainname;

    public Station() {

    }

    public Station(String cname, String loc, String uname) {
        latlng = loc;
        username = uname;
        chainname = cname;

    }

    public String getLatlng() {
        return latlng;
    }

    public String getUsername() {
        return username;
    }

    public String getChainname() {
        return chainname;
    }

    public void setLatlng(String latlng) {
        this.latlng = latlng;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setChainname(String chainname) {
        this.chainname = chainname;
    }
}
