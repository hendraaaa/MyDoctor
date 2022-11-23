package com.example.mydoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpDokterActivity extends AppCompatActivity {
    private TextInputEditText edtKategori;
    private TextInputEditText etUsername,etEmail,etPassword;
    private Button button;
    private FirebaseAuth auth;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_dokter);
        button = findViewById(R.id.bt_submitDokter);
        edtKategori = findViewById(R.id.et_kategori);
        etUsername = findViewById(R.id.edtUsernameDok);
        etEmail = findViewById(R.id.edtEmailDok);
        etPassword = findViewById(R.id.edtPasswordDok);

        auth = FirebaseAuth.getInstance();

        edtKategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showKategoriDialog(view);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                register(etUsername.getText().toString(),etEmail.getText().toString(),etPassword.getText().toString(),edtKategori.getText().toString());
            }
        });
    }
    private void showKategoriDialog(final View v) {
        final String[] array = new String[]{
                "Dokter Anak", "Dokter Obat", "Dokter Umum", "Psikiater"
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("State");
        builder.setSingleChoiceItems(array, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ((EditText) v).setText(array[i]);
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
    private void register(String username,String email,String password,String kategori){
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userId = firebaseUser.getUid();
                            reference = FirebaseDatabase.getInstance().getReference("users").child(userId);
                            HashMap<String,String> hashMap = new HashMap<>();
                            hashMap.put("id",userId);
                            hashMap.put("email",email);
                            hashMap.put("username",username);
                            hashMap.put("password",password);
                            hashMap.put("kategori","Dokter");
                            hashMap.put("imageURL","default");
                            hashMap.put("status","offline");
                            hashMap.put("bidang",kategori);

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Intent intent = new Intent(SignUpDokterActivity.this,LoginActivty.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(SignUpDokterActivity.this,"You can not register with this Email and Password",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}