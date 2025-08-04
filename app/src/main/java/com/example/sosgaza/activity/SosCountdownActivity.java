package com.example.sosgaza.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.sosgaza.R;
import com.example.sosgaza.core.AlertSound;
import com.example.sosgaza.core.AppSharedPrefs;
import com.example.sosgaza.core.Constants;
import com.example.sosgaza.core.PermissionManager;
import com.example.sosgaza.databinding.ActivitySosCountdownBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

public class SosCountdownActivity extends AppCompatActivity {

    ActivitySosCountdownBinding binding;

    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySosCountdownBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        String emergencyNumber = AppSharedPrefs.getInstance(this).getString(Constants.EMERGENCY_NUMBER_KEY, "");
        binding.etPhoneSosCountdown.setText(emergencyNumber);
        binding.btnCancelSosCountdown.setOnClickListener(view -> {
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            finish();
        });

        initCountDown();
    }

    private void initCountDown() {
        countDownTimer = new CountDownTimer(Constants.COUNT_DOWN_SECONDS * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int secondsRemaining = (int) (millisUntilFinished / 1000);
                // تحديث الرقم في الدائرة
                binding.tvTimerCountSosCountdown.setText(String.valueOf(secondsRemaining));
                // تحديث مؤشر التقدم (ProgressBar)
                int progress = (int) ((millisUntilFinished * 100) / (Constants.COUNT_DOWN_SECONDS * 1000));
                binding.progressCircularSosCountdown.setProgress(progress);
            }

            @Override
            public void onFinish() {
                if (!PermissionManager.isPermissionGranted(SosCountdownActivity.this, Manifest.permission.SEND_SMS)) {
                    PermissionManager.requestPermission(SosCountdownActivity.this, new String[]{Manifest.permission.SEND_SMS}, PermissionManager.SMS_PERMISSION_REQUEST_CODE);
                } else {
                    checkLocationPermission();
                }
            }
        }.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(permissions, grantResults, this, new PermissionManager.PermissionCallback() {
            @Override
            public void onGranted() {
                checkLocationPermission();
            }

            @Override
            public void onDenied(List<String> deniedPermissions) {
                Toast.makeText(SosCountdownActivity.this, "يجب السماح للتطبيق بالوصول إلى الرسائل لاستخدام هذه الميزة", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeniedPermanently(List<String> permanentlyDeniedPermissions) {
                PermissionManager.showAppSettingsDialog(SosCountdownActivity.this, "تم رفض صلاحيات الوصول للرسائل", "لقد قمت برفض صلاحيات الوصول الى الرسائل بشكل دائم. من فضلك قم بتمكينها من إعدادات التطبيق.");
            }
        });
    }

    private void checkLocationPermission() {
        if (PermissionManager.isLocationEnabled(SosCountdownActivity.this)) {
            PermissionManager.getCurrentLocation(SosCountdownActivity.this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            String locationUrl = "http://maps.google.com/maps?q=" + location.getLatitude() + "," + location.getLongitude();
                            sendSmsMessage(locationUrl);
                        }
                    },
                    new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SosCountdownActivity.this, "لم يتمكن التطبيق من تحديد الموقع الحالي", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            PermissionManager.showLocationSettingsDialog(SosCountdownActivity.this);
        }
    }

    private void sendSmsMessage(String locationUrl) {
        String phoneNumber = binding.etPhoneSosCountdown.getText().toString().trim();
        String message = String.format(Constants.EMERGENCY_MESSAGE, locationUrl);
        SmsManager smsManager = SmsManager.getDefault();
        try {
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            if (binding.cbEmergencySosCountdown.isChecked()) {
                smsManager.sendTextMessage(Constants.AMBULANCE_NUMBER, null, message, null, null);
            }
            if (binding.cbPoliceSosCountdown.isChecked()) {
                smsManager.sendTextMessage(Constants.POLICE_NUMBER, null, message, null, null);
            }
            if (binding.cbCivilDefenseSosCountdown.isChecked()) {
                smsManager.sendTextMessage(Constants.CIVIL_DEFENSE_NUMBER, null, message, null, null);
            }
            AlertSound.getInstance(this).playSuccessSound();
            showNotification(true);
        } catch (Exception e) {
            AlertSound.getInstance(this).playFailSound();
            showNotification(false);
        }
        finish();
    }

    @SuppressLint("MissingPermission")
    private void showNotification(boolean isSuccess) {
        if (PermissionManager.isPermissionGranted(SosCountdownActivity.this, Manifest.permission.POST_NOTIFICATIONS)) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, Constants.NOTIFICATION_CHANNEL_ID)
                    .setSmallIcon(R.drawable.sos_icon)
                    .setContentTitle(Constants.NOTIFICATION_TITLE)
                    .setContentText(isSuccess ? Constants.NOTIFICATION_SUCCESS_TEXT : Constants.NOTIFICATION_FAIL_TEXT)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(1, builder.build());
        }
    }
}