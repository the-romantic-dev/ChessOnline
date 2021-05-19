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

public class CreateRoomActivity extends AppCompatActivity {
    private Button createRoomButton;
    private EditText edRoomPass;
    private TextView helloText;
    private TextView loadingText;
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
                Room room = new Room(pass, chessBoard, false);

                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(getApplicationContext(), "Пустое поле", Toast.LENGTH_SHORT).show();
                } else {
                    mDatabase.setValue(room);
                    Toast.makeText(getApplicationContext(), "Игра создана", Toast.LENGTH_SHORT).show();
                    //блокировать экран и ждать присоединения второго игрока
                    helloText.setVisibility(View.INVISIBLE);
                    loadingText.setVisibility(View.VISIBLE);
                    createRoomButton.setEnabled(false);

                    mDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Room room  = dataSnapshot.getValue(Room.class);
                            if (room.getConnection()) {
                                Toast.makeText(getApplicationContext(), "Соперник найден, подключение..", Toast.LENGTH_SHORT).show();
                                mDatabase.removeEventListener(this);
                                //startGameActivity
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
        createRoomButton = findViewById(R.id.createRoom);
        edRoomPass = findViewById(R.id.newRoomPass);
        helloText = findViewById(R.id.hello);
        loadingText = findViewById(R.id.loading);
    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(CreateRoomActivity.this, OnlineActivity.class));
        finish();
    }

}