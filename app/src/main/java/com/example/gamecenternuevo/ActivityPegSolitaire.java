package com.example.gamecenternuevo;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class ActivityPegSolitaire extends AppCompatActivity {

    // creamos un objeto de SoundPlayer
    private static SoundPlayer soundPlayerPeg;

    private static final String TAG = "JUEGO";
    public TextView[][] matrixTextView;
    private boolean casillasJugablesEncontradas;
    private boolean gameOver;
    private int puntuacion;
    private String NombreJugador;

    // public static boolean booleanCasillaEligida = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peg_solitaire);
        setTitle("Peg Solitaire");

        // iniciamos soundPlayer
        soundPlayerPeg = new SoundPlayer(this);

        // animamos background ( cambia colores)
        animarBackgroundPeg();

        // creamos matriz
        matrixTextView = new TextView[][]{
                {findViewById(R.id.tv_00), findViewById(R.id.tv_01), findViewById(R.id.tv_02), findViewById(R.id.tv_03), findViewById(R.id.tv_04), findViewById(R.id.tv_05), findViewById(R.id.tv_06)},
                {findViewById(R.id.tv_10), findViewById(R.id.tv_11), findViewById(R.id.tv_12), findViewById(R.id.tv_13), findViewById(R.id.tv_14), findViewById(R.id.tv_15), findViewById(R.id.tv_16)},
                {findViewById(R.id.tv_20), findViewById(R.id.tv_21), findViewById(R.id.tv_22), findViewById(R.id.tv_23), findViewById(R.id.tv_24), findViewById(R.id.tv_25), findViewById(R.id.tv_26)},
                {findViewById(R.id.tv_30), findViewById(R.id.tv_31), findViewById(R.id.tv_32), findViewById(R.id.tv_33), findViewById(R.id.tv_34), findViewById(R.id.tv_35), findViewById(R.id.tv_36)},
                {findViewById(R.id.tv_40), findViewById(R.id.tv_41), findViewById(R.id.tv_42), findViewById(R.id.tv_43), findViewById(R.id.tv_44), findViewById(R.id.tv_45), findViewById(R.id.tv_46)},
                {findViewById(R.id.tv_50), findViewById(R.id.tv_51), findViewById(R.id.tv_52), findViewById(R.id.tv_53), findViewById(R.id.tv_54), findViewById(R.id.tv_55), findViewById(R.id.tv_56)},
                {findViewById(R.id.tv_60), findViewById(R.id.tv_61), findViewById(R.id.tv_62), findViewById(R.id.tv_63), findViewById(R.id.tv_64), findViewById(R.id.tv_65), findViewById(R.id.tv_66)},
        };

        juegoBase();
        Log.d(TAG, "sale onCreate");

    }

    /**
     *  METODO PRINCIPAL JUEGO
     * metodo que contiene todos metodos del juego
     */
    public void juegoBase() {
        llenarMatrix();
        eliminarCasillasEsquina();
        generarCasillaVaciaEnCentro();
        buscarTodasCasillasJugables();
        listenerParaCasillasJugablesConvertirSelecionada();
        detectarCasillaSelecionada();

    }

    public void volverBuscarDetectarSaltar() {
        buscarTodasCasillasJugables();
        listenerParaCasillasJugablesConvertirSelecionada();
        detectarCasillaSelecionada();
    }

    /**
     * Metodo para animar Background
     */
    private void animarBackgroundPeg() {
        // ----------------animamos background del constraint layout -------------------------------
        ConstraintLayout constraintLayout = findViewById(R.id.layout_principal_peg);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(2500);
        animationDrawable.start();
        //-------------------------------fin animacion----------------------------------------------
    }


    /**
     * metodo para crear matriz bidimencional de TextView
     */
    public void crearMatrix() {
        Log.d(TAG, "entra crear matriz");
        matrixTextView = new TextView[][]{

                {findViewById(R.id.tv_00), findViewById(R.id.tv_01), findViewById(R.id.tv_02), findViewById(R.id.tv_03), findViewById(R.id.tv_04), findViewById(R.id.tv_05), findViewById(R.id.tv_06)},
                {findViewById(R.id.tv_10), findViewById(R.id.tv_11), findViewById(R.id.tv_12), findViewById(R.id.tv_13), findViewById(R.id.tv_14), findViewById(R.id.tv_15), findViewById(R.id.tv_16)},
                {findViewById(R.id.tv_20), findViewById(R.id.tv_21), findViewById(R.id.tv_22), findViewById(R.id.tv_23), findViewById(R.id.tv_24), findViewById(R.id.tv_25), findViewById(R.id.tv_26)},
                {findViewById(R.id.tv_30), findViewById(R.id.tv_31), findViewById(R.id.tv_32), findViewById(R.id.tv_33), findViewById(R.id.tv_34), findViewById(R.id.tv_35), findViewById(R.id.tv_36)},
                {findViewById(R.id.tv_40), findViewById(R.id.tv_41), findViewById(R.id.tv_42), findViewById(R.id.tv_43), findViewById(R.id.tv_44), findViewById(R.id.tv_45), findViewById(R.id.tv_46)},
                {findViewById(R.id.tv_50), findViewById(R.id.tv_51), findViewById(R.id.tv_52), findViewById(R.id.tv_53), findViewById(R.id.tv_54), findViewById(R.id.tv_55), findViewById(R.id.tv_56)},
                {findViewById(R.id.tv_60), findViewById(R.id.tv_61), findViewById(R.id.tv_62), findViewById(R.id.tv_63), findViewById(R.id.tv_64), findViewById(R.id.tv_65), findViewById(R.id.tv_66)},
        };
        Log.d(TAG, "sale crear matriz");
    }

    /**
     * metodo para rellenar matriz de texto con sus valores correspondentes I J
     * solo para identificar casillas
     */
    public void llenarMatrix() {
        Log.d(TAG, "entra llenar matriz");
        for (int i = 0; i < matrixTextView.length; i++) {
            for (int j = 0; j < matrixTextView[0].length; j++) {
                // cambiarBackground(matrixTextView[i][j], R.drawable.casilla_rellena);
                convertirCasilla_A_Rellena(matrixTextView[i][j]);
                // matrixTextView[i][j].setBackgroundResource(R.drawable.casilla_rellena);
                // matrixTextView[i][j].setText("i="+i + " j=" + j);
            }
        }
        Log.d(TAG, "sale llenar matriz");
    }

    /**
     * metodo para eliminar agunas casillas de las esquinas
     */
    public void eliminarCasillasEsquina() {
        Log.d(TAG, "entra eliminar esquinas matriz");
        for (int i = 0; i < matrixTextView.length; i++) {
            for (int j = 0; j < matrixTextView[0].length; j++) {
                // primera esquina
                if ((i == 0 && j == 0) || (i == 0 && j == 1) || (i == 1 && j == 1) || (i == 1 && j == 0)) {
                    convertirCasilla_A_Disable(matrixTextView[i][j]);
                }
                // segunda esquina
                if ((i == 0 && j == 5) || (i == 0 && j == 6) || (i == 1 && j == 5) || (i == 1 && j == 6)) {
                    convertirCasilla_A_Disable(matrixTextView[i][j]);
                }
                // tercera esquina
                if ((i == 5 && j == 0) || (i == 5 && j == 1) || (i == 6 && j == 0) || (i == 6 && j == 1)) {
                    convertirCasilla_A_Disable(matrixTextView[i][j]);
                }
                // cuarta esquina
                if ((i == 5 && j == 5) || (i == 5 && j == 6) || (i == 6 && j == 5) || (i == 6 && j == 6)) {
                    convertirCasilla_A_Disable(matrixTextView[i][j]);
                }
            }
        }
        Log.d(TAG, "sale eliminar esquinas matriz");
    }


    /**
     * metodo para crear casilla vacia en el centro
     */
    public void generarCasillaVaciaEnCentro() {
        Log.d(TAG, "entra generar casilla vacia matriz");
        for (int i = 0; i < matrixTextView.length; i++) {
            for (int j = 0; j < matrixTextView[0].length; j++) {
                if ((i == 3 && j == 3) && (matrixTextView[i][j].isEnabled())) {
                    convertirCasilla_A_Vacia(matrixTextView[i][j]);
                }
            }
        }
        Log.d(TAG, "sale generar casilla vacia matriz");
    }


    /**
     * busca todas casillas jugables para casillas vacias
     */
    public void buscarTodasCasillasJugables() {

        casillasJugablesEncontradas = false;
        int numeroDeCasillasEncontradas = 0;

        Log.d(TAG, "entra buscar todas casillas jugables  --- V2 ---");
        for (int i = 0; i < matrixTextView.length; i++) {
            for (int j = 0; j < matrixTextView[0].length; j++) {
                // buscamos casilla vacia
                if (matrixTextView[i][j].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.casilla_vacia, null).getConstantState())) {
                    // I+2
                    // comprobamos que i + 2 no sale fuera de la matriz y es casilla activa
                    if ((i + 2 < matrixTextView.length) && (matrixTextView[i + 2][j].isEnabled())) {
                        // comparamos que  i + 1 esta llena
                        if (matrixTextView[i + 1][j].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.casilla_rellena, null).getConstantState())) {
                            // comprobamos que i + 2 esta rellena
                            if (matrixTextView[i + 2][j].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.casilla_rellena, null).getConstantState())) {
                                convertirCasilla_A_Jugable(matrixTextView[i + 2][j]);
                                numeroDeCasillasEncontradas++;
                            }
                        }
                    }
                    // I-2
                    // comprobamos que i - 2 no sale fuera de la matriz y es casilla activa
                    if ((i - 2 >= 0) && (matrixTextView[i - 2][j].isEnabled())) {
                        // comparamos que  i - 1 esta rellena
                        if (matrixTextView[i - 1][j].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.casilla_rellena, null).getConstantState())) {
                            // comprobamos que i - 2 esta rellena
                            if (matrixTextView[i - 2][j].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.casilla_rellena, null).getConstantState())) {
                                // cambiamos a jugable
                                convertirCasilla_A_Jugable(matrixTextView[i - 2][j]);
                                numeroDeCasillasEncontradas++;
                            }
                        }
                    }
                    // J+2
                    // comprobamos que j + 2 no sale fuera de bordes y estan activos

                    if ((j + 2 < matrixTextView[0].length) && matrixTextView[i][j + 2].isEnabled()) {
                        // comprobamos que j + 1 es rellena
                        if (matrixTextView[i][j + 1].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.casilla_rellena, null).getConstantState())) {
                            // comprobamos j + 2 tiene que ser tambien rellena
                            if (matrixTextView[i][j + 2].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.casilla_rellena, null).getConstantState())) {
                                // convertimos en casilla jugable
                                convertirCasilla_A_Jugable(matrixTextView[i][j + 2]);
                                numeroDeCasillasEncontradas++;
                            }
                        }
                    }
                    // J-2
                    // comprobamos que j - 2 no sale del borde y esta habilitada
                    if ((j - 2 >= 0) && (matrixTextView[i][j - 2].isEnabled())) {
                        // comprobamos que j - 1 es rellena
                        if (matrixTextView[i][j - 1].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.casilla_rellena, null).getConstantState())) {
                            // comprobamos que j - 2 es relleno
                            if (matrixTextView[i][j - 2].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.casilla_rellena, null).getConstantState())) {
                                // si cumple - convertimos en jugable
                                convertirCasilla_A_Jugable(matrixTextView[i][j - 2]);
                                numeroDeCasillasEncontradas++;
                            }
                        }
                    }
                }
            }
        }
        // comprobamos si hay posibles saltos
        if(numeroDeCasillasEncontradas == 0){
            contarNumeroCasillasRellenas();
        }
        Log.d(TAG, "sale buscar todas casillas jugables  --- V2 ---");
    }

    private void animarEscaladoHastaDesaparecer(TextView textView) {

        Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.scale2);
        textView.startAnimation(animation2);

    }

    private void animarAlfa(TextView textView){
        Animation animationAlfa = AnimationUtils.loadAnimation(this, R.anim.anim_alfa);
        textView.startAnimation(animationAlfa);
    }


    /**
     * metodo para determinar fin de juego
     * contamos numero de casillas rellenas
     * si es = 1 - juego ganado
     * si es mas > 1 juego perdido
     * salte alertDialog que nos lleva Menu Del juego
     */
    public void contarNumeroCasillasRellenas(){

        int numeroCasillasRellenas = 0;
        for (int i = 0; i < matrixTextView.length ; i++) {
            for (int j = 0; j < matrixTextView[0].length; j++) {
                if(esCasillaBuscada(matrixTextView[i][j], R.drawable.casilla_rellena)){
                    numeroCasillasRellenas ++;
                }
            }
        }
        if( numeroCasillasRellenas == 1){
            mostrarToast("Victoria!!!");
            gameOver = true;
            //------ AlertBuilder
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Victoria");
            builder.setIcon(R.drawable.ak_74);
            builder.setMessage("Has ganado partida !");
            soundPlayerPeg.playImperialSong();
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(getApplicationContext(), MenuActivityPegSolitaire.class);
                    ActivityPegSolitaire.this.finish();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        if( numeroCasillasRellenas > 1){
            mostrarToast("Has Perdido");
            gameOver = true;
            // ---- AlertBuilder
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("perdido");
            builder.setIcon(R.drawable.gun_rosa_75);
            builder.setMessage("Has perdido partida");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(getApplicationContext(), MenuActivityPegSolitaire.class);
                    ActivityPegSolitaire.this.finish();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    /**
     * Metodo para recorrer matriz y
     * 1 - colocar a Todas casillas que puenen ser jugadas Listener
     * 2 - y convertimos casilla pulsada a casilla selecionada
     * 3 - se activa sonido recarga de arma
     * 4 - llamamos metodo 'detectarCasillaSelecionada'
     * 5 - y convertimos resto de botones jugables a rellenas
     */
    public void listenerParaCasillasJugablesConvertirSelecionada() {
        for (int i = 0; i < matrixTextView.length; i++) {
            for (int j = 0; j < matrixTextView[0].length; j++) {
                if (matrixTextView[i][j].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.casilla_jugable, null).getConstantState())) {
                    matrixTextView[i][j].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.setBackgroundResource(R.drawable.casilla_selecionada);
                            soundPlayerPeg.playZvuk1();
                            detectarCasillaSelecionada();
                            // resto convertimos en casillas rellenas
                            for (int i = 0; i < matrixTextView.length; i++) {
                                for (int j = 0; j < matrixTextView[0].length; j++) {
                                    if (matrixTextView[i][j].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.casilla_jugable, null).getConstantState())) {
                                        matrixTextView[i][j].setBackgroundResource(R.drawable.casilla_rellena);
                                    }
                                }
                            }
                        }
                    });
                }
            }
        }
    }

    /**
     * Metodo para recorrer toda matriz buscando casilla selecionada
     * 1 - detectamos casilla selecionada
     * 2 - comprobamos que no casillas vecinas que pueden ser usadas en juego no sobrepasan matriz y sean activas
     * 3 - comprobamos i + 1, i + 2  si son casillas rellena y vacias convertimos ultima en 'salto_permitido'
     * 4 - ponemos casilla salto_permitido' a la escucha
     * 5 - si casilla salto permitido esta pulsado llamamos metodo 'realizarSalto'
     *
     */
    public void detectarCasillaSelecionada() {
        Log.d(TAG, "entramos detectar casilla selecionada");
        for (int i = 0; i < matrixTextView.length; i++) {
            for (int j = 0; j < matrixTextView[0].length; j++) {
                if (esCasillaBuscada(matrixTextView[i][j], R.drawable.casilla_selecionada)) {

                    // comprobamos que i+2 no pasa fuera de la matriz por abajo y es una casilla activada
                    if ((i + 2 < matrixTextView.length) && matrixTextView[i + 2][j].isEnabled()) {
                        if (esCasillaBuscada(matrixTextView[i + 1][j], R.drawable.casilla_rellena)) {
                            Log.d(TAG, " i=" + i + " | i+1=" + (i + 1) + "|  j=" + j);
                            if (esCasillaBuscada(matrixTextView[i + 2][j], R.drawable.casilla_vacia)) {
                                Log.d(TAG, " i=" + i + " | i+2=" + (i + 2) + "|  j=" + j);
                                convertirCasilla_A_Salto_Permitido(matrixTextView[i + 2][j]);
                                // ponemos a la escucha
                                matrixTextView[i + 2][j].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        v.setOnClickListener(null);
                                        // repintamos celdas
                                        realizarSalto();
                                    }
                                });
                            }
                        }
                    }

                    // comprobamos que i - 2 no sale fuera de la matriz por ariba y la casiila esta activada
                    if ((i - 2 >= 0) && (matrixTextView[i - 2][j].isEnabled())) {
                        if (esCasillaBuscada(matrixTextView[i - 1][j], R.drawable.casilla_rellena)) {
                            Log.d(TAG, " i=" + i + " | i - 1=" + (i - 1) + "|  j=" + j);
                            if (esCasillaBuscada(matrixTextView[i - 2][j], R.drawable.casilla_vacia)) {
                                Log.d(TAG, " i=" + i + " | i - 2=" + (i - 2) + "|  j=" + j);
                                convertirCasilla_A_Salto_Permitido(matrixTextView[i - 2][j]);
                                // ponemos a la escucha
                                matrixTextView[i - 2][j].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        v.setOnClickListener(null);
                                        realizarSalto();
                                    }
                                });
                            }
                        }
                    }

                    // comprobamos que no sale fuera de martiz por la derecha y esta casilla esta activada
                    if ((j + 2 < matrixTextView[0].length) && (matrixTextView[i][j + 2].isEnabled())) {
                        if (esCasillaBuscada(matrixTextView[i][j + 1], R.drawable.casilla_rellena)) {
                            Log.d(TAG, " i=" + i + " | j=" + j + "|  j + 1=" + (j + 1));
                            if (esCasillaBuscada(matrixTextView[i][j + 2], R.drawable.casilla_vacia)) {
                                Log.d(TAG, " i=" + i + " | j=" + j + "|  j + 2=" + (j + 2));
                                convertirCasilla_A_Salto_Permitido(matrixTextView[i][j + 2]);
                                matrixTextView[i][j + 2].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        v.setOnClickListener(null);
                                        realizarSalto();
                                    }
                                });
                            }
                        }
                    }

                    // comprobamos que no sale fuera de martiz por la izquerda y esta casilla esta activada
                    if ((j - 2 >= 0) && (matrixTextView[i][j - 2].isEnabled())) {
                        if (esCasillaBuscada(matrixTextView[i][j - 1], R.drawable.casilla_rellena)) {
                            Log.d(TAG, " i=" + i + " | j=" + j + "|  j - 1=" + (j - 1));
                            if (esCasillaBuscada(matrixTextView[i][j - 2], R.drawable.casilla_vacia)) {
                                Log.d(TAG, " i=" + i + " | j=" + j + "|  j - 2=" + (j - 2));
                                convertirCasilla_A_Salto_Permitido(matrixTextView[i][j - 2]);
                                matrixTextView[i][j - 2].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        v.setOnClickListener(null);
                                        realizarSalto();
                                    }
                                });
                            }
                        }
                    }
                }
            }
        }

        Log.d(TAG, "salimos detectar casilla selecionada");
    }

    /**
     *
     * Metodo para realizar movimiento
     * consiste en :
     *
     * 1 - casilla selecionada se convierte a vacia
     * 2 - casilla target se convierta a rellena
     * 3 - casilla entre estas dos se convierte en vacia en vacia
     * 4 - se pronuncia sonido del disparo
     * 5 - eliminamos todos Listener posibles
     * 6 - llamamos al metodo 'volverBuscarDetectarSaltar()'
     */
    private void realizarSalto() {
        // animacion para borrar celda

        Log.d(TAG, "entramos realizar salto");
        for (int i = 0; i < matrixTextView.length; i++) {
            for (int j = 0; j < matrixTextView[0].length; j++) {
                if (esCasillaBuscada(matrixTextView[i][j], R.drawable.casilla_selecionada)) {
                    Log.d(TAG, "salto origen: i=" + i + " | j=" + j);
                    // i + 2
                    if ((i + 2 < matrixTextView.length)) {
                        if (esCasillaBuscada(matrixTextView[i + 2][j], R.drawable.target74)) {
                            Log.d(TAG, "destino i=" + (i + 2) + " | j=" + j);
                            // animacion falla
                            // matrixTextView[i + 1][j].startAnimation(animationAlfa);
                            convertirCasilla_A_Vacia(matrixTextView[i][j]);
                            convertirCasilla_A_Vacia(matrixTextView[i + 1][j]);
                            convertirCasilla_A_Rellena(matrixTextView[i + 2][j]);
                            soundPlayerPeg.playZvuk2();
                        }
                    }
                    // destino i - 2
                    if (i - 2 >= 0) {
                        if (esCasillaBuscada(matrixTextView[i - 2][j], R.drawable.target74)) {
                            Log.d(TAG, "destino i=" + (i - 2) + " | j=" + j);
                            // animacion falla
                            // matrixTextView[i - 1][j].startAnimation(animationAlfa);
                            convertirCasilla_A_Vacia(matrixTextView[i][j]);
                            convertirCasilla_A_Vacia(matrixTextView[i - 1][j]);
                            convertirCasilla_A_Rellena(matrixTextView[i - 2][j]);
                            soundPlayerPeg.playZvuk2();
                        }
                    }
                    // si j + 2
                    if (j + 2 < matrixTextView[0].length) {
                        if (esCasillaBuscada(matrixTextView[i][j + 2], R.drawable.target74)) {
                            Log.d(TAG, "destino i=" + i + " | j=" + (j + 2));
                            // animacion falla
                            // matrixTextView[i][j + 1].startAnimation(animationAlfa);
                            convertirCasilla_A_Vacia(matrixTextView[i][j]);
                            convertirCasilla_A_Vacia(matrixTextView[i][j + 1]);
                            convertirCasilla_A_Rellena(matrixTextView[i][j + 2]);
                            soundPlayerPeg.playZvuk2();
                        }
                    }
                    // si j - 2
                    if (j - 2 >= 0) {
                        if (esCasillaBuscada(matrixTextView[i][j - 2], R.drawable.target74)) {
                            Log.d(TAG, "destino i=" + i + " | j=" + (j - 2));
                            // animacion falla
                            // matrixTextView[i][j - 1].startAnimation(animationAlfa);
                            convertirCasilla_A_Vacia(matrixTextView[i][j]);
                            convertirCasilla_A_Vacia(matrixTextView[i][j - 1]);
                            convertirCasilla_A_Rellena(matrixTextView[i][j - 2]);
                            soundPlayerPeg.playZvuk2();
                        }
                    }
                }
            }
        }
        // tengo que eliminar listener de los botones
        listenerNull();
        // volvemos a buscar, detectar boton pulsado y saltar
        volverBuscarDetectarSaltar();
    }


    /**
     * metodo para comprobar una casilla que es igual a la indicada con parametro drawable
     *
     * @param textView
     * @param drawableId
     * @return
     */
    private boolean esCasillaBuscada(TextView textView, int drawableId) {
        return textView.getBackground().getConstantState().equals(getResources().getDrawable(drawableId, null).getConstantState());
    }

    /**
     * metodo para eliminar listener de todos los elementos de matriz
     */
    public void listenerNull(){
        for (int i = 0; i < matrixTextView.length; i++) {
            for (int j = 0; j < matrixTextView[0].length; j++) {
                matrixTextView[i][j].setOnClickListener(null);
            }
        }
    }


    /**
     *  cambiamos fondo del textView a que pasamos como parametro
     * @param textView
     * @param idRes
     */
    private void cambiarBackground(@NonNull TextView textView, int idRes) {
        textView.setBackgroundResource(idRes);
    }


    /**
     * convertimos casilla dada a la casilla vacia
     * @param textView
     */
    public void convertirCasilla_A_Vacia(TextView textView) {
        //Log.d(TAG, "entra convertirCasillaVacia");
        cambiarBackground(textView, R.drawable.casilla_vacia);
        //Log.d(TAG, "sale convertirCasillaVacia");
    }

    /**
     * Metodo para cambiar fondo de la casilla a la casilla jugable
     *
     * @param textView casilla que convertimos a casilla jugable
     */
    public void convertirCasilla_A_Jugable(TextView textView) {
        //Log.d(TAG, "entra convertirCasilla_Jugable");
        cambiarBackground(textView, R.drawable.casilla_jugable);
        //Log.d(TAG, "sale convertirCasilla_Jugable");
    }

    public void convertirCasilla_A_Salto_Permitido(TextView textView) {
        //Log.d(TAG, "entra convertirCasilla salto permitido");
        //cambiarBackground(textView, R.drawable.casilla_salto_permitido);
        cambiarBackground(textView, R.drawable.target74);
        //Log.d(TAG, "sale convertirCasilla salto permitido");
    }

    public void convertirCasilla_A_Selecionada(TextView textView) {
        cambiarBackground(textView, R.drawable.casilla_selecionada);

    }

    private void convertirCasilla_A_Disable(TextView textView) {
        textView.setBackgroundColor(Color.GRAY);
        textView.setEnabled(false);
    }

    public void mostrarToast(String texto) {
        Toast.makeText(getApplicationContext(), texto, Toast.LENGTH_SHORT).show();
    }
    public void convertirCasilla_A_Rellena(TextView textView) {
        //Log.d(TAG, "entra convertirCasillaRellena");
        cambiarBackground(textView, R.drawable.casilla_rellena);
        //Log.d(TAG, "sale convertirCasillaRellena");
    }



    /**
     * metodo para trabajar con menu en el juego
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.gameoptionpeg, menu);

        // creamos help menu
        menu.findItem(R.id.help_menu_item_help_peg).setIntent(
                new Intent(this, HelpActivityPeg.class));
        menu.findItem(R.id.help_menu_item_setting_peg).setIntent(
                new Intent(this, SettingActivityPeg.class));
        menu.findItem(R.id.help_menu_item_score_peg).setIntent(
                new Intent(this, ScoresActivityPeg.class));
        return true;
    }

}// --- end of

