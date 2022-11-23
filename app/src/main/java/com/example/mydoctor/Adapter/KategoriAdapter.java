package com.example.mydoctor.Adapter;

import android.content.Context;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.mydoctor.Activity.ListDokterActivity;
import com.example.mydoctor.Model.Kategori;
import com.example.mydoctor.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.KategoriViewHolder> {
    private ArrayList<Kategori>kategoris;
    private Context context;
    public KategoriAdapter(Context context,ArrayList<Kategori>mKategories){
        this.context = context;
        this.kategoris = mKategories;

    }

    @NonNull
    @Override
    public KategoriViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kategori,parent,false);
        return new KategoriViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KategoriViewHolder holder, int position) {
        holder.bind(kategoris.get(position));

    }

    @Override
    public int getItemCount() {
        return kategoris.size();
    }

    public class KategoriViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imageView;
        private TextView tvTitle;
        private StorageReference storageReference;
        public KategoriViewHolder(@NonNull View itemView) {
            super(itemView);
           imageView = itemView.findViewById(R.id.IconKategori);
           tvTitle = itemView.findViewById(R.id.tvKategoriDokter);


        }
        public void bind(Kategori kategori){
            tvTitle.setText(kategori.getNama());
            Glide.with(context)
                .load(kategori.getIcon())
                .into(imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ListDokterActivity.class);
                    intent.putExtra(ListDokterActivity.EXTRA_KATEGORI,kategori);
                    context.startActivity(intent);
                }
            });





        }
    }

}
