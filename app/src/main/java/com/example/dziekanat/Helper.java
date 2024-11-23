package com.example.dziekanat;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Helper {

    public static boolean validateLogin(String login, String password){
        if (login.trim().isEmpty() || password.trim().isEmpty())
            return false;
        else return true;
    }

    public static boolean validateGrade(String index, String ocena, String przedmiot){
        if (index.trim().isEmpty() || ocena.trim().isEmpty() || przedmiot.trim().isEmpty()) {
            return false;
        }
        if (containsLettersOrSpecialCharsOrSpaces(index)) {
            return false;
        }
        Optional<Float> optionalFloat = parseToFloat(ocena);
        if (!optionalFloat.isPresent()) {
            return false;
        }
        if (containsNumbers(przedmiot)) {
            return false;
        }
        return true;
    }

    private static Optional<Float> parseToFloat(String ocena) {
        try {
            return Optional.of(Float.parseFloat(ocena.trim()));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private static boolean containsNumbers(String text) {
        return text.matches(".*\\d.*");
    }

    private static boolean containsLettersOrSpecialCharsOrSpaces(String text) {
        return text.matches(".*[a-zA-Z].*") || text.matches(".*\\W.*") || text.matches(".*\\s.*");
    }
}
