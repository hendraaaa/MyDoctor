package com.example.mydoctor.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mydoctor.Activity.DetailNewsActivity;
import com.example.mydoctor.Model.Berita;
import com.example.mydoctor.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class GoodNewsAdapter extends RecyclerView.Adapter<GoodNewsAdapter.GoodNewsHolder> {
    private ArrayList<Berita> beritas;
    private Context context;
    public GoodNewsAdapter(Context context,ArrayList<Berita>mBerita){
        this.context = context;
        this.beritas = mBerita;

    }
    @NonNull
    @Override
    public GoodNewsAdapter.GoodNewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_good_news,parent,false);
        return new GoodNewsAdapter.GoodNewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoodNewsAdapter.GoodNewsHolder holder, int position) {
        holder.bind(beritas.get(position));


    }

    @Override
    public int getItemCount() {
        return beritas.size();
    }

    public class GoodNewsHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView judul,day;

        public GoodNewsHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageNews);
            judul = itemView.findViewById(R.id.tvJudul);
            day = itemView.findViewById(R.id.tvDay);
        }
        public void bind(Berita berita) {
            judul.setText(berita.getJudul());
            day.setText(berita.getDay());
            Glide
                    .with(context)
                    .load(berita.getImage())
                    .into(imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetailNewsActivity.class);
                    intent.putExtra(DetailNewsActivity.EXTRA_NEWS,berita);
                    context.startActivity(intent);
                }
            });

        }
    }
}
