package com.rjcollege.attendance;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "attendance")
public class Attendance {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "mobilenumber")
    private String mobileNumber;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "checkintime")
    private String checkInTime;

    @ColumnInfo(name = "checkinlocation")
    private String checkInLocation;

    @ColumnInfo(name = "checkinimage",typeAffinity = ColumnInfo.BLOB)
    private byte[] checkInImage;


    @ColumnInfo(name = "checkouttime")
    private String checkOutTime;

    @ColumnInfo(name = "checkoutlacation")
    private String getCheckOutLocation;

    @ColumnInfo(name = "checkoutimage",typeAffinity = ColumnInfo.BLOB)
    private byte[] getGetCheckOutImage;




    public Attendance(int id, String date, String checkInTime, String checkInLocation, byte[] checkInImage) {
        this.id = id;
        this.date = date;
        this.checkInTime = checkInTime;
        this.checkInLocation = checkInLocation;
        this.checkInImage = checkInImage;
    }

    @Ignore
    public Attendance(String name, String mobileNumber, String date, String checkInTime, String checkInLocation, byte[] checkInImage) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.date = date;
        this.checkInTime = checkInTime;
        this.checkInLocation = checkInLocation;
        this.checkInImage = checkInImage;
    }

    @Ignore
    public Attendance(int id, String checkOutTime, String getCheckOutLocation, byte[] getGetCheckOutImage) {
        this.id = id;
        this.checkOutTime = checkOutTime;
        this.getCheckOutLocation = getCheckOutLocation;
        this.getGetCheckOutImage = getGetCheckOutImage;
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


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getCheckInLocation() {
        return checkInLocation;
    }

    public void setCheckInLocation(String checkInLocation) {
        this.checkInLocation = checkInLocation;
    }

    public byte[] getCheckInImage() {
        return checkInImage;
    }

    public void setCheckInImage(byte[] checkInImage) {
        this.checkInImage = checkInImage;
    }

    public byte[] getGetGetCheckOutImage() {
        return getGetCheckOutImage;
    }

    public void setGetGetCheckOutImage(byte[] getGetCheckOutImage) {
        this.getGetCheckOutImage = getGetCheckOutImage;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getGetCheckOutLocation() {
        return getCheckOutLocation;
    }

    public void setGetCheckOutLocation(String getCheckOutLocation) {
        this.getCheckOutLocation = getCheckOutLocation;
    }

}
