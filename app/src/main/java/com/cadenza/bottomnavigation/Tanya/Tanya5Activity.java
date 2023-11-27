package com.cadenza.bottomnavigation.Tanya;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.cadenza.bottomnavigation.Panduan.panduanActivity;
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

import com.cadenza.bottomnavigation.databinding.ActivityTanya5Binding;

import com.cadenza.bottomnavigation.R;

public class Tanya5Activity extends AppCompatActivity {

    private ImageView btnkembali;
    private Button btnChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tanya5);

        btnkembali = findViewById(R.id.btnKembali);
        btnChat = findViewById(R.id.btnChat);

        btnkembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PertanyaanActivity.class));
            }
        });
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wpurl="https://wa.me/+6285732214214?text=Assalamualaikum";
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(wpurl));
                startActivity(intent);
            }
        });

    }
}