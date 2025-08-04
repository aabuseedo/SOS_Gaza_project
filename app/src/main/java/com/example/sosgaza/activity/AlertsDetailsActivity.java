package com.example.sosgaza.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sosgaza.databinding.ActivityAlertsDetailsBinding;
import com.example.sosgaza.fragment.AlertFragment;


public class AlertsDetailsActivity extends AppCompatActivity {

    ActivityAlertsDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAlertsDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.ivBackIconAlertDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AlertsDetailsActivity.this, AlertFragment.class));
                finish();
            }
        });
    }
}