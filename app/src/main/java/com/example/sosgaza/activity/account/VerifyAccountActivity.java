package com.example.sosgaza.activity.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sosgaza.R;
import com.example.sosgaza.databinding.ActivityVerifyAccountBinding;

public class VerifyAccountActivity extends AppCompatActivity {

    ActivityVerifyAccountBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityVerifyAccountBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.ResendTvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //إنشاء Dialog بخصائص بسيطة

                TextView title = new TextView(VerifyAccountActivity.this);
                title.setText("إعادة الإرسال");
                title.setPadding(40, 40, 40, 20);
                title.setTextSize(20);
                title.setTextColor(getResources().getColor(R.color.red1));

                AlertDialog.Builder builder = new AlertDialog.Builder(VerifyAccountActivity.this);
                builder.setCustomTitle(title);
                builder.setMessage("سيتم إرسال رمز التأكيد مرة أخرى.");
                builder.setPositiveButton("حسنًا", null);
                builder.show();
            }
        });

        binding.confirmBtnVerifyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VerifyAccountActivity.this, PersonalInformationActivity.class));
                finish();
            }
        });
    }
}