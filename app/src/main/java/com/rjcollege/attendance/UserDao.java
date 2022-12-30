package com.rjcollege.attendance;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface UserDao {


    @Query("select * from user")
    List<User> getAllUser();


    @Query("select * from user where User.mobilenumber= :mobilenumber")
    boolean getUserByMobile(String mobilenumber);

    @Query("select * from user where User.mobilenumber= :mobilenumber and User.password= :password")
    boolean getUserByMobileandPassword(String mobilenumber,String password);

    @Query("select * from user where User.mobilenumber= :mobilenumber and User.password= :password")
    User LoginDetails(String mobilenumber,String password);



    @Query("select * from user where id= :id")
    User getUserById(Long id);

    @Insert()
    void addUser(User user);

    @Update
    void makeAttedance(User user);

    @Delete
    void deleteUser(User user);


}
