package com.example.sosgaza.fragment;

import android.Manifest;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sosgaza.R;
import com.example.sosgaza.core.Constants;
import com.example.sosgaza.core.PermissionManager;
import com.example.sosgaza.databinding.FragmentMapBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private FragmentMapBinding binding;
    private GoogleMap mMap;
    private ArrayAdapter<String> adapter;
    private List<AutocompletePrediction> predictionList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMapBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!PermissionManager.isPermissionGranted(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)) {
            PermissionManager.requestPermission(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PermissionManager.LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            if (!PermissionManager.isLocationEnabled(requireContext())) {
                PermissionManager.showLocationSettingsDialog(getContext());
            } else {
                initMap();
            }
        }

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        LatLng gaza = new LatLng(31.502225060293974, 34.466887405991024);
        mMap.addMarker(new MarkerOptions().position(gaza).title("Gaza"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gaza, 14));
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map_view);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        binding.btnZoomInMap.setOnClickListener(v -> mMap.animateCamera(CameraUpdateFactory.zoomIn()));
        binding.btnZoomOutMap.setOnClickListener(v -> mMap.animateCamera(CameraUpdateFactory.zoomOut()));

        binding.btnArrowDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.fragmentMap.getCurrentState() == R.id.start) {
                    binding.fragmentMap.transitionToState(R.id.end);
                } else {
                    binding.fragmentMap.transitionToState(R.id.start);
                }
            }
        });

        PermissionManager.getCurrentLocation(getContext(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(myLocation).title("My Location"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 14));
                    }
                },
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

    private void initMapSearch() {
        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), Constants.GOOGLE_API_KEY);
        }
        PlacesClient placesClient = Places.createClient(requireContext());

        binding.etSearchMap.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                            .setQuery(s.toString())
                            .build();

                    placesClient.findAutocompletePredictions(request)
                            .addOnSuccessListener((response) -> {
                                List<String> suggestions = new ArrayList<>();
                                predictionList = response.getAutocompletePredictions();

                                for (AutocompletePrediction prediction : predictionList) {
                                    suggestions.add(prediction.getFullText(null).toString());
                                }

                                adapter = new ArrayAdapter<>(requireContext(),
                                        android.R.layout.simple_dropdown_item_1line, suggestions);
                                binding.etSearchMap.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            })
                            .addOnFailureListener(e -> {
                                Log.e("PlacesError", "Autocomplete error", e);
                            });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(permissions, grantResults, requireActivity(), new PermissionManager.PermissionCallback() {
            @Override
            public void onGranted() {
                if (!PermissionManager.isLocationEnabled(requireContext())) {
                    PermissionManager.showLocationSettingsDialog(getContext());
                } else {
                    initMap();
                }
            }

            @Override
            public void onDenied(List<String> deniedPermissions) {
                Toast.makeText(getContext(), "يجب السماح للتطبيق بالوصول إلى الموقع لاستخدام هذه الميزة", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeniedPermanently(List<String> permanentlyDeniedPermissions) {
                PermissionManager.showAppSettingsDialog(getContext(), "تم رفض صلاحيات الوصول للموقع", "لقد قمت برفض صلاحيات الوصول الى الموقع بشكل دائم. من فضلك قم بتمكينه من إعدادات التطبيق.");
            }
        });
    }

}