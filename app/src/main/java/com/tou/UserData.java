package com.tou;

/**
 * Created by user1 on 2018-02-23.
 */


public class UserData {
    private String name;
    private String birth;
    private long startDate;

    public UserData(String name, String birth, long startDate) {
        this.name = name;
        this.birth = birth;
        this.startDate = startDate;
    }

    public String getName() {return name;}

    public String getBirth() {return birth;}

    public long getStartDate() {
        return startDate;
    }


}
