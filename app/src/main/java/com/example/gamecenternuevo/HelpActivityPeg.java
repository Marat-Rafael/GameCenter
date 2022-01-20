package com.example.gamecenternuevo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

public class HelpActivityPeg extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_peg);
        setTitle("Help Peg");
        animarBackgroundHelpPeg();

    }

    /**
     * Metodo para animar Background
     */
    private void animarBackgroundHelpPeg() {
        // ----------------animamos background del constraint layout -------------------------------
        ConstraintLayout constraintLayout = findViewById(R.id.layout_activity_help_peg);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(2500);
        animationDrawable.start();
        //-------------------------------fin animacion----------------------------------------------
    }
}