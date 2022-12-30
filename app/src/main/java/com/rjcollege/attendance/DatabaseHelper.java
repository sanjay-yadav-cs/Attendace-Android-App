package com.rjcollege.attendance;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;


@Database(entities = { Attendance.class,User.class},exportSchema = false,version = 1)
@TypeConverters({ImageTypeConverter.class})
public abstract class DatabaseHelper extends RoomDatabase {

    private static final String DB_NAME = "expensedb";

    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getDB(Context context){

        if (instance==null){
            instance = Room.databaseBuilder(context,DatabaseHelper.class,DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries().build();
        }

        return instance;
    }

    public abstract AttencanceDao attencanceDao();
    public abstract UserDao userDao();

}
