package com.rjcollege.attendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.rjcollege.attendance.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        DatabaseHelper databaseHelper = DatabaseHelper.getDB(this);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);

        SharedPreferences.Editor myEdit = sharedPreferences.edit();


        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {


                    if (binding.mobilenumber.getText().toString().isEmpty()){
                        binding.mobilenumber.setError("Enter Mobile Number");
                        binding.mobilenumber.requestFocus();
                        return;
                    }

                    if ( binding.mobilenumber.getText().toString().length()<10){
                        binding.mobilenumber.setError("Enter Valid Mobile Number");
                        binding.mobilenumber.requestFocus();
                        return;
                    }

                    if (binding.password.getText().toString().isEmpty()){
                        binding.password.setError("Enter Password");
                        binding.password.requestFocus();
                        return;
                    }



                    if (databaseHelper.userDao().getUserByMobileandPassword(binding.mobilenumber.getText().toString(),binding.password.getText().toString())){
//                        Toast.makeText(LoginActivity.this,"User Already Exist",Toast.LENGTH_LONG).show();

                        User myuser = databaseHelper.userDao().LoginDetails(binding.mobilenumber.getText().toString(),binding.password.getText().toString());

                        myEdit.putBoolean("isLogin",true);
                        myEdit.putString("name", myuser.getName());
                        myEdit.putString("mobilenumber", myuser.getMobileNumber());
                        myEdit.putInt("id", myuser.getId());
                        myEdit.apply();
                        Toast.makeText(LoginActivity.this,"Login Successfully !!!!",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    }else {
                        Toast.makeText(LoginActivity.this,"Mobile Number or Password is Wrong",Toast.LENGTH_LONG).show();
                    }


                }catch (Exception e){
//                    Toast.makeText(RegisterActivity.this,"Faild to Create Account Try Again",Toast.LENGTH_SHORT).show();
                    Toast.makeText(LoginActivity.this,e.toString(),Toast.LENGTH_LONG).show();
                }




//                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }
        });


        binding.gotosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
    }
}