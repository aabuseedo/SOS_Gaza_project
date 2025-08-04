package com.example.sosgaza.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sosgaza.R;
import com.example.sosgaza.databinding.ActivityChangePasswordBinding;
import com.example.sosgaza.databinding.ActivityChangePhoneNumBinding;
import com.example.sosgaza.databinding.ActivityManageEmergencyNumberBinding;

public class ChangePasswordActivity extends AppCompatActivity {

    ActivityChangePasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.ivBackIconChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.editBtnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //إنشاء Dialog بخصائص بسيطة

                TextView title = new TextView(ChangePasswordActivity.this);
                title.setText("تأكيد العملية");
                title.setPadding(40, 40, 40, 20);
                title.setTextSize(20);
                title.setTextColor(getResources().getColor(R.color.red1));

                AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this);
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