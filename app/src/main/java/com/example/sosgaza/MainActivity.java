package com.example.sosgaza;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sosgaza.core.Constants;
import com.example.sosgaza.core.PermissionManager;
import com.example.sosgaza.databinding.ActivityMainBinding;
import com.example.sosgaza.fragment.AlertFragment;
import com.example.sosgaza.fragment.CourseFragment;
import com.example.sosgaza.fragment.HomeFragment;
import com.example.sosgaza.fragment.MapFragment;
import com.example.sosgaza.fragment.ProfileFragment;
import com.example.sosgaza.services.MyAccessibilityService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {


    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // ضبط الاتجاه من اليمين لليسار (RTL)
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        binding.btnNavigationViewActivityMain.setOnItemSelectedListener(this);
        binding.btnNavigationViewActivityMain.setSelectedItemId(R.id.home_item);

        binding.btnNavigationViewActivityMain.setBackgroundColor(getResources().getColor(R.color.white3));

        createNotificationChannel();

        requestAppPermissions();
    }

    HomeFragment homeFragment = new HomeFragment();
    MapFragment mapFragment = new MapFragment();
    AlertFragment alertFragment = new AlertFragment();
    CourseFragment courseFragment = new CourseFragment();
    ProfileFragment profileFragment = new ProfileFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_item:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_fragment_activityMain, homeFragment)
                        .commit();
                return true;
            case R.id.map_item:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_fragment_activityMain, mapFragment)
                        .commit();
                return true;
            case R.id.alerts_item:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_fragment_activityMain, alertFragment)
                        .commit();
                return true;
            case R.id.courses_item:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_fragment_activityMain, courseFragment)
                        .commit();
                return true;
            case R.id.profile_item:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_fragment_activityMain, profileFragment)
                        .commit();
                return true;
        }
        return false;
    }

    private void requestAppPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            PermissionManager.requestPermission(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.SEND_SMS,
                    Manifest.permission.POST_NOTIFICATIONS},
                    PermissionManager.ALL_PERMISSION_REQUEST_CODE);
        }else{
            PermissionManager.requestPermission(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.SEND_SMS},
                    PermissionManager.ALL_PERMISSION_REQUEST_CODE);
        }
    }

    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(
                Constants.NOTIFICATION_CHANNEL_ID,
                "Default Channel",
                NotificationManager.IMPORTANCE_DEFAULT
        );
        channel.setDescription("General Notifications");

        NotificationManager manager = getSystemService(NotificationManager.class);
        if (manager != null) {
            manager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (!PermissionManager.isAccessibilityServiceEnabled(this, MyAccessibilityService.class)){
            PermissionManager.showAccessibilityDialog(this);
        }
    }
}