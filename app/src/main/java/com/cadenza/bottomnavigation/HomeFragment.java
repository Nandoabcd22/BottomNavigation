package com.cadenza.bottomnavigation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.cadenza.bottomnavigation.Panduan.panduanActivity;
import com.cadenza.bottomnavigation.Tanya.PertanyaanActivity;

public class HomeFragment extends Fragment {
    private ImageView btnPanduan, btnAlquran, btnTanya, btnGalery,btnTestimoni;
    private TextView txtNama;
    private SharedPreferences sharedPreferences;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Konstruktor kosong yang diperlukan
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate layout untuk fragment ini
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        btnPanduan = view.findViewById(R.id.btnPanduan);
        btnAlquran = view.findViewById(R.id.btnAlquran);
        btnTanya = view.findViewById(R.id.btnTanya);
        btnGalery = view.findViewById(R.id.btnGalery);
        btnTestimoni = view.findViewById(R.id.btnTestimoni);
        txtNama = view.findViewById(R.id.txtNama);
        sharedPreferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE); // Inisialisasi sharedPreferences
//        Toast.makeText(requireActivity(), sharedPreferences.getString("NIK", ""), Toast.LENGTH_SHORT).show();


        if (btnPanduan != null) {
            btnPanduan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(), panduanActivity.class));
                }
            });
        }

        if (btnTanya != null) {
            btnTanya.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(), PertanyaanActivity.class));
                }
            });
        }
        if (btnGalery != null) {
            btnGalery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(), GaleryActivity.class));
                }
            });
        }

        if (btnAlquran != null) {
            btnAlquran.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotoUrl("https://kalam.sindonews.com/quran");
                }
            });
        }

        if (btnTestimoni != null) {
            btnTestimoni.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(), TestimoniActivity.class));
                }
            });
        }

        String nama_lengkap = sharedPreferences.getString("nama_lengkap", "");

        txtNama.setText(nama_lengkap);
        return view;

    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));

    }
}
