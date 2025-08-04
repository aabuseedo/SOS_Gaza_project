package com.example.sosgaza.activity.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sosgaza.databinding.ActivityDescription2Binding;

public class DescriptionActivity2 extends AppCompatActivity {

    ActivityDescription2Binding binding;
    boolean hasMoved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDescription2Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        new Handler().postDelayed(() -> {
            if (!hasMoved) {
                hasMoved = true;
                startActivity(new Intent(DescriptionActivity2.this, DescriptionActivity3.class));
                finish();
            }
        }, 5000);

        binding.Description2Btn.setOnClickListener(v -> {
            if (!hasMoved) {
                hasMoved = true;
                startActivity(new Intent(DescriptionActivity2.this, DescriptionActivity3.class));
                finish();
            }
        });
    }
}