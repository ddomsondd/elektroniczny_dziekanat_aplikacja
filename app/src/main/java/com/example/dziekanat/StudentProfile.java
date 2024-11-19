package com.example.dziekanat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.dziekanat.dto.GradeDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StudentProfile extends AppCompatActivity {
    private TextView textViewImieNazwisko, textViewGrupa,
            textViewRok, textViewKierunek, textViewWydzial, textViewIndex, textViewPowitanie;

    Button buttonWyloguj;
    private RecyclerView recyclerViewGrades;
    private GradesAdapter gradesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        recyclerViewGrades = findViewById(R.id.recyclerViewGrades);
        recyclerViewGrades.setLayoutManager(new LinearLayoutManager(this));


        textViewImieNazwisko = findViewById(R.id.textViewImieNazwisko);
        textViewGrupa = findViewById(R.id.textViewGrupa);
        textViewRok = findViewById(R.id.textViewRok);
        textViewKierunek = findViewById(R.id.textViewKierunek);
        textViewWydzial = findViewById(R.id.textViewWydzial);
        textViewIndex = findViewById(R.id.textViewIndex);
        textViewPowitanie = findViewById(R.id.textViewPowitanieZalogowany);
        buttonWyloguj = findViewById(R.id.buttonWyloguj);

        SharedPreferences sharedPreferences = getSharedPreferences("appPrefs", MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("access_token", "");

        Log.d("JEST TEN TOKEN?", accessToken);
        if (!accessToken.isEmpty()) {
            fetchStudentProfileWithGrades(accessToken);
        } else {
            Toast.makeText(this, "Brak tokena dostępu", Toast.LENGTH_SHORT).show();
        }

        buttonWyloguj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void fetchStudentProfileWithGrades(String accessToken) {

        String url = "http://192.168.1.25:8080/student/profile/grades";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    Log.d("RESPONSE", response.toString());
                    try {
                        Log.d("JSON", String.valueOf(response.getJSONObject("student").getString("firstName")));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        String firstName = response.getJSONObject("student").getString("firstName");
                        String lastName = response.getJSONObject("student").getString("lastName");
                        String studentIndex = response.getJSONObject("student").getString("studentIndex");
                        String yearOfStudy = response.getJSONObject("student").getString("yearOfStudy");
                        String specialization = response.getJSONObject("student").getString("specialization");
                        String faculty = response.getJSONObject("student").getString("faculty");
                        //String group = response.getJSONObject("student").getString("group");

                        textViewPowitanie.setText("Witaj, "+ firstName + "!");
                        textViewImieNazwisko.setText(firstName + " " + lastName);
                        textViewIndex.setText(studentIndex);
                        textViewRok.setText(yearOfStudy);
                        textViewKierunek.setText(specialization);
                        textViewWydzial.setText(faculty);
                        //textViewGrupa.setText(group);

                        try {
                            List<GradeDTO> grades = parseGrades(response.getJSONArray("grades"));
                            gradesAdapter = new GradesAdapter(grades);
                            recyclerViewGrades.setAdapter(gradesAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(StudentProfile.this, "Błąd przetwarzania danych", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.e("Profile Error", "Błąd odpowiedzi: " + error.getMessage());
                    if (error.networkResponse != null && error.networkResponse.statusCode == 401) {
                        Toast.makeText(StudentProfile.this, "Nieautoryzowany dostęp. Proszę zalogować się ponownie.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(StudentProfile.this, "Błąd pobierania danych", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);

    }

    private List<GradeDTO> parseGrades(JSONArray gradesArray) throws JSONException {
        List<GradeDTO> grades = new ArrayList<>();
        for (int i = 0; i < gradesArray.length(); i++) {
            JSONObject gradeObject = gradesArray.getJSONObject(i);
            GradeDTO grade = new GradeDTO();
            grade.setClassName(gradeObject.getString("className"));
            grade.setGrade(gradeObject.getDouble("grade"));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                grade.setDate(LocalDate.parse(gradeObject.getString("date")));
            }
            grades.add(grade);
        }
        return grades;
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


}