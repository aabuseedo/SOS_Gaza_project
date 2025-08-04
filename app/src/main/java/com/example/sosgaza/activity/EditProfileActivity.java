package com.example.sosgaza.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sosgaza.R;
import com.example.sosgaza.databinding.ActivityEditProfileBinding;

public class EditProfileActivity extends AppCompatActivity {

    ActivityEditProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.ivBackIconActivityEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //انشاء قائمة بيانات بسيطة

        // قائمة فصائل الدم
        String[] bloodTypes = {"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-", "لا أعلم"};
        ArrayAdapter<String> bloodAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, bloodTypes);
        binding.bloodTypeEtEditProfile.setAdapter(bloodAdapter);
        binding.bloodTypeEtEditProfile.setOnClickListener(v -> binding.bloodTypeEtEditProfile.showDropDown());

        // قائمة الحالات الصحية
        String[] healthStatuses = {"سليم", "ربو", "سكري", "ضغط", "أمراض قلب", "أخرى"};
        ArrayAdapter<String> healthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, healthStatuses);
        binding.medicalStatusEtEtEditProfile.setAdapter(healthAdapter);
        binding.medicalStatusEtEtEditProfile.setOnClickListener(v -> binding.medicalStatusEtEtEditProfile.showDropDown());


        binding.confirmBtnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //إنشاء Dialog بخصائص بسيطة

                TextView title = new TextView(EditProfileActivity.this);
                title.setText("تأكيد العملية");
                title.setPadding(40, 40, 40, 20);
                title.setTextSize(20);
                title.setTextColor(getResources().getColor(R.color.red1));

                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
                builder.setCustomTitle(title);
                builder.setMessage("هل تريد حفظ التغييرات؟");
                // زر التأكيد
                builder.setPositiveButton("تأكيد", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // تنفيذ حفظ التغييرات هنا
                    }
                });
                // زر الإلغاء
                builder.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // إغلاق الـ Dialog
                    }
                });
                builder.show();
            }
        });

    }
}