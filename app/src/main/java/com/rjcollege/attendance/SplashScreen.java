package com.rjcollege.attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        boolean islogin = sh.getBoolean("isLogin",false);



        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();

                try {

                    sleep(1500);

                }catch (Exception exception){

                    exception.printStackTrace();

                }finally {
                    if (islogin){
                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(SplashScreen.this, OptionActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }

                }


            }
        };
        thread.start();
    }
}