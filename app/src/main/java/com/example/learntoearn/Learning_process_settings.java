package com.example.learntoearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Learning_process_settings extends AppCompatActivity {
    private ImageView homepage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_process_settings);
        homepage = findViewById(R.id.home_page);
        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Learning_process_settings.this, account.class);
                startActivity(intent);
            }
        });
    }
}

