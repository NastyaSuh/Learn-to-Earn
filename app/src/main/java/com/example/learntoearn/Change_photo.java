package com.example.learntoearn;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class Change_photo extends AppCompatActivity {
    private ImageView tool;
    private ImageView photofromgallery;
    private Uri filePath;
    private final int REQUEST_CODE_PERMISSION_RECEIVE_CAMERA = 71;
    private FirebaseStorage storage;
    StorageReference mStorageRef;
    private Button choose;
    private Button upload;
    private File mTempPhoto;
    private String mImageUri = "";
    private String mRereference = "";
    private static final int REQUEST_CODE_TAKE_PHOTO = 103;
    private String imagePath;
    private ImageView homepage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_photo);
        tool = findViewById(R.id.settool);
        homepage = findViewById(R.id.home_page);
        File localFile = null;
        photofromgallery = findViewById(R.id.photofromgallery);
        storage = FirebaseStorage.getInstance();
        choose = findViewById(R.id.choose);
        upload = findViewById(R.id.upload);
        mRereference = getIntent().getStringExtra("Reference");
        mStorageRef = FirebaseStorage.getInstance().getReference();
        tool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_change_photo_window();
            }
        });

        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Change_photo.this, account.class);
                startActivity(intent);
            }
        });
    }

    private void show_change_photo_window(){
        AlertDialog.Builder dia = new AlertDialog.Builder(this);
        dia.setTitle("Изменить фотографию");
        LayoutInflater inflater = LayoutInflater.from(this);
        View Changephoto = inflater.inflate(R.layout.change_photo, null);
        dia.setView(Changephoto);
        dia.show();
    }
}
