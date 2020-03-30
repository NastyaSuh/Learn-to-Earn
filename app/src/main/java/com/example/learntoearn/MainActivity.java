package com.example.learntoearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.accounts.Account;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.learntoearn.R;
import com.example.learntoearn.Values.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button button_login;
    private Button button_signup;
    FirebaseAuth auth; //аутентификация пользователя
    FirebaseDatabase database; //подключаем базу данных
    DatabaseReference user; //работа с бд
    ConstraintLayout root;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        button_login = (Button) findViewById(R.id.button_login);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignin_Window();
            }
        });

        button_signup = (Button) findViewById(R.id.button_signup);
        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegister_Window();
            }
        });

        auth = FirebaseAuth.getInstance(); //подключаем firebase
        database = FirebaseDatabase.getInstance(); //подключаем Firebase Database
        user = database.getReference("Users"); //работа с отважными людьми, которые осмелились пользовать нашу прогу
        root = findViewById(R.id.root_element);
    }



    private void showRegister_Window() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Sign up");
        dialog.setMessage("Enter the information to sign up");

        LayoutInflater inflater = LayoutInflater.from(this);
        View Register_Window = inflater.inflate(R.layout.register_window, null);
        dialog.setView(Register_Window);
        final MaterialEditText email = Register_Window.findViewById(R.id.emailField);
        final MaterialEditText password = Register_Window.findViewById(R.id.passwordField);
        final MaterialEditText name = Register_Window.findViewById(R.id.nameField);
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();

            }
        });

        dialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInt, int which) {
                if(TextUtils.isEmpty(email.getText().toString())){
                    Snackbar.make(root, "Enter your Email Adress", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(name.getText().toString())){
                    Snackbar.make(root, "Enter your Name", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if(password.getText().toString().length() < 5){
                    Snackbar.make(root, "Your Password is too small", Snackbar.LENGTH_LONG).show();
                    return;
                }

                //с помощью предыдущих условных операторов мы проверяли правильность введенных данных
                //если они верны, то регистрируем пользователя;)
                auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()) //регистрируем пользователя
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Users users = new Users();
                                users.setEmail(email.getText().toString());
                                users.setPassword(password.getText().toString());
                                users.setName(name.getText().toString());

                                user.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(users).
                                        addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Snackbar.make(root, "New User is added!", Snackbar.LENGTH_LONG).show();
                                    }
                                });
                            }
                        });
            }
        });
        dialog.show();
    }


    private void showSignin_Window() {AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Log in");
        dialog.setMessage("Enter the information to sign in");

        LayoutInflater inflater = LayoutInflater.from(this);
        View Signin_Window = inflater.inflate(R.layout.sing_in_window, null);
        dialog.setView(Signin_Window);
        final MaterialEditText email = Signin_Window.findViewById(R.id.emailField);
        final MaterialEditText password = Signin_Window.findViewById(R.id.passwordField);
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();

            }
        });

        dialog.setPositiveButton("Log in", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInt, int which) {
                if(TextUtils.isEmpty(email.getText().toString())){
                    Snackbar.make(root, "Enter your Email Adress", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if(password.getText().toString().length() < 5){
                    Snackbar.make(root, "Your Password is too small", Snackbar.LENGTH_LONG).show();
                    return;
                }
                auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                startActivity(new Intent(MainActivity.this, account.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(root, "Autorization Error"+e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });
        dialog.show();
    }

}
