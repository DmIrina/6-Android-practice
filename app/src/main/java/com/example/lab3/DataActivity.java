package com.example.lab3;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DataActivity extends AppCompatActivity {

    private TextView dataTextView;
    private Button closeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        dataTextView = findViewById(R.id.dataTextView);
        closeButton = findViewById(R.id.closeButton);

        String data = readDataFromFile();

        if (TextUtils.isEmpty(data)) {
            dataTextView.setText("Сховище порожнє");
        } else {
            dataTextView.setText(data);
        }

        closeButton.setOnClickListener(v -> {
            finish();
        });
    }

    private String readDataFromFile() {
        try {
            File file = new File(getFilesDir(), "result.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder data = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line).append("\n");
            }
            reader.close();
            return data.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
