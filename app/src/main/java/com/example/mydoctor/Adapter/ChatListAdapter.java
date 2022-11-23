package com.example.mydoctor.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mydoctor.Activity.ChatActivity;
import com.example.mydoctor.Model.Messages;
import com.example.mydoctor.Model.Users;
import com.example.mydoctor.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatViewHolder> {
    private ArrayList<Users> dokters;
    private Context context;
    private boolean isChat;
    private ValueEventListener valueEventListener;
    String theLastMessage;
    public ChatListAdapter(Context context,ArrayList<Users>mDokters,boolean isChat){
        this.context = context;
        this.dokters = mDokters;
        this.isChat = isChat;

    }
    @NonNull
    @Override
    public ChatListAdapter.ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_list,parent,false);
        return new ChatListAdapter.ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListAdapter.ChatViewHolder holder, int position) {
        holder.bind(dokters.get(position));


    }

    @Override
    public int getItemCount() {
        return dokters.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imageView;

        private TextView nama,lastCht;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            nama = itemView.findViewById(R.id.name);
            lastCht = itemView.findViewById(R.id.tvLastCht);
        }
        public void bind(Users dokters){
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    deleteChat();
                    return true;
                }
            });
            nama.setText(dokters.getUsername());
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
            if (isChat){
                lastMessage(dokters.getId(),lastCht);
            }else {
                lastCht.setVisibility(View.GONE);
            }
        }
    }
    private void lastMessage(final String userid, final TextView last_msg){
        theLastMessage = "default";
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Messages chat = snapshot.getValue(Messages.class);
                    if (firebaseUser != null && chat != null) {
                        if (chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userid) ||
                                chat.getReceiver().equals(userid) && chat.getSender().equals(firebaseUser.getUid())) {
                            theLastMessage = chat.getMessage();
                        }
                    }
                }

                switch (theLastMessage){
                    case  "default":
                        last_msg.setText("No Message");
                        break;

                    default:
                        last_msg.setText(theLastMessage);
                        break;
                }

                theLastMessage = "default";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void deleteChat(){
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("chatList").child(firebaseUser.getUid());
        reference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context,"Berhasil di hapus",Toast.LENGTH_SHORT).show();
            }
        });




    }
}
