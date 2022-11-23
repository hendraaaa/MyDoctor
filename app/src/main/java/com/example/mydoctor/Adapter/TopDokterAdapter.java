package com.example.mydoctor.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mydoctor.Model.Users;
import com.example.mydoctor.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class TopDokterAdapter extends RecyclerView.Adapter<TopDokterAdapter.TopViewHolder> {
    private ArrayList<Users> dokters;
    private Context context;
    public TopDokterAdapter(Context context,ArrayList<Users>mDokters){
        this.context = context;
        this.dokters = mDokters;

    }
    @NonNull
    @Override
    public TopDokterAdapter.TopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_dokter,parent,false);
        return new TopDokterAdapter.TopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopDokterAdapter.TopViewHolder holder, int position) {
        holder.bind(dokters.get(position));


    }

    @Override
    public int getItemCount() {
        if (dokters.size() <= 3){
            return dokters.size();
        }else {
            return 3;
        }
    }

    public class TopViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imageView;
        private TextView nama,kategori;

        public TopViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageTop);
            nama = itemView.findViewById(R.id.nameTop);
            kategori = itemView.findViewById(R.id.kategoriTop);
        }
        public void bind(Users dokters){
           nama.setText(dokters.getUsername());
           kategori.setText(dokters.getBidang());
            Glide
                    .with(imageView)
                    .load(dokters.getImageURL())
                    .into(imageView);
        }
    }
}
