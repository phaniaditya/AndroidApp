package com.example.a5e025799h.next_genshopping;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnLogin;
    EditText input_email, input_password ;
    TextView btnSignup;

    RelativeLayout activity_main;

    private FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //view
        btnLogin = (Button) findViewById(R.id.login_btn_login);
        input_email = (EditText) findViewById(R.id.login_email);
        input_password = (EditText) findViewById(R.id.login_password);
        btnSignup = (TextView) findViewById(R.id.login_btn_sign_up);
        activity_main = (RelativeLayout) findViewById(R.id.activity_main);

        btnSignup.setOnClickListener(this);
        btnLogin.setOnClickListener(this);


        auth = FirebaseAuth.getInstance();

        //check already session, if ok-> search

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(MainActivity.this, SearchActivity.class));
        }
    }
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.login_btn_sign_up){
            startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            finish();
        }
        else if(view.getId() == R.id.login_btn_login){
            loginUser(input_email.getText().toString(), input_password.getText().toString());

        }

    }
    private void loginUser(String email, final String password) {

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"please enter email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"please enter password",Toast.LENGTH_SHORT).show();
            return;
        }
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    if(password.length()<6){
                        Snackbar snackbar = Snackbar.make(activity_main, "password length must be over 6",Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                }

                else{
                    SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("user", input_email.getText().toString());
                    editor.commit();

                     Intent i = new Intent(MainActivity.this, SearchActivity.class);
                     startActivity(i);
                    //startActivity(new Intent(MainActivity.this, SearchActivity.class));
                }
            }
        });
    }


}
