package com.example.mydoctor.Activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.mydoctor.Adapter.ListDokterAdapter;
import com.example.mydoctor.Model.Kategori;
import com.example.mydoctor.Model.Users;
import com.example.mydoctor.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ListDokterActivity extends AppCompatActivity {
    public static final String EXTRA_KATEGORI = "extra_kategori";
    private RecyclerView recyclerView;
    private Kategori kategori;
    private ListDokterAdapter listDokterAdapter;
    private DatabaseReference reference;
    private FirebaseUser firebaseUser;
    private ArrayList<Users>mdokters;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_dokter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        kategori = getIntent().getParcelableExtra(EXTRA_KATEGORI);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        recyclerView = findViewById(R.id.rvListDokter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mdokters = new ArrayList<>();
        if (kategori != null){
            readDokter();
            getSupportActionBar().setTitle(kategori.getNama());


        }else {
            readPasien();
            getSupportActionBar().setTitle("Pasien");


        }


    }
    private void readDokter(){
        reference = FirebaseDatabase.getInstance().getReference("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mdokters.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Users users = dataSnapshot.getValue(Users.class);
                    if (users.getKategori().equals("Dokter")){
                        if (users.getBidang().equals(kategori.getNama())){
                            mdokters.add(users);
                            Log.d("a", "onDataChange: "+mdokters);

                        }
                    }
                }
                listDokterAdapter = new ListDokterAdapter(ListDokterActivity.this,mdokters);
                listDokterAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(listDokterAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void readPasien() {
        reference = FirebaseDatabase.getInstance().getReference("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mdokters.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Users users = dataSnapshot.getValue(Users.class);
                    if (users.getKategori().equals("Pasien")){

                        mdokters.add(users);




                    }
                }
                listDokterAdapter = new ListDokterAdapter(ListDokterActivity.this,mdokters);
                listDokterAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(listDokterAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();

        }
        return super.onOptionsItemSelected(item);

    }
    private void status(String status){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid());

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("status",status);

        reference.updateChildren(hashMap);
    }
    @Override
    protected void onResume() {
        super.onResume();
        status("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        status("offline");
    }
}