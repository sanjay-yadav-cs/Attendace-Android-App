package com.rjcollege.attendance;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface AttencanceDao {


    @Query("select * from attendance")
    List<Attendance> getAllAttendace();

    @Query("select id from attendance")
    int getCheckinId();

    @Query("select checkinimage from attendance where Attendance.id= :id")
    byte[] getCheckinImg(int id);

    @Insert()
    long addAttendace(Attendance attendance);

//    @Update
//    void CheckOut(Attendance attendance);

//    @Query("UPDATE attendance SET checkouttime = :checkouttime, checkoutlacation= :checkoutlacation, checkoutimage= :img WHERE ID = 2;")
//    void CheckOut(String checkouttime,String checkoutlacation,byte[] img);

    @Query("UPDATE attendance SET checkouttime = :checkouttime, checkoutlacation= :checkoutlacation,checkoutimage= :img WHERE ID = :id;")
    void CheckOut(int id,String checkouttime,String checkoutlacation,byte[] img);

    @Delete
    void deleteAttendace(Attendance attendance);


}
