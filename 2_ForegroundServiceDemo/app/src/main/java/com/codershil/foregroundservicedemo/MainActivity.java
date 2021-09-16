package com.codershil.foregroundservicedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText editTextInput;
    private Button btnStartService, btnStopService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextInput = findViewById(R.id.edt_text_input);
        btnStartService = findViewById(R.id.startService);
        btnStopService = findViewById(R.id.stopService);

        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMyService();
            }
        });

        btnStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMyService();
            }
        });
    }

    // method to start foreground service
    private void startMyService() {
        String input = editTextInput.getText().toString();

        Intent exampleServiceIntent = new Intent(MainActivity.this,ExampleService.class);
        exampleServiceIntent.putExtra("inputExtra",input);
        ContextCompat.startForegroundService(this,exampleServiceIntent);
    }

    // method to stop foreground service
    private void stopMyService() {
        Intent exampleServiceIntent = new Intent(MainActivity.this,ExampleService.class);
        stopService(exampleServiceIntent);
    }


}