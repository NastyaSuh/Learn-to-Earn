package com.example.learntoearn;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import com.example.learntoearn.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Susha Nastya on 05/05/2020.
 */

public class account extends AppCompatActivity {
    private FirebaseAuth mAuth;
     TextView text_name;
     TextView text_email;
     Button button_log;
     DatabaseReference reff;
     private ImageView mphoto;
     public static final int USER_KEY_GET_PHOTO = 100;

    private View.OnClickListener mOnPhotoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openGallery();

        }
    };

    private void openGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select picture"), USER_KEY_GET_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == USER_KEY_GET_PHOTO && resultCode == RESULT_OK
                && data != null){
            Uri photoUri = data.getData();
            mphoto.setImageURI(photoUri);
        }
        else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        mAuth = FirebaseAuth.getInstance();
        mphoto = (ImageView) findViewById(R.id.photofromgallery);
        mphoto.setOnClickListener(mOnPhotoClickListener);
        text_name = (TextView) findViewById(R.id.username);
        text_email = (TextView) findViewById(R.id.usersemail);
        button_log = (Button) findViewById(R.id.button_log);
        button_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reff = FirebaseDatabase.getInstance().getReference().child("Users").child("XRenajkOrIMbLkeeByeLp5joI583");
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String name = dataSnapshot.child("name").getValue().toString();
                        String email = dataSnapshot.child("email").getValue().toString();
                        text_name.setText(name);
                        text_email.setText(email);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }


}
