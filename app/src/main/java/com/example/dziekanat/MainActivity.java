package com.example.dziekanat;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private EditText editTextLogin;
    private EditText editTextHaslo;
    private Button buttonZaloguj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextLogin = findViewById(R.id.editTextLogin);
        editTextHaslo = findViewById(R.id.editTextHaslo);
        buttonZaloguj = findViewById(R.id.buttonZaloguj);

        buttonZaloguj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginStudent();
            }
        });
    }

    public void loginStudent() {
        String login = editTextLogin.getText().toString().trim();
        String password = editTextHaslo.getText().toString().trim();

        if (login.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Proszę wypełnić wszystkie pola", Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject loginData = new JSONObject();
        try {
            loginData.put("username", login);
            loginData.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = "http://192.168.1.25:8080/login";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, loginData,
                response -> {
                    try {
                        String accessToken = response.getString("access_token");
                        String refreshToken = response.getString("refresh_token");
                        String role = response.getString("role");
                        //String studentId = response.getString("student_id");


                        SharedPreferences sharedPreferences = getSharedPreferences("appPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("access_token", accessToken);
                        editor.putString("refresh_token", refreshToken);
                        editor.apply();

                        if(role.equals("STUDENT")){
                            Toast.makeText(MainActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, StudentProfile.class);
                            startActivity(intent);
                        } else{
                            Toast.makeText(MainActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, EmployeeProfile.class);
                            startActivity(intent);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.e("Login Error", "Błąd odpowiedzi: " + error.getMessage());
                    Toast.makeText(MainActivity.this, "Nieprawidłowy login lub hasło", Toast.LENGTH_SHORT).show();
                }
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }

}
