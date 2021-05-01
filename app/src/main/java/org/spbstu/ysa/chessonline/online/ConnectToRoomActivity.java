package org.spbstu.ysa.chessonline.online;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.spbstu.ysa.chessonline.R;

public class ConnectToRoomActivity extends AppCompatActivity {
    private Button connectToRoomButton;
    private EditText edRoomPass;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        setViews();

        mDatabase = FirebaseDatabase.getInstance().getReference("games");

        connectToRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = edRoomPass.getText().toString();
                edRoomPass.setText("");

                if (!TextUtils.isEmpty(pass)) {
                    String key = findRoomListener(pass);

                    if (key != null) {
                        Toast.makeText(getApplicationContext(), "Игра найдена", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Игра не найдена", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Пустое поле", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void setViews() {
        connectToRoomButton = findViewById(R.id.connectToRoom);
        edRoomPass = findViewById(R.id.RoomPass);
    }

    private String findRoomListener(final String password) {
        final String[] key = {null};
        ValueEventListener roomsListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    Room room = ds.getValue(Room.class);
                    Log.d("myTag", "Room pass: " + room.getPassword());
                    if (room.getPassword().equals(password)) {
                        key[0] = ds.getKey();
                        Log.d("myTag", "Room key: " + ds.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("dbLog", "findRoomListener:onCancelled", databaseError.toException());
            }
        };
        mDatabase.addListenerForSingleValueEvent(roomsListener);
        Log.d("myTag", "Room key: " + key[0]);
        return key[0];
    }

    private void roomNodeListener() {
        ChildEventListener gameChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String chessBoard = snapshot.getValue(String.class);
                //переписываем свое поле
                Log.d("myLogs", chessBoard);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mDatabase.addChildEventListener(gameChildEventListener);
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(ConnectToRoomActivity.this, OnlineActivity.class));
        finish();
    }

}