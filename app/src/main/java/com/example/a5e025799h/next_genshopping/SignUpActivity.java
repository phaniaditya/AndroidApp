package com.example.a5e025799h.next_genshopping;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSignUp;
    TextView btnLogin;
    EditText input_email, input_pass;
    RelativeLayout activity_sign_up;

    private FirebaseAuth auth;
    Snackbar snackbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnSignUp = (Button)findViewById(R.id.signup_btn_register);
        btnLogin = (TextView)findViewById(R.id.signup_btn_login);
        input_email = (EditText)findViewById(R.id.signup_email);
        input_pass = (EditText)findViewById(R.id.signup_password);
        activity_sign_up = (RelativeLayout) findViewById(R.id.activity_sign_up);

        btnSignUp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        //init
        auth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.signup_btn_login) {
            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
            finish();
        } else if (view.getId() == R.id.signup_btn_register) {
            snackbar = Snackbar.make(activity_sign_up, "Error: " + "o", Snackbar.LENGTH_SHORT);

            snackbar.show();
            //startActivity(new Intent(SignUpActivity.this, MainActivity.class));
           signUpUser(input_email.getText().toString(), input_pass.getText().toString());
        }
    }




    private void signUpUser(String email, String password) {

        snackbar.show();
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    snackbar = Snackbar.make(activity_sign_up, "Error: " + task.getException(), Snackbar.LENGTH_SHORT);
                    snackbar.show();
                } else {
                    snackbar = Snackbar.make(activity_sign_up, "Register Success : ", Snackbar.LENGTH_SHORT);
                    snackbar.show();

                }
            }
        });

    }

}

