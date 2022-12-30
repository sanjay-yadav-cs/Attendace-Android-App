package com.rjcollege.attendance;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;

public class ImageTypeConverter {


    @TypeConverter
    public static byte[] getStringFromBitmap(Bitmap bitmapimg){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmapimg.compress(Bitmap.CompressFormat.PNG,0,byteArrayOutputStream);
        byte [] b = byteArrayOutputStream.toByteArray();

        return b;
    }


    @TypeConverter
    public static Bitmap getBitmapFromStr(byte[] arr){

        return BitmapFactory.decodeByteArray(arr,0,arr.length);
    }




}
