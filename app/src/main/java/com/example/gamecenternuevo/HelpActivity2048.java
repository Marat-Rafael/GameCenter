package com.example.gamecenternuevo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

public class HelpActivity2048 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_2048);
        setTitle("Help 2048");
        animarBackgroundHelp2048();
    }



    /**
     * Metodo para animar Background
     */
    private void animarBackgroundHelp2048() {
        // ----------------animamos background del constraint layout -------------------------------
        ConstraintLayout constraintLayout = findViewById(R.id.layout_activity_help_2048);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(2500);
        animationDrawable.start();
        //-------------------------------fin animacion----------------------------------------------
    }
}