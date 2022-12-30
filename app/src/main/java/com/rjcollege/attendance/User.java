package com.rjcollege.attendance;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "mobilenumber")
    private String mobileNumber;

    @ColumnInfo(name = "password")
    private String password;


    public User(int id, String name, String mobileNumber, String password) {
        this.id = id;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.password = password;
    }

    @Ignore
    public User(String name, String mobileNumber, String password) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.password = password;
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

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
