package com.example.sosgaza.activity.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sosgaza.databinding.ActivityDescription1Binding;

public class DescriptionActivity1 extends AppCompatActivity {

    ActivityDescription1Binding binding;
    boolean hasMoved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDescription1Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        new Handler().postDelayed(() -> {
            if (!hasMoved) {
                hasMoved = true;
                startActivity(new Intent(DescriptionActivity1.this, DescriptionActivity2.class));
                finish();
            }
        }, 5000);

        binding.Description1Btn.setOnClickListener(v -> {
            if (!hasMoved) {
                hasMoved = true;
                startActivity(new Intent(DescriptionActivity1.this, DescriptionActivity2.class));
                finish();
            }
        });
    }
}
