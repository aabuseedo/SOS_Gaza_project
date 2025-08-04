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
import com.example.sosgaza.databinding.ActivityChangePhoneNumBinding;
import com.example.sosgaza.databinding.ActivityEditProfileBinding;

public class ChangePhoneNumActivity extends AppCompatActivity {

    ActivityChangePhoneNumBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityChangePhoneNumBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.ivBackIconChangePhoneNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.confirmBtnChangePhoneNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //إنشاء Dialog بخصائص بسيطة

                TextView title = new TextView(ChangePhoneNumActivity.this);
                title.setText("تأكيد العملية");
                title.setPadding(40, 40, 40, 20);
                title.setTextSize(20);
                title.setTextColor(getResources().getColor(R.color.red1));

                AlertDialog.Builder builder = new AlertDialog.Builder(ChangePhoneNumActivity.this);
                builder.setCustomTitle(title);
                builder.setMessage("سيتم إرسال رمز تحقق لتأكيد الرقم الجديد.");
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