package com.example.sosgaza.core;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

public class PermissionManager {

    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    public static final int SMS_PERMISSION_REQUEST_CODE = 1002;
    public static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 1003;
    public static final int ALL_PERMISSION_REQUEST_CODE = 1004;

    public interface PermissionCallback {
        void onGranted();

        void onDenied(List<String> deniedPermissions);

        void onDeniedPermanently(List<String> permanentlyDeniedPermissions);
    }

    public static void requestPermission(Activity activity, String[] permissions, int requestCode) {
        activity.requestPermissions(permissions, requestCode);
    }

    public static void requestPermission(Fragment fragment, String[] permissions, int requestCode) {
        fragment.requestPermissions(permissions, requestCode);
    }

    public static void handlePermissionsResult(
            String[] permissions,
            int[] grantResults,
            Activity activity,
            PermissionCallback callback
    ) {
        List<String> deniedPermissions = new ArrayList<>();
        List<String> permanentlyDeniedPermissions = new ArrayList<>();

        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                if (isPermissionDeniedPermanently(activity, permissions[i])) {
                    permanentlyDeniedPermissions.add(permissions[i]);
                } else {
                    deniedPermissions.add(permissions[i]);
                }
            }
        }

        if (deniedPermissions.isEmpty() && permanentlyDeniedPermissions.isEmpty()) {
            callback.onGranted();
        } else if (!permanentlyDeniedPermissions.isEmpty()) {
            callback.onDeniedPermanently(permanentlyDeniedPermissions);
        } else {
            callback.onDenied(deniedPermissions);
        }
    }

    public static boolean isPermissionGranted(Context context, String permission) {
        return ContextCompat.checkSelfPermission(
                context, permission
        ) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean isPermissionDeniedPermanently(Activity activity, String permission) {
        return !ActivityCompat.shouldShowRequestPermissionRationale(
                activity, permission
        );
    }

    public static boolean isLocationEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @SuppressLint("MissingPermission")
    public static void getCurrentLocation(Context context, OnSuccessListener<Location> onSuccessListener, OnFailureListener onFailureListener) {
        FusedLocationProviderClient fusedLocationClient;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        fusedLocationClient.getLastLocation().addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    public static boolean isAccessibilityServiceEnabled(Context context, Class<? extends AccessibilityService> service) {
        String expectedComponentName = new ComponentName(context, service).flattenToString();
        String enabledServices = Settings.Secure.getString(
                context.getContentResolver(),
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        );

        if (enabledServices == null) return false;

        TextUtils.SimpleStringSplitter splitter = new TextUtils.SimpleStringSplitter(':');
        splitter.setString(enabledServices);

        while (splitter.hasNext()) {
            String componentName = splitter.next();
            if (componentName.equalsIgnoreCase(expectedComponentName)) {
                return true;
            }
        }

        return false;
    }

    public static void showAccessibilityDialog(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("خدمة الوصول غير مفعلة")
                .setMessage("من فضلك قم بتفعيل خدمة الوصول")
                .setPositiveButton("تفعيل", (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                })
                .setNegativeButton("إلغاء", null)
                .show();
    }

    public static void showLocationSettingsDialog(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("الGPS غير مفعل")
                .setMessage("من فضلك فعل الGPS لاستخدام ميزات الخريطة.")
                .setPositiveButton("تفعيل", (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    context.startActivity(intent);
                })
                .setNegativeButton("إلغاء", null)
                .show();
    }

    public static void showAppSettingsDialog(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("الذهاب إلى الإعدادات", (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                    intent.setData(uri);
                    context.startActivity(intent);
                })
                .setNegativeButton("إلغاء", null)
                .show();
    }
}
