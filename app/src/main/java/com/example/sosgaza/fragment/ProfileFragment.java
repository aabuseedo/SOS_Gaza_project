package com.example.sosgaza.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sosgaza.R;
import com.example.sosgaza.activity.account.LoginActivity;
import com.example.sosgaza.activity.ChangePasswordActivity;
import com.example.sosgaza.activity.ChangePhoneNumActivity;
import com.example.sosgaza.activity.EditProfileActivity;
import com.example.sosgaza.activity.ManageEmergencyNumberActivity;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link ProfileFragment#newInstance} factory method to
// * create an instance of this fragment.
// */

public class ProfileFragment extends Fragment {

    ImageView image_profile;
    TextView tv_phone_num_profile, tv_mobile_num_profile, tv_health_profile, tv_blood_profile;
    Button btn_edit_profile, btn_emergency_number_profile, btn_edit_phone_profile, btn_change_password_profile, btn_logout_profile;

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public ProfileFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment ProfileFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static ProfileFragment newInstance(String param1, String param2) {
//        ProfileFragment fragment = new ProfileFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // ربط العناصر من ملف XML
        image_profile = view.findViewById(R.id.image_profile);
        tv_phone_num_profile = view.findViewById(R.id.tv_phone_num_profile);
        tv_mobile_num_profile = view.findViewById(R.id.tv_mobile_num_profile);
        tv_health_profile = view.findViewById(R.id.tv_health_profile);
        tv_blood_profile = view.findViewById(R.id.tv_blood_profile);
        btn_edit_profile = view.findViewById(R.id.btn_edit_profile);
        btn_emergency_number_profile = view.findViewById(R.id.btn_emergency_number_profile);
        btn_edit_phone_profile = view.findViewById(R.id.btn_edit_phone_profile);
        btn_change_password_profile = view.findViewById(R.id.btn_change_password_profile);
        btn_logout_profile = view.findViewById(R.id.btn_logout_profile);

        Context context = getContext();

        //__________________________________________________________________________________________

        btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditProfileActivity.class);
                startActivity(intent);
            }
        });

        btn_emergency_number_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ManageEmergencyNumberActivity.class);
                startActivity(intent);
            }
        });

        btn_edit_phone_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChangePhoneNumActivity.class);
                startActivity(intent);
            }
        });

        btn_change_password_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        btn_logout_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


    }

}