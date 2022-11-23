package com.example.mydoctor.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mydoctor.Adapter.GoodNewsAdapter;
import com.example.mydoctor.Adapter.RumahSakitAdapter;
import com.example.mydoctor.Model.RumahSakit;
import com.example.mydoctor.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    RecyclerView recyclerView;
    RumahSakitAdapter rumahSakitAdapter;
    ArrayList<RumahSakit>rumahSakits;
    DatabaseReference reference;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        recyclerView = root.findViewById(R.id.rvHospital);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rumahSakits = new ArrayList<>();

        readRumahSakit();


        return root;
    }
    private void readRumahSakit(){
        reference = FirebaseDatabase.getInstance().getReference("rumahSakit");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                rumahSakits.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    RumahSakit rumahSakit = dataSnapshot.getValue(RumahSakit.class);

                    rumahSakits.add(rumahSakit);
                }
                rumahSakitAdapter = new RumahSakitAdapter(getContext(),rumahSakits);
                rumahSakitAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(rumahSakitAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}