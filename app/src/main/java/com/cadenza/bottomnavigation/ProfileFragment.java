package com.cadenza.bottomnavigation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    // Parameter untuk fragment initialization
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    // Komponen UI
    private TextView txtNik, txtNama, txtLogout, etEdit, btnHubungi;
    private ImageView btnLogout, profileimage;
    private SharedPreferences sharedPreferences;

    public ProfileFragment() {
        // Konstruktor kosong yang diperlukan oleh Fragment
    }

    /**
     * Metode untuk membuat instance baru dari ProfileFragment.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return Instance ProfileFragment baru.
     */
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate tata letak untuk fragment ini
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        // Inisialisasi elemen UI berdasarkan ID
        txtNama = rootView.findViewById(R.id.txtNama);
        txtNik = rootView.findViewById(R.id.txtNik);
        profileimage = rootView.findViewById(R.id.profileimage);
        btnLogout = rootView.findViewById(R.id.btnLogout);
        txtLogout = rootView.findViewById(R.id.txtLogout);
        btnHubungi = rootView.findViewById(R.id.btnHubungi);
        sharedPreferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE); // Inisialisasi SharedPreferences

        // Menambahkan listener untuk tombol hubungi via WhatsApp
        btnHubungi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wpurl = "https://wa.me/+6289686404940?text=Assalamualaikum ADMIN";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(wpurl));
                startActivity(intent);
            }
        });

        // Menambahkan listener untuk tombol logout
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Menghapus data dari SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear(); // Menghapus data yang ada di SharedPreferences
                editor.apply();
                // Menjalankan aktivitas LogJamaahActivity dan menyelesaikan aktivitas saat ini
                startActivity(new Intent(requireActivity(), LogJamaahActivity.class));
                requireActivity().finish();
            }
        });

        // Menambahkan listener untuk teks logout
        txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Menghapus data dari SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear(); // Menghapus data yang ada di SharedPreferences
                editor.apply();
                // Menjalankan aktivitas LogJamaahActivity dan menyelesaikan aktivitas saat ini
                startActivity(new Intent(requireActivity(), LogJamaahActivity.class));
                requireActivity().finish();
            }
        });

        // Menambahkan listener untuk tombol edit (jika ditemukan)
        if (etEdit != null) {
            etEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Menjalankan aktivitas EditAkunActivity
                    startActivity(new Intent(getActivity(), EditAkunActivity.class));
                }
            });
        }

        // Mengambil data dari SharedPreferences
        String nama_lengkap = sharedPreferences.getString("nama_lengkap", ""); // Ganti "nama" sesuai dengan nama yang digunakan saat menyimpan data
        String NIK = sharedPreferences.getString("NIK", ""); // Ganti "no_telp" sesuai dengan nama yang digunakan saat menyimpan data

        // Mengatur teks pada TextView dengan data dari SharedPreferences
        txtNama.setText(nama_lengkap);
        txtNik.setText(NIK);

        return rootView;
    }
}
