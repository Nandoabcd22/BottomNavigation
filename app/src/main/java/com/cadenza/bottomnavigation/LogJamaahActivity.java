package com.cadenza.bottomnavigation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cadenza.bottomnavigation.API.Db_Contract;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LogJamaahActivity extends AppCompatActivity {

    private EditText etNik;
    private Button btnLogin;
    private TextView btnRegister, btnLupaNik;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_jamaah);

        // Inisialisasi elemen UI
        etNik = findViewById(R.id.etNik);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        btnLupaNik = findViewById(R.id.btnLupaNik);

        // Menambahkan onClickListener untuk tombol register
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegJamaahActivity.class));
            }
        });

        // Menambahkan onClickListener untuk tombol lupa NIK
        btnLupaNik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LupaNIKActivity.class));
            }
        });

        // Menambahkan onClickListener untuk tombol login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        // Inisialisasi SharedPreferences
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

        // Mengecek status login
        checkLoginStatus();
    }

    // Metode untuk melakukan proses login pengguna
    private void loginUser() {
        // Mendapatkan NIK dari EditText
        String NIK = etNik.getText().toString();

        // Memastikan NIK tidak kosong
        if (!NIK.isEmpty()) {

            // Inisialisasi antrean permintaan Volley
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            // Membuat permintaan StringRequest menggunakan metode POST
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Db_Contract.urlLogjamaah + "?NIK=" + NIK,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                // Memproses respons JSON dari server
                                JSONObject jsonResponse = new JSONObject(response);

                                // Mendapatkan kode dan status dari respons
                                int code = jsonResponse.getInt("code");
                                String status = jsonResponse.getString("status");

                                // Jika kode adalah 200 (berhasil)
                                if (code == 200) {
                                    // Mendapatkan data pengguna dari respons
                                    JSONObject userObject = jsonResponse.getJSONObject("data_jamaah");
                                    String NIK = userObject.getString("NIK");
                                    String nama_lengkap = userObject.getString("nama_lengkap");
                                    String alamat = userObject.getString("alamat");
                                    String no_hp = userObject.getString("no_hp");
                                    String tgl_lahir = userObject.getString("tgl_lahir");
                                    String jenis_kelamin = userObject.getString("jenis_kelamin");
                                    String id_agen = userObject.getString("id_agen");
                                    String nama_bapak = userObject.getString("nama_bapak");

                                    // Menyimpan data ke SharedPreferences
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("NIK", NIK);
                                    editor.putString("nama_lengkap", nama_lengkap);
                                    editor.putString("alamat", alamat);
                                    editor.putString("no_hp", no_hp);
                                    editor.putString("tgl_lahir", tgl_lahir);
                                    editor.putString("jenis_kelamin", jenis_kelamin);
                                    editor.putString("id_agen", id_agen);
                                    editor.putString("nama_bapak", nama_bapak);
                                    editor.putBoolean("isLoggedIn", true);
                                    editor.apply();

                                    // Mengatur ulang input NIK
                                    etNik.setText("");

                                    // Berpindah ke halaman utama setelah login berhasil
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    Toast.makeText(getApplicationContext(), "Login Berhasil", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Menampilkan pesan status jika login gagal
                                    Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Menampilkan pesan jika terjadi kesalahan dalam permintaan
                    Toast.makeText(getApplicationContext(), "Login Gagal: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    // Mengatur parameter POST dengan NIK
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("NIK", NIK);
                    return params;
                }
            };

            // Menambahkan permintaan ke antrean
            requestQueue.add(stringRequest);
        } else {
            // Menampilkan pesan jika NIK kosong
            Toast.makeText(getApplicationContext(), "NIK tidak boleh kosong", Toast.LENGTH_SHORT).show();
        }
    }

    // Metode untuk memeriksa status login
    private void checkLoginStatus() {
        // Mendapatkan status login dari SharedPreferences
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        // Jika pengguna sudah login sebelumnya, arahkan ke halaman utama
        if (isLoggedIn) {
            Intent intent = new Intent(LogJamaahActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
