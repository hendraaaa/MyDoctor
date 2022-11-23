package com.example.mydoctor.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mydoctor.API.APIService;
import com.example.mydoctor.Adapter.MessageAdapter;
import com.example.mydoctor.Model.Messages;
import com.example.mydoctor.Model.Users;
import com.example.mydoctor.Notification.Client;
import com.example.mydoctor.Notification.Data;
import com.example.mydoctor.Notification.MyResponse;
import com.example.mydoctor.Notification.Sender;
import com.example.mydoctor.Notification.Token;
import com.example.mydoctor.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    public final static String EXTRA_DOKTER = "extra_dokter";
    private ImageView btn_send;
    private EditText et_content;
    private DatabaseReference reference;
    private FirebaseUser fuser;
  //  private Users dokters;
    private MessageAdapter messageAdapter;
    private ArrayList<Messages> mMessages;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private ValueEventListener valueEventListener;
    private APIService apiService;
    boolean notify = false;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
     //   dokters = getIntent().getParcelableExtra(EXTRA_DOKTER);
        userId = getIntent().getStringExtra("userid");

        initToolbarr();

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        btn_send = findViewById(R.id.btn_send);
        et_content = findViewById(R.id.text_content);
        et_content.addTextChangedListener(textWatcher);

        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        recyclerView = findViewById(R.id.recyclerViewMessage);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mMessages = new ArrayList<>();
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notify = true;
                String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                sendMessage(fuser.getUid(), userId, et_content.getText().toString(), currentTime);
                et_content.setText("");
            }
        });




        reference = FirebaseDatabase.getInstance().getReference("users").child(fuser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users1 = snapshot.getValue(Users.class);


                readMessage(fuser.getUid(),userId, users1.getImageURL());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        seenMessage(userId);





    }
    private void seenMessage(final String userid){
        reference = FirebaseDatabase.getInstance().getReference("chats");
        valueEventListener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Messages chat = snapshot.getValue(Messages.class);
                    if (chat.getReceiver().equals(fuser.getUid()) && chat.getSender().equals(userid)){
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("isseen", true);
                        snapshot.getRef().updateChildren(hashMap);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.toString().trim().length() == 0) {
                btn_send.setImageResource(R.drawable.ic_thumb_up);

            } else {
                btn_send.setImageResource(R.drawable.ic_send);
            }

        }
    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendMessage(String sender, String receiver, String message, String waktu) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        hashMap.put("date", waktu);
        hashMap.put("isseen",false);
        databaseReference.child("chats").push().setValue(hashMap);
        DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("chatList")
                .child(fuser.getUid())
                .child(userId);
        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()){
                    chatRef.child("id").setValue(userId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        final String msg = message;

        reference = FirebaseDatabase.getInstance().getReference("users").child(fuser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users user = dataSnapshot.getValue(Users.class);
                if (notify) {
                    sendNotifiaction(receiver, user.getUsername(), msg);
                }
                notify = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void sendNotifiaction(String receiver, final String username, final String message){
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = tokens.orderByKey().equalTo(receiver);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Token token = snapshot.getValue(Token.class);
                    Data data = new Data(fuser.getUid(), R.mipmap.ic_launcher, username+": "+message, "New Message",
                            userId);

                    Sender sender = new Sender(data, token.getToken());

                    apiService.sendNotification(sender)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                    if (response.code() == 200){
                                        if (response.body().success != 1){
                                            Toast.makeText(ChatActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {

                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void readMessage(String myid, String userId, String imgUrl) {
        reference = FirebaseDatabase.getInstance().getReference("chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mMessages.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Messages messages = dataSnapshot.getValue(Messages.class);
                    if (messages.getReceiver().equals(myid) && messages.getSender().equals(userId) ||
                            messages.getReceiver().equals(userId) && messages.getSender().equals(myid)) {
                        mMessages.add(messages);
                        messages.setKey(dataSnapshot.getKey());
                    }
                    messageAdapter = new MessageAdapter(ChatActivity.this, mMessages, imgUrl);
                    messageAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(messageAdapter);
                    recyclerView.scrollToPosition(messageAdapter.getItemCount() - 1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            recyclerView.scrollToPosition(messageAdapter.getItemCount() - 1);
                        }
                    }, 1000);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initToolbarr() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users").child(userId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                getSupportActionBar().setTitle(users.getUsername());
                if (users.getStatus().equals("online")){
                    toolbar.setSubtitle("Active Now");


                }else {
                    toolbar.setSubtitle("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







//        getSupportActionBar().setTitle(dokters.getUsername());
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        if (valueEventListener != null && reference!= null){
            reference.removeEventListener(valueEventListener);
        }

        status("offline");
    }
}