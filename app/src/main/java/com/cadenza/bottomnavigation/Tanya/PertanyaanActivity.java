package com.cadenza.bottomnavigation.Tanya;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.cadenza.bottomnavigation.MainActivity;
import com.cadenza.bottomnavigation.Panduan.HukumIbadahHajiActivity;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.cadenza.bottomnavigation.databinding.ActivityPertanyaanBinding;

import com.cadenza.bottomnavigation.R;

public class PertanyaanActivity extends AppCompatActivity {
    private Button btnCrDaftarUmroh, btnTotalBiaya, btnKualitas, btnPersiapan, btnMenghubungiAgen, btnProsedur,
            btnLangkah, btnPemandu, btnLayanan, btnJadwal, btnFasilitas, btnKlik;
    private ImageView btnKmbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pertanyaan);

        btnCrDaftarUmroh = findViewById(R.id.btnCrDaftarUmroh);
        btnTotalBiaya = findViewById(R.id.btnTotalBiaya);
        btnKualitas = findViewById(R.id.btnKualitas);
        btnPersiapan = findViewById(R.id.btnPersiapan);
        btnMenghubungiAgen = findViewById(R.id.btnMenghubungiAgen);
        btnProsedur = findViewById(R.id.btnProsedur);
        btnLangkah = findViewById(R.id.btnLangkah);
        btnPemandu = findViewById(R.id.btnPemandu);
        btnLayanan = findViewById(R.id.btnLayanan);
        btnJadwal = findViewById(R.id.btnJadwal);
        btnFasilitas = findViewById(R.id.btnFasilitas);
        btnKmbl = findViewById(R.id.btnKmbl);
        btnKlik = findViewById(R.id.btnKlik);

        btnCrDaftarUmroh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Tanya1Activity.class));
            }
        });
        btnTotalBiaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Tanya2Activity.class));
            }
        });
        btnKualitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Tanya3Activity.class));
            }
        });
        btnPersiapan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Tanya4Activity.class));
            }
        });
        btnMenghubungiAgen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Tanya5Activity.class));
            }
        });
        btnProsedur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Tanya6Activity.class));
            }
        });
        btnLangkah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Tanya7Activity.class));
            }
        });
        btnPemandu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Tanya8Activity.class));
            }
        });
        btnLayanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Tanya9Activity.class));
            }
        });
        btnJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Tanya10Activity.class));
            }
        });
        btnFasilitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Tanya11Activity.class));
            }
        });
        btnKmbl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        btnKlik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wpurl="https://wa.me/+6289686404940?text=Assalamualaikum ADMIN" + "%0A" +
                        "Saya Ingin Bertanya";
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(wpurl));
                startActivity(intent);
            }
        });

    }
}