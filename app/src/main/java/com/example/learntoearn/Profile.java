package com.example.learntoearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Profile extends AppCompatActivity {
    private TextView change_photo;
    private ImageView homepage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
         change_photo = findViewById(R.id.textView6);
         homepage = findViewById(R.id.home_page);

         change_photo.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(Profile.this, Change_photo.class);
                 startActivity(intent);
             }
         });

         homepage.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(Profile.this, account.class);
                 startActivity(intent);
             }
         });
    }

}
