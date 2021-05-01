package org.spbstu.ysa.chessonline.online;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.spbstu.ysa.chessonline.R;
import org.spbstu.ysa.chessonline.ui.MainActivity;

public class OnlineActivity extends AppCompatActivity {
    private Button createRoom;
    private Button connectToRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online);
        setViews();

        createRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startGameActivity = new Intent(OnlineActivity.this, CreateRoomActivity.class);
                startActivity(startGameActivity);
                finish();
            }
        });

        connectToRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startGameActivity = new Intent(OnlineActivity.this, ConnectToRoomActivity.class);
                startActivity(startGameActivity);
                finish();
            }
        });
    }

    private void setViews() {
        createRoom = findViewById(R.id.create_room);
        connectToRoom = findViewById(R.id.connect_room);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(OnlineActivity.this, MainActivity.class));
        finish();
    }

}