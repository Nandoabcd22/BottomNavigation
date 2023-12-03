package com.cadenza.bottomnavigation;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cadenza.bottomnavigation.API.Db_Contract;
import com.cadenza.bottomnavigation.Panduan.panduanActivity;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EditAkunActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ImageView btnkembali;
    Button btnsmpan;
    TextView etnmalengkap, etNk, etNohp;
    ImageView etProfile;
    Uri imageUri;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_akun);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        etNohp = findViewById(R.id.etNohp);
        etNk = findViewById(R.id.etNk);
        etnmalengkap = findViewById(R.id.etnmalengkap);
        etProfile = findViewById(R.id.etProfile);
        btnsmpan = findViewById(R.id.btnsmpan);
        etProfile = findViewById(R.id.etProfile);

        // Initialize RequestQueue
        queue = Volley.newRequestQueue(this);

        etProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(EditAkunActivity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {
                                // Handle permission denied
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });
        btnkembali = findViewById(R.id.btnKembali);

        if (btnkembali != null) {
            btnkembali.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), ProfileFragment.class));
                }
            });
        }

        btnsmpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChangesToDatabase();
            }
        });

        // Ambil data dari SharedPreferences
        String nama_lengkap = sharedPreferences.getString("nama_lengkap", "");
        String no_hp = sharedPreferences.getString("no_hp", "");
        String NIK = sharedPreferences.getString("NIK", "");

        // Set teks pada TextView dengan data dari SharedPreferences
        etnmalengkap.setText(nama_lengkap);
        etNohp.setText(no_hp);
        etNk.setText(NIK);
    }

    private void saveChangesToDatabase() {
        // Get updated data
        String nama_lengkap = etnmalengkap.getText().toString();
        String no_hp = etNohp.getText().toString();
        String NIK = etNk.getText().toString();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Db_Contract.urleditakun,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int kode = jsonObject.getInt("kode");
                            String status = jsonObject.getString("status");

                            // Periksa jika data berhasil diedit
                            if (kode == 201) {
                                // Data berhasil diedit, tampilkan notifikasi berhasil
                                Toast.makeText(EditAkunActivity.this, "Data berhasil diubah", Toast.LENGTH_SHORT).show();
                            } else {
                                // Gagal mengedit data, tampilkan notifikasi error
                                Toast.makeText(EditAkunActivity.this, "Gagal mengedit data", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error", "Error occurred during the request");
                // Gagal mengedit data, tampilkan notifikasi error
                Toast.makeText(EditAkunActivity.this, "Gagal mengedit data", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nama_lengkap", nama_lengkap);
                params.put("no_hp", no_hp);
                params.put("NIK", NIK);

                // Tambahkan data gambar ke params
                params.putAll(getByteData());

                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private Map<String, String> getByteData() {
        Map<String, String> params = new HashMap<>();
        if (imageUri != null) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                String imageData = bitmapToBase64(bitmap);
                params.put("profile_image", imageData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return params;
    }

    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                etProfile.setImageBitmap(bitmap);

                // Tambahkan kode untuk mengatur gambar di ImageView dengan ID profileimage di layout yang berbeda
                ImageView profileImageAnotherLayout = findViewById(R.id.profileimage);
                profileImageAnotherLayout.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
