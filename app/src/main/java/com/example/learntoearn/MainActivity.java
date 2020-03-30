package com.example.learntoearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.learntoearn.R;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openaccount();
            }
        });

        button1 = (Button) findViewById(R.id.button_signup);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openaccount();
            }
        });
    }

    public void openaccount(){
        Intent intent = new Intent(this, account.class);
        startActivity(intent);
    }

}
