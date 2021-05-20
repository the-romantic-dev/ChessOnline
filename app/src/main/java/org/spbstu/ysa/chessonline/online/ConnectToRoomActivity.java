package org.spbstu.ysa.chessonline.online;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.spbstu.ysa.chessonline.R;
import org.spbstu.ysa.chessonline.ui.GameActivity;

public class ConnectToRoomActivity extends AppCompatActivity {
    private Button connectToRoomButton;
    private EditText edRoomPass;
    private TextView helloText;
    private TextView loadingText;
    private DatabaseReference mDatabase;
    private String roomKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        setViews();

        mDatabase = FirebaseDatabase.getInstance().getReference("games");

        connectToRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String pass = edRoomPass.getText().toString();
                edRoomPass.setText("");

                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(getApplicationContext(), "Пустое поле", Toast.LENGTH_SHORT).show();
                } else {
                    helloText.setVisibility(View.INVISIBLE);
                    loadingText.setVisibility(View.VISIBLE);
                    connectToRoomButton.setEnabled(false);

                    mDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds: dataSnapshot.getChildren()) {
                                Room room = ds.getValue(Room.class);
                                //Log.d("myTag", "Room pass: " + room.getPassword());
                                //Log.d("myTag", "Room key in progress" + roomKey);
                                if (room.getPassword().equals(pass) && !room.getConnection()) {
                                    roomKey = ds.getKey();
                                    mDatabase.child(roomKey).child("connection").setValue(true);

                                    Toast.makeText(getApplicationContext(), "Игра найдена", Toast.LENGTH_SHORT).show();
                                    Log.d("myTag", "Final room key:" + roomKey);
                                    mDatabase.removeEventListener(this);
                                    Intent startGameActivity = new Intent(ConnectToRoomActivity.this, GameActivity.class);
                                    startGameActivity.putExtra("isOnline", true);
                                    startGameActivity.putExtra("isHost", false);
                                    startGameActivity.putExtra("creatorIsWhite", !room.getCreatorColor());
                                    startGameActivity.putExtra("roomKey", roomKey);
                                    startActivity(startGameActivity);
                                    //startGameActivity
                                }
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w("dbLog", "findRoomListener:onCancelled", databaseError.toException());
                        }
                    });
                }
            }
        });
    }

    private void setViews() {
        connectToRoomButton = findViewById(R.id.connectToRoom);
        edRoomPass = findViewById(R.id.RoomPass);
        helloText = findViewById(R.id.hello);
        loadingText = findViewById(R.id.loading);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ConnectToRoomActivity.this, OnlineActivity.class));
        finish();
    }

}