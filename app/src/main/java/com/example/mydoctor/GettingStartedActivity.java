package com.example.mydoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.balysv.materialripple.MaterialRippleLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class GettingStartedActivity extends AppCompatActivity {
    private MaterialRippleLayout btnGetStrtd,btnSignUpUsr,btnSignDkr;

    private FirebaseUser firebaseUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_started);
        btnGetStrtd = findViewById(R.id.btnGetStarted);
        btnSignUpUsr = findViewById(R.id.signUPusr);
        btnSignDkr = findViewById(R.id.btnSignUpdkr);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        btnGetStrtd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GettingStartedActivity.this,LoginActivty.class));

            }
        });
        btnSignDkr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GettingStartedActivity.this,SignUpDokterActivity.class));


            }
        });
        btnSignUpUsr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GettingStartedActivity.this,SignUpActivity.class));


            }
        });

        if (firebaseUser != null){
            Intent intent = new Intent(GettingStartedActivity.this,HomeActivty.class);
            startActivity(intent);
            finish();
        }



    }


}