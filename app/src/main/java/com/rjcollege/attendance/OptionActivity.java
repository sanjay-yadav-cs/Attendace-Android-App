package com.rjcollege.attendance;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rjcollege.attendance.databinding.ActivityOptionBinding;

public class OptionActivity extends AppCompatActivity {
ActivityOptionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = ActivityOptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        binding.optionpagebtnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OptionActivity.this,LoginActivity.class));
            }
        });

        binding.optionpagebtnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OptionActivity.this,RegisterActivity.class));
            }
        });




    }
}