package com.tou;

/**
 * Created by user1 on 2018-02-23.
 */


public class UserData {
    private String name;
    private String birth;

    public UserData(String name, String birth) {
        this.name = name;
        this.birth = birth;
    }

    public String getName() {return name;}

    public String getBirth() {return birth;}
}
