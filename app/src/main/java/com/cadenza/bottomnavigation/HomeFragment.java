package com.cadenza.bottomnavigation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.cadenza.bottomnavigation.Panduan.panduanActivity;
import com.cadenza.bottomnavigation.Tanya.PertanyaanActivity;

public class HomeFragment extends Fragment {
    private ImageView btnPanduan, btnAlquran, btnTanya;
    private TextView btnSlkpnya, btnSelengkapnya;

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
        btnSlkpnya = view.findViewById(R.id.btnSlkpnya);
        btnSelengkapnya = view.findViewById(R.id.btnSelengkapnya);

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

        if (btnSlkpnya != null) {
            btnSlkpnya.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Mengasumsikan ShopFragment adalah Fragment, bukan Activity
                    ShopFragment shopFragment = new ShopFragment();

                    // Menggunakan FragmentTransaction untuk menggantikan fragment saat ini
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.homeFragmentContainer, shopFragment);
                    transaction.addToBackStack(null); // Opsional, untuk menambahkan transaksi ke tumpukan kembali
                    transaction.commit();
                }
            });
        }

        if (btnSelengkapnya != null) {
            btnSelengkapnya.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Memulai aktivitas yang terkait dengan ShopFragment
                    startActivity(new Intent(getActivity(), ShopFragment.class));
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

        // Kode yang sudah ada...

        return view;
    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
}
