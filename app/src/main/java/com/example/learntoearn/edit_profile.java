package com.example.learntoearn;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.example.learntoearn.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.IOException;

/**
 * Created by Susha Nastya on 05/05/2020.
 */

public class edit_profile extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    private TextView textView_emailname;
    private DatabaseReference databaseReference;
    private static final String TAG = account.class.getSimpleName();
    Button btnsave;
    private EditText editTextName;
    private EditText editTextSurname;
    private ImageView profileImageView;
    public static final int PICK_IMAGE = 100;
    private TextView profile_settings;
    private TextView education_settings;
    private FirebaseStorage firebaseStorage;
    Uri imagePath;
    private StorageReference storageReference;
    private ImageView home_page;

    public edit_profile() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null) {
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                profileImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        storageReference = firebaseStorage.getReference();
        firebaseStorage = FirebaseStorage.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        editTextName = findViewById(R.id.EditTextName);
        editTextSurname = findViewById(R.id.EditTextSurname);
        btnsave = findViewById(R.id.savebtn);
        btnsave.setOnClickListener((View.OnClickListener) this);
        textView_emailname = findViewById(R.id.textViewEmailAdress);
        FirebaseUser user = mAuth.getCurrentUser();
        textView_emailname.setText(user.getEmail());
        profile_settings = findViewById(R.id.textView4);
        education_settings = findViewById(R.id.textView5);
        home_page = findViewById(R.id.home_page);

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileintent = new Intent();
                profileintent.setType("image/*");
                profileintent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(profileintent, "Выберите изображение"), PICK_IMAGE);
            }
        });

        profile_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(edit_profile.this, Profile.class);
                startActivity(intent);
            }
        });
        education_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(edit_profile.this, Learning_process_settings.class);
                startActivity(intent);
            }
        });
        home_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(edit_profile.this, account.class);
                startActivity(intent);
            }
        });

    }

    private void userInformation() {
        String name = editTextName.getText().toString().trim();
        String surname = editTextSurname.getText().toString().trim();
        Userinformantion userinformantion = new Userinformantion(name, surname);
        FirebaseUser user = mAuth.getCurrentUser();
        databaseReference.child(user.getUid()).setValue(userinformantion);
        Toast.makeText(getApplicationContext(), "Информация о пользователе обновлена", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onClick(View view) {
        if(view == btnsave){
            if(imagePath == null){
                Drawable drawable = this.getResources().getDrawable(R.drawable.circle);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.circle);
                //openSelectProfilePictureDialog()
                userInformation();
                //get User Data
                finish();
                startActivity(new Intent(edit_profile.this, Profile.class));
            }
            else{
                userInformation();
                sendUserData();
                finish();
                startActivity(new Intent(edit_profile.this, Profile.class));
            }
        }
    }

    private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        // get user id from Firebase>Authentification>Users
        DatabaseReference databaseReference = firebaseDatabase.getReference(mAuth.getUid());
        //User id/Images/ProfilePic.jpg
        StorageReference imageReference = storageReference.child(mAuth.getUid()).child("Images").child("Profile Pic");
        UploadTask uploadTask = imageReference.putFile(imagePath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(edit_profile.this, "Ошибка в загрузке фотографии", Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(edit_profile.this, "Фотография успешно загружена", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void openSelectProfilePictureDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        TextView title = new TextView(this);
        title.setText("Фотография");
        title.setPadding(10,10,10,10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(20);
        alertDialog.setCustomTitle(title);
        TextView msg = new TextView(this);
        msg.setText("Выберите фотографию для аватарки;)");
        msg.setGravity(Gravity.CENTER_HORIZONTAL);
        msg.setTextColor(Color.BLACK);
        alertDialog.setView(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {}
        });
        new Dialog(getApplicationContext());
        alertDialog.show();
        final Button okBT = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        LinearLayout.LayoutParams neutralBtnLP = (LinearLayout.LayoutParams) okBT.getLayoutParams();
        neutralBtnLP.gravity = Gravity.FILL_HORIZONTAL;
        okBT.setPadding(10,10,10,10);
        okBT.setTextColor(Color.BLUE);
        okBT.setLayoutParams(neutralBtnLP);
    }
}

