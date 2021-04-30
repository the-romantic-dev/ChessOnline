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

import org.spbstu.ysa.chessonline.R;

public class CreateRoomActivity extends AppCompatActivity {
    private Button createRoomButton;
    private EditText edRoomPass;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        setViews();

        createRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase = FirebaseDatabase.getInstance().getReference("games").push();

                String pass = edRoomPass.getText().toString();
                edRoomPass.setText("");
                //Вместо chessBoard будет класс с состоянием шахматного поля
                String chessBoard = "data";
                Room room = new Room(pass, chessBoard);

                if (!TextUtils.isEmpty(pass)) {
                    mDatabase.setValue(room);
                    Toast.makeText(getApplicationContext(), "Игра создана", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Пустое поле", Toast.LENGTH_SHORT).show();
                }

                //roomNodeListener();
                //переход в ожидание
            }
        });

    }

    private void setViews() {
        createRoomButton = findViewById(R.id.createRoom);
        edRoomPass = findViewById(R.id.newRoomPass);
    }

    private void roomNodeListener() {
        ChildEventListener gameChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Room gameRoom = snapshot.getValue(Room.class);
                //переписываем свое поле
                Log.d("myLogs", gameRoom.getPassword());
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
        startActivity(new Intent(CreateRoomActivity.this, OnlineActivity.class));
        finish();
    }

}