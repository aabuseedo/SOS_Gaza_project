package com.example.sosgaza.activity.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sosgaza.MainActivity;
import com.example.sosgaza.databinding.ActivityPersonalInformationBinding;

public class PersonalInformationActivity extends AppCompatActivity {

    ActivityPersonalInformationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityPersonalInformationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //انشاء قائمة بيانات بسيطة

        // قائمة فصائل الدم
        String[] bloodTypes = {"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-", "لا أعلم"};
        ArrayAdapter<String> bloodAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, bloodTypes);
        binding.bloodTypeEtPersonalInformation.setAdapter(bloodAdapter);
        binding.bloodTypeEtPersonalInformation.setOnClickListener(v -> binding.bloodTypeEtPersonalInformation.showDropDown());

        // قائمة الحالات الصحية
        String[] healthStatuses = {"سليم", "ربو", "سكري", "ضغط", "أمراض قلب", "أخرى"};
        ArrayAdapter<String> healthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, healthStatuses);
        binding.medicalStatusEtPersonalInformation.setAdapter(healthAdapter);
        binding.medicalStatusEtPersonalInformation.setOnClickListener(v -> binding.medicalStatusEtPersonalInformation.showDropDown());

        binding.confirmBtnPersonalInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PersonalInformationActivity.this, MainActivity.class));
                finish();
            }
        });

    }
}
