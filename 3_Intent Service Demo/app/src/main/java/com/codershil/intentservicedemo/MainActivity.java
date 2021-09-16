package com.codershil.intentservicedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText editTextInput;
    Button btnStartService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextInput = findViewById(R.id.edit_text_input);
        btnStartService = findViewById(R.id.btn_start_service);

        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIntentService();
            }
        });
    }

    // method for starting intent service
    private void startIntentService(){
        String input = editTextInput.getText().toString();
        Intent serviceIntent = new Intent(this,ExampleIntentService.class);
        serviceIntent.putExtra("inputExtra",input);
        ContextCompat.startForegroundService(this,serviceIntent);
    }

}