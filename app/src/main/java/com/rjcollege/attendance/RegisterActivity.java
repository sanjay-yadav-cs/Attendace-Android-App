package com.rjcollege.attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.rjcollege.attendance.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
private ActivityRegisterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());





        DatabaseHelper databaseHelper = DatabaseHelper.getDB(this);

        binding.btngotoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    if (binding.name.getText().toString().isEmpty()){
                        binding.name.setError("Enter Name");
                        binding.name.requestFocus();
                        return;
                    }
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

                    if (binding.cpassword.getText().toString().isEmpty()){
                        binding.cpassword.setError("Enter Password");
                        binding.cpassword.requestFocus();
                        return;
                    }

                    if (!binding.cpassword.getText().toString().equals(binding.password.getText().toString())){
                        binding.cpassword.setError("Password and Confirm password must match");
                        binding.cpassword.requestFocus();
                        return;
                    }


                    if (databaseHelper.userDao().getUserByMobile(binding.mobilenumber.getText().toString())){
                        Toast.makeText(RegisterActivity.this,"User Already Exist",Toast.LENGTH_LONG).show();
                    }else{

                        databaseHelper.userDao().addUser(new User(binding.name.getText().toString(),binding.mobilenumber.getText().toString(),binding.password.getText().toString()));
                        Toast.makeText(RegisterActivity.this,"Acount Created Successfully",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));

                    }

//                    boolean u = databaseHelper.userDao().getUserByMobile(binding.mobilenumber.getText().toString());
//
//                    Toast.makeText(RegisterActivity.this,String.valueOf(u), Toast.LENGTH_SHORT).show();

                }catch (Exception e){
//                    Toast.makeText(RegisterActivity.this,"Faild to Create Account Try Again",Toast.LENGTH_SHORT).show();
                    Toast.makeText(RegisterActivity.this,e.toString(),Toast.LENGTH_LONG).show();
                }


            }
        });

    }
}