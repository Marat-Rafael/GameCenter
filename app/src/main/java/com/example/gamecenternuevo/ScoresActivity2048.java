package com.example.gamecenternuevo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.TextView;

public class ScoresActivity2048 extends AppCompatActivity {

    SQLiteDatabase db;
    UsuariosHelper helper;
    TextView textViewTodasPuntuacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores2048);
        setTitle("Score 2048");

        textViewTodasPuntuacion = findViewById(R.id.scores_layout_2048_resultados);
        helper = new UsuariosHelper(this);
        animarBackgroundScore2048();
        db = helper.getReadableDatabase();


        helper.mostrarTodosUsuariosConPuntuacion(textViewTodasPuntuacion,db);

    }

    /**
     * Metodo para animar Background
     */
    private void animarBackgroundScore2048() {
        // ----------------animamos background del constraint layout -------------------------------
        ConstraintLayout constraintLayout = findViewById(R.id.layout_activity_scores_2048);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(2500);
        animationDrawable.start();
        //-------------------------------fin animacion----------------------------------------------
    }

}