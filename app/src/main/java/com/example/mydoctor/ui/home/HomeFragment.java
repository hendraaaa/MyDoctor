package com.example.mydoctor.ui.home;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mydoctor.Activity.ListDokterActivity;
import com.example.mydoctor.Activity.ProfilActivity;
import com.example.mydoctor.Adapter.GoodNewsAdapter;
import com.example.mydoctor.Adapter.KategoriAdapter;
import com.example.mydoctor.Adapter.TopDokterAdapter;
import com.example.mydoctor.Model.Berita;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView,recyclerViewTop,recyclerViewNews;
    private KategoriAdapter kategoriAdapter;
    private TopDokterAdapter topDokterAdapter;
    private GoodNewsAdapter goodNewsAdapter;
    private ArrayList<Kategori>kategoris;
    private ArrayList<Users> mDokters;
    private ArrayList<Berita>mBeritas;
    private TypedArray typedArray;
    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    private ProgressBar progressBar;
    private LinearLayout linearLayout;
    private TextView tvUsername;
    private CircleImageView circleImageView;
    private RelativeLayout rlPasien,rlDokter;
    Users users;
    private CardView cardView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        progressBar = root.findViewById(R.id.loadingHome);
        linearLayout = root.findViewById(R.id.showHome);
        tvUsername = root.findViewById(R.id.tvUsername);
        circleImageView = root.findViewById(R.id.profilImage);
        rlPasien = root.findViewById(R.id.rlPasien);
        rlDokter = root.findViewById(R.id.rlDokter);
        cardView = root.findViewById(R.id.Pasien);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ListDokterActivity.class));
            }
        });

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),ProfilActivity.class);
               // i.putExtra(ProfilActivity.EXTRA_USER,users);
                startActivity(i);
            }
        });


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (getActivity() == null){
                    return;
                }
                    users = snapshot.getValue(Users.class);
                    progressBar.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                    tvUsername.setText(users.getUsername());
                    if (!users.getImageURL().equals("default")){

                            Glide
                                    .with(getContext())
                                    .load(users.getImageURL())
                                    .into(circleImageView);




                    }
                    if (users.getKategori().equals("Dokter")){
                        rlDokter.setVisibility(View.VISIBLE);
                        rlPasien.setVisibility(View.GONE);
                    }else {
                        rlDokter.setVisibility(View.GONE);
                        rlPasien.setVisibility(View.VISIBLE);
                    }

                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView = root.findViewById(R.id.rvKategori);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        kategoris = new ArrayList<>();

        recyclerViewTop = root.findViewById(R.id.rvTopDokter);
        recyclerViewTop.setHasFixedSize(true);
        LinearLayoutManager lm = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerViewTop.setLayoutManager(lm);

        mDokters = new ArrayList<>();


        recyclerViewNews = root.findViewById(R.id.rvGoodNews);
        recyclerViewNews.setHasFixedSize(true);
        LinearLayoutManager lmm = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerViewNews.setLayoutManager(lmm);
        mBeritas = new ArrayList<>();



        ReadKategori();
        ReadTopDokter();
        ReadGoodNews();



        return root;
    }



    private void ReadKategori(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("kategori");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                kategoris.clear();

                Log.d("GG", "onDataChange: "+dataSnapshot);

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Kategori kategori = snapshot.getValue(Kategori.class);

                    kategoris.add(kategori);


                }
                kategoriAdapter = new KategoriAdapter(getContext(),kategoris);
                kategoriAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(kategoriAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void ReadTopDokter(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mDokters.clear();

                Log.d("GG", "onDataChange: "+dataSnapshot);

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Users dokters = snapshot.getValue(Users.class);

                    if (dokters.getKategori().equals("Dokter")){
                        mDokters.add(dokters);
                    }


                }
                topDokterAdapter = new TopDokterAdapter(getContext(),mDokters);
                topDokterAdapter.notifyDataSetChanged();
                recyclerViewTop.setAdapter(topDokterAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void ReadGoodNews(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("good_news");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mBeritas.clear();

                Log.d("GG", "onDataChange: "+dataSnapshot);

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Berita berita = snapshot.getValue(Berita.class);

                    mBeritas.add(berita);


                }
                goodNewsAdapter = new GoodNewsAdapter(getContext(),mBeritas);
                goodNewsAdapter.notifyDataSetChanged();
                recyclerViewNews.setAdapter(goodNewsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT).show();

            }
        });
    }
}