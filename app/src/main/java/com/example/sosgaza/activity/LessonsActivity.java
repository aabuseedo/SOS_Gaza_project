package com.example.sosgaza.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sosgaza.R;
import com.example.sosgaza.databinding.ActivityAlertsDetailsBinding;
import com.example.sosgaza.databinding.ActivityLessonsBinding;
import com.example.sosgaza.databinding.ActivityLoginBinding;
import com.example.sosgaza.fragment.AlertFragment;
import com.example.sosgaza.fragment.CourseFragment;

public class LessonsActivity extends AppCompatActivity {

    ActivityLessonsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLessonsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.ivBackIconActivityLessons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LessonsActivity.this, CourseFragment.class));
                finish();
            }
        });
    }
}