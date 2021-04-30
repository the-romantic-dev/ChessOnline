package org.spbstu.ysa.chessonline.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.spbstu.ysa.chessonline.R;

public class SettingsActivity extends AppCompatActivity {


    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setViews();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMenu();
            }
        });

    }

    private void setViews() {
        backButton = findViewById(R.id.back_to_menu_button);
    }

    private void backToMenu() {
        Intent backToMenu = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(backToMenu);
        finish();
    }

    @Override
    public void onBackPressed() {
        backToMenu();
    }

}