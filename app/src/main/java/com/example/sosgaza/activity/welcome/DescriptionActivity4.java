package com.example.sosgaza.activity.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sosgaza.activity.account.LoginActivity;
import com.example.sosgaza.databinding.ActivityDescription4Binding;

public class DescriptionActivity4 extends AppCompatActivity {

    ActivityDescription4Binding binding;
    boolean hasMoved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDescription4Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        new Handler().postDelayed(() -> {
            if (!hasMoved) {
                hasMoved = true;
                startActivity(new Intent(DescriptionActivity4.this, LoginActivity.class));
                finish();
            }
        }, 5000);

        binding.Description4Btn.setOnClickListener(v -> {
            if (!hasMoved) {
                hasMoved = true;
                startActivity(new Intent(DescriptionActivity4.this, LoginActivity.class));
                finish();
            }
        });
    }
}