package com.example.mydoctor.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mydoctor.Activity.ListDokterActivity;
import com.example.mydoctor.Model.Kategori;
import com.example.mydoctor.Model.Messages;
import com.example.mydoctor.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    private ArrayList<Messages> messages;
    private String imageURL;
    private Context context;
    FirebaseUser firebaseUser;
    public MessageAdapter(Context context,ArrayList<Messages>mMessages,String imageURL){
        this.context = context;
        this.messages = mMessages;
        this.imageURL = imageURL;

    }

    @NonNull
    @Override
    public MessageAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_right,parent,false);
            return new MessageAdapter.MessageViewHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_left,parent,false);
            return new MessageAdapter.MessageViewHolder(view);

        }

    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MessageViewHolder holder, int position) {
        Messages messages1 = messages.get(position);

        holder.tv_content.setText(messages1.getMessage());
        holder.tv_time.setText(messages1.getDate());

       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               DatabaseReference reference = FirebaseDatabase.getInstance().getReference("chats").child(messages1.getKey());
               reference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void aVoid) {
                       Toast.makeText(context,"Berhasil di hapus",Toast.LENGTH_SHORT).show();
                   }
               });


           }
       });


        if (position == messages.size() -1){
            if (messages1.isIsseen()){
                holder.imageView.setImageResource(R.drawable.ic_done_all);
            }else {
                holder.imageView.setImageResource(R.drawable.ic_done);

            }
        }else {
            holder.imageView.setVisibility(View.GONE);

        }


    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView tv_content,tv_time;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_content = itemView.findViewById(R.id.text_content);
            tv_time = itemView.findViewById(R.id.text_time);
            imageView = itemView.findViewById(R.id.Seen);


        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (messages.get(position).getSender().equals(firebaseUser.getUid())){
            return MSG_TYPE_RIGHT;
        }else {
            return MSG_TYPE_LEFT;
        }
    }

}
