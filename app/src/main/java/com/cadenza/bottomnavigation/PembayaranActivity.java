package com.cadenza.bottomnavigation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cadenza.bottomnavigation.API.Db_Contract;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PembayaranActivity extends AppCompatActivity {

    Button btnSelectImage,btnTransaksi;
    ImageView imageView;

    ImageView btnKembali;
    String encodedImage;
    Bitmap bitmap;

    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);

        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnTransaksi = findViewById(R.id.btnTransaksi);
        btnKembali = findViewById(R.id.btnKembali);
        imageView = findViewById(R.id.imView);

        // Mendapatkan NIK dari SharedPreferences
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String NIK = sharedPreferences.getString("NIK", ""); // Sesuaikan dengan key yang Anda gunakan

        // Mendapatkan data yang diterima melalui Intent
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String id_paket = intent.getStringExtra("id_paket");
        String nama_paket = intent.getStringExtra("nama_paket");
        String keberangkatan = intent.getStringExtra("keberangkatan");
        String pilihan_paket = intent.getStringExtra("pilihan_paket");
        String harga = intent.getStringExtra("harga");
        String lama_waktu = intent.getStringExtra("lama_waktu");
        String ukuran_baju = intent.getStringExtra("ukuran_baju");

//        Toast.makeText(PembayaranActivity.this, NIK, Toast.LENGTH_SHORT).show();
//        Toast.makeText(PembayaranActivity.this, sharedPreferences.getString("NIK", ""), Toast.LENGTH_SHORT).show();



        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PembayaranActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Mengakhiri activity dan kembali ke mainactivity
            }
        });

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withActivity(PembayaranActivity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {

                                Intent intent  = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Select Image"),1);

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();


            }
        });
        // Mendapatkan tanggal saat ini
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = sdf.format(new Date());


        btnTransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmap == null) {
                    Toast.makeText(PembayaranActivity.this, "Pilih gambar terlebih dahulu", Toast.LENGTH_SHORT).show();
                } else {
                    StringRequest request = new StringRequest(Request.Method.POST, Db_Contract.urltransaksi,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.contains("Anda sudah mengupload gambar sebelumnya, admin sedang memprosesnya")) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(PembayaranActivity.this);
                                        builder.setTitle("!");
                                        builder.setMessage(response);
                                        builder.setPositiveButton("OK", null);
                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                    } else if (response.contains("Gambar berhasil diupload")) {
                                        Toast.makeText(PembayaranActivity.this, response, Toast.LENGTH_SHORT).show();
                                        // Menutup aktivitas saat pengunggahan berhasil
                                        Intent intent = new Intent(PembayaranActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(PembayaranActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("Volley Error", "Error: " + error.getMessage(), error);
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("tgl_pemesanan", currentDate);
                            params.put("NIK", NIK); // Menggunakan NIK dari SharedPreferences
                            params.put("ukuran_baju", ukuran_baju);
                            params.put("id_paket", id_paket);
                            params.put("bukti_pembayaran", encodedImage);
                            return params;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(PembayaranActivity.this);
                    requestQueue.add(request);
                }
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri filePath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                if (inputStream != null) {
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    imageView.setImageBitmap(bitmap);
                    imageStore(bitmap);
                } else {
                    Toast.makeText(this, "Gagal membaca file gambar", Toast.LENGTH_SHORT).show();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void imageStore(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);


    }
}