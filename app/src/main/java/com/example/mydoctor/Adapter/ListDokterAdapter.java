package com.example.mydoctor.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mydoctor.Activity.ChatActivity;
import com.example.mydoctor.Model.Users;
import com.example.mydoctor.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListDokterAdapter extends RecyclerView.Adapter<ListDokterAdapter.ListDokterViewHolder> {
    private ArrayList<Users> dokters;
    private Context context;
    public ListDokterAdapter(Context context,ArrayList<Users>mDokters){
        this.context = context;
        this.dokters = mDokters;

    }
    @NonNull
    @Override
    public ListDokterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_dokter,parent,false);
        return new ListDokterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListDokterViewHolder holder, int position) {
        holder.bind(dokters.get(position));


    }

    @Override
    public int getItemCount() {
        return dokters.size();
    }

    public class ListDokterViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imageView;

        private TextView nama,gender;

        public ListDokterViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            nama = itemView.findViewById(R.id.name);
            gender = itemView.findViewById(R.id.gender);
        }
        public void bind(Users dokters){
            nama.setText(dokters.getUsername());
            gender.setText("Laki-laki");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChatActivity.class);
                    intent.putExtra("userid",dokters.getId());
                    context.startActivity(intent);
                }
            });
            if (!dokters.getImageURL().equals("default")){
                Glide
                        .with(context)
                        .load(dokters.getImageURL())
                        .into(imageView);
            }
        }
    }
}
