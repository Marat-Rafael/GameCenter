package com.example.gamecenternuevo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ScoresActivity2048 extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {

    SQLiteDatabase db;
    UsuariosHelper helper;
    TextView textViewTodasPuntuacion;
    final String TAG = "juego";

    // creamos objeto spinner para diferentes opciones del ordenamiento
    private Spinner spiner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores2048);
        setTitle("Score 2048");

        textViewTodasPuntuacion = findViewById(R.id.scores_layout_2048_resultados);
        helper = new UsuariosHelper(this);
        animarBackgroundScore2048();
        db = helper.getReadableDatabase();

        // identificamos spinner
        spiner = findViewById(R.id.scores_spinner);

        // creamos array de strings para usar en spiner
        String[] opcionesSpinner = {"Usuario", "Puntuacion"};

        spiner.setPrompt("Ordenamiento");

        // declaramos i iniciamos arrayAdapter , le pasamos como parametros context, tipo del spiner, i lista de opciones
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcionesSpinner);

        // al adapter declarado agregamos adapter
        spiner.setAdapter(arrayAdapter);

        // HAY QUE AÑADIR onItemSelectListener !!!!
        spiner.setOnItemSelectedListener(this);

        //helper.mostrarTodosUsuariosConPuntuacion(textViewTodasPuntuacion,db);

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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("spinner","casos");
        String orderBy;
        switch (i) {
            case -1:
                Log.d(TAG,"caso -1 : error");
                break;
            case 0:
                orderBy = "Usuario";
                Log.d(TAG,"caso 0 segun id");
                helper.mostrarTodosUsuariosConPuntuacion(textViewTodasPuntuacion,db,orderBy);
                break;
            case 1:
                orderBy = "Puntuacion_2048";
                Log.d(TAG,"caso 1 segun usuario");
                helper.mostrarTodosUsuariosConPuntuacion(textViewTodasPuntuacion,db,orderBy);
                break;

        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}