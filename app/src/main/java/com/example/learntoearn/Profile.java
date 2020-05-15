package com.example.learntoearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Profile extends AppCompatActivity {
    private ImageView homepage;

    private ImageView settingstool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        homepage = findViewById(R.id.home_page);
        settingstool = findViewById(R.id.setting_tool);
         homepage.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(Profile.this, Profile.class);
                 startActivity(intent);
             }
         });
         settingstool.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(Profile.this, account.class);
                 startActivity(intent);
             }
         });
    }

}
