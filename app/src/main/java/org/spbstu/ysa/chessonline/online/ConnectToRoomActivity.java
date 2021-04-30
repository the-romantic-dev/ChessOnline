package org.spbstu.ysa.chessonline.online;


import android.os.Bundle;
import android.text.TextUtils;
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

public class ConnectToRoomActivity extends AppCompatActivity {
    private Button connectToRoomButton;
    private EditText edRoomPass;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        setViews();

        mDatabase = FirebaseDatabase.getInstance().getReference("games").push();

        connectToRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = edRoomPass.getText().toString();
                //Вместо chessBoard будет класс с состоянием шахматного поля
                String chessBoard = "data";

                if (!TextUtils.isEmpty(pass)) {
                    mDatabase.child("password").setValue(pass);
                    mDatabase.setValue(chessBoard);
                } else {
                    Toast.makeText(getApplicationContext(), "Пустое поле", Toast.LENGTH_SHORT).show();
                }
                //переход в ожидание
            }
        });
        gameChangesListener();
    }

    private void setViews() {
        connectToRoomButton = findViewById(R.id.connectToRoom);
        edRoomPass = findViewById(R.id.RoomPass);
    }

    private void gameChangesListener() {
        ChildEventListener gameChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String chessBoard = snapshot.getValue(String.class);
                //переписываем свое поле
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

}