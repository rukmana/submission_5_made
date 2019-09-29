package com.example.moviecatalogue;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    public String getApiKey(){
        String apiKey = getResources().getString(R.string.api_key);
        return apiKey;
    }

    public static int manipulateColor(int color, float factor) {
        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);
        return Color.argb(a,
                Math.min(r, 255),
                Math.min(g, 255),
                Math.min(b, 255));
    }

}
