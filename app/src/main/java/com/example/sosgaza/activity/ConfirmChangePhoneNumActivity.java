package com.example.sosgaza.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sosgaza.R;
import com.example.sosgaza.databinding.ActivityChangePhoneNumBinding;

public class ConfirmChangePhoneNumActivity extends AppCompatActivity {

    ActivityChangePhoneNumBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
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

                TextView title = new TextView(ConfirmChangePhoneNumActivity.this);
                title.setText("إعادة الإرسال");
                title.setPadding(40, 40, 40, 20);
                title.setTextSize(20);
                title.setTextColor(getResources().getColor(R.color.red1));

                AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmChangePhoneNumActivity.this);
                builder.setCustomTitle(title);
                builder.setMessage("سيتم إرسال رمز التأكيد مرة أخرى.");
                builder.setPositiveButton("حسنًا", null);
                builder.show();
            }
        });


    }
}