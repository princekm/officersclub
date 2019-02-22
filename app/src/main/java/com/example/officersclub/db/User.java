package com.example.officersclub.db;

public class User {
    private String name;
    private Integer staffNo;
    public User(String name,Integer staffNo)
    {
        this.name=name;
        this.staffNo=staffNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStaffNo() {
        return staffNo.toString();
    }

    public void setStaffNo(Integer staffNo) {
        this.staffNo = staffNo;
    }
}
