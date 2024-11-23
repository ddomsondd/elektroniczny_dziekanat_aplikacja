package com.example.dziekanat;

import static com.example.dziekanat.Helper.validateGrade;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class EmployeeProfile extends AppCompatActivity {

    private EditText editTextIndexStudenta, editTextOcena, editTextPrzedmiot;
    private Button buttonZapisz, buttonWyloguj, buttonExportStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);

        editTextIndexStudenta = findViewById(R.id.editTextIndexStudenta);
        editTextOcena = findViewById(R.id.editTextOcena);
        editTextPrzedmiot = findViewById(R.id.editTextPrzedmiot);
        buttonWyloguj = findViewById(R.id.buttonWyloguj2);
        buttonZapisz = findViewById(R.id.buttonZapiszOcene);
        buttonExportStudents = findViewById(R.id.buttonExportStudents);

        SharedPreferences sharedPreferences = getSharedPreferences("appPrefs", MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("access_token", "");

        buttonZapisz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zapiszOcene(accessToken);
            }
        });

        buttonWyloguj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        buttonExportStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportStudents(accessToken);
            }
        });
    }

    private void zapiszOcene(String accessToken){
        String index = editTextIndexStudenta.getText().toString().trim();
        String ocena = editTextOcena.getText().toString().trim();
        String przedmiot = editTextPrzedmiot.getText().toString().trim();


        if (!validateGrade(index, ocena, przedmiot)) {
            Toast.makeText(this, "Proszę wypełnić prawidłowo wszystkie pola", Toast.LENGTH_SHORT).show();
        } else{
            JSONObject gradeData = new JSONObject();
            try {
                gradeData.put("grade", ocena);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String url = generateUrl(Integer.parseInt(index), przedmiot);
            Log.d("URL", url);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST, url, gradeData,
                    response -> {
                        Toast.makeText(EmployeeProfile.this, "Ocena została zapisana", Toast.LENGTH_SHORT).show();
                        editTextIndexStudenta.setText("");
                        editTextOcena.setText("");
                        editTextPrzedmiot.setText("");

                    },
                    error -> {
                        Log.e("Grade Save Error", "Błąd odpowiedzi: " + error.getMessage());
                        Toast.makeText(EmployeeProfile.this, "Nieprawidłowe dane dodania oceny", Toast.LENGTH_SHORT).show();
                    }

            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer " + accessToken);
                    return headers;
                }
            };


            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(jsonObjectRequest);
        }


    }

    private void logout(){
        SharedPreferences sharedPreferences = getSharedPreferences("appPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("access_token");
        editor.remove("refresh_token");
        editor.remove("role");
        editor.apply();
        finish();
    }

    private void exportStudents(String accessToken) {
        String url = "http://192.168.1.25:8080/export/students/download/android";

        try {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.addRequestHeader("Authorization", "Bearer " + accessToken);
            request.setTitle("Eksport studentów");
            request.setDescription("Pobieranie pliku TXT z listą studentów...");
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "students.txt");

            DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            if (downloadManager != null) {
                long downloadId = downloadManager.enqueue(request);
                Toast.makeText(this, "Rozpoczęto eksport studentów", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Nie udało się rozpocząć pobierania", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Wystąpił błąd podczas eksportu studentów", Toast.LENGTH_SHORT).show();
        }
    }

    public static String generateUrl(int studentIndex, String className) {
        try {
            String encodedClassName = URLEncoder.encode(className, StandardCharsets.UTF_8.toString());
            URI uri = new URI("http", null, "192.168.1.25", 8080, "/grades/student/index",
                    "studentIndex=" + studentIndex + "&className=" + encodedClassName, null);

            return uri.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}