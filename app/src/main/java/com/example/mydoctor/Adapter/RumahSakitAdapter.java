package com.example.mydoctor.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mydoctor.Model.RumahSakit;
import com.example.mydoctor.R;

import java.util.ArrayList;

public class RumahSakitAdapter extends RecyclerView.Adapter<RumahSakitAdapter.RumahSakitViewHolder> {
    private ArrayList<RumahSakit> rumahSakits;
    private Context context;
    public RumahSakitAdapter(Context context,ArrayList<RumahSakit>rumahSakits){
        this.context = context;
        this.rumahSakits = rumahSakits;

    }
    @NonNull
    @Override
    public RumahSakitAdapter.RumahSakitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hospital,parent,false);
        return new RumahSakitAdapter.RumahSakitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RumahSakitAdapter.RumahSakitViewHolder holder, int position) {
        holder.bind(rumahSakits.get(position));


    }

    @Override
    public int getItemCount() {
        return rumahSakits.size();
    }

    public class RumahSakitViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView title,alamat;

        public RumahSakitViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            alamat = itemView.findViewById(R.id.alamat);
        }
        public void bind(RumahSakit rumahSakit) {
            title.setText(rumahSakit.getNama());
            alamat.setText(rumahSakit.getAlamat());
            Glide
                    .with(context)
                    .load(rumahSakit.getImage())
                    .into(imageView);

        }
    }
}
