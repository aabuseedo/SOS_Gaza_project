package com.example.sosgaza.activity;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sosgaza.core.AppSharedPrefs;
import com.example.sosgaza.core.Constants;
import com.example.sosgaza.databinding.ActivityManageEmergencyNumberBinding;

public class ManageEmergencyNumberActivity extends AppCompatActivity {

    ActivityManageEmergencyNumberBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityManageEmergencyNumberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        String emergencyNumber = AppSharedPrefs.getInstance(this).getString(Constants.EMERGENCY_NUMBER_KEY, "");
        binding.mobileNumEtEmergencyNumber.setText(emergencyNumber);

        binding.ivBackIconActivityEmergencyNumber.setOnClickListener(view -> finish());

        binding.confirmBtnEmergencyNumber.setOnClickListener(view -> showEmergencyNumberDialog());
    }

    private void showEmergencyNumberDialog() {
        String emergencyNumber = binding.mobileNumEtEmergencyNumber.getText().toString().trim();

        if (emergencyNumber.isEmpty()) {
            binding.mobileNumEtEmergencyNumber.setError("يرجى إدخال رقم الطوارىء");
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("تأكيد العملية")
                .setMessage("هل تريد حفظ التغييرات؟")
                .setPositiveButton("تأكيد", (dialog, which) -> {
                    AppSharedPrefs.getInstance(this).putString(Constants.EMERGENCY_NUMBER_KEY, emergencyNumber);
                })
                .setNegativeButton("إلغاء", null)
                .show();
    }
}