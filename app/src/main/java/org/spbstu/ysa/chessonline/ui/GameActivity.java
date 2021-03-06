package org.spbstu.ysa.chessonline.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class GameActivity extends AndroidApplication {
    DatabaseReference ref;
    String roomKey;
    //ссыль на бд
    boolean isOnline;

    boolean mainIsCreated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ref = FirebaseDatabase.getInstance().getReference("games");
        super.onCreate(savedInstanceState);

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        isOnline = getIntent().getBooleanExtra("isOnline", false);

        if (!isOnline) {
            initialize(new ChessGame(true, GameActivity.this), config);
        } else {
            boolean isHost = getIntent().getBooleanExtra("isHost", false);
            boolean creatorIsWhite = getIntent().getBooleanExtra("creatorIsWhite", false);
            roomKey = getIntent().getStringExtra("roomKey");

            if (isOnline) {
                ref.child(roomKey).child("connection").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            boolean playerIsLeave = !dataSnapshot.getValue(boolean.class);
                            if (playerIsLeave) {
                                //удаляем комнату
                                ref.child(roomKey).removeValue();
                                ref.child(roomKey).child("connection").removeEventListener(this);
                                //завершаем игру
                                if (!mainIsCreated) {
                                    mainIsCreated = true;
                                    Log.d("MAIN_ACTIVITY", "IS CALLED");
                                    startActivity(new Intent(GameActivity.this, MainActivity.class));
                                    finish();
                                }

                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w("dbLog", "findRoomListener:onCancelled", databaseError.toException());
                    }
                });
            }


            if (isHost)
                initialize(new ChessGame(ref.child(roomKey), isHost, creatorIsWhite, this), config);

            else {
                initialize(new ChessGame(ref.child(roomKey), isHost, !creatorIsWhite, this), config);
            }
        }

    }

    @Override
    public void onBackPressed() {
        if (!mainIsCreated) {
            mainIsCreated = true;
            startActivity(new Intent(GameActivity.this, MainActivity.class));
        }
        finish();
    }

    public void backToMenu() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Если комната существует, connection = false;
        if (isOnline) {
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        ref.child(roomKey).child("connection").setValue(false);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }

    }
}