package com.example.gamecenternuevo;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ActivityPegSolitaire extends AppCompatActivity {

    // creamos un objeto de SoundPlayer
    private static SoundPlayer soundPlayer;


    private static final String TAG = "JUEGO";
    public TextView[][] matrixTextView;


    public boolean terminado;

    // public static boolean booleanCasillaEligida = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peg_solitaire);
        setTitle("Peg Solitaire");

        // iniciamos soundPlayer
        soundPlayer = new SoundPlayer(this);

        terminado = false;

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
                matrixTextView[i][j].setBackgroundResource(R.drawable.casilla_rellena);
                matrixTextView[i][j].setText(i + " " + j);
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
                            }
                        }
                    }
                }
            }
        }
        Log.d(TAG, "sale buscar todas casillas jugables  --- V2 ---");
    }

    private void animarRotate(TextView textView) {

        Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.scale2);
        textView.startAnimation(animation2);

    }

    /**
     *
     */
    public void listenerParaCasillasJugablesConvertirSelecionada() {
        for (int i = 0; i < matrixTextView.length; i++) {
            for (int j = 0; j < matrixTextView[0].length; j++) {
                if (matrixTextView[i][j].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.casilla_jugable, null).getConstantState())) {
                    matrixTextView[i][j].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.setBackgroundResource(R.drawable.casilla_selecionada);
                            soundPlayer.playRecoil();
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
                                        realizarSaltoNuevo();
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
                                        realizarSaltoNuevo();
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
                                        realizarSaltoNuevo();
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
                                        realizarSaltoNuevo();
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
     * Metodo para convertir
     * casilla selecionada a vacia
     * casilla objetivo a rellena
     * casilla entremedio en vacia
     */
    private void realizarSaltoNuevo() {
        Log.d(TAG, "entramos realizar salto");
        for (int i = 0; i < matrixTextView.length; i++) {
            for (int j = 0; j < matrixTextView[0].length; j++) {
                if (esCasillaBuscada(matrixTextView[i][j], R.drawable.casilla_selecionada)) {
                    Log.d(TAG, "salto origen: i=" + i + " | j=" + j);
                    // origen i j , destino i+2
                    if ((i + 2 < matrixTextView.length)) {
                        if (esCasillaBuscada(matrixTextView[i + 2][j], R.drawable.target75)) {
                            Log.d(TAG, "destino i=" + (i + 2) + " | j=" + j);
                            convertirCasilla_A_Vacia(matrixTextView[i + 1][j]);
                            convertirCasilla_A_Vacia(matrixTextView[i][j]);
                            convertirCasilla_A_Rellena(matrixTextView[i + 2][j]);
                            soundPlayer.playShoot();
                        }
                    }
                    // destino i - 2
                    if (i - 2 >= 0) {
                        if (esCasillaBuscada(matrixTextView[i - 2][j], R.drawable.target75)) {
                            Log.d(TAG, "destino i=" + (i - 2) + " | j=" + j);
                            convertirCasilla_A_Vacia(matrixTextView[i - 1][j]);
                            convertirCasilla_A_Vacia(matrixTextView[i][j]);
                            convertirCasilla_A_Rellena(matrixTextView[i - 2][j]);
                            soundPlayer.playShoot();
                        }
                    }
                    // si j + 2
                    if (j + 2 < matrixTextView[0].length) {
                        if (esCasillaBuscada(matrixTextView[i][j + 2], R.drawable.target75)) {
                            Log.d(TAG, "destino i=" + i + " | j=" + (j + 2));
                            convertirCasilla_A_Vacia(matrixTextView[i][j]);
                            convertirCasilla_A_Vacia(matrixTextView[i][j + 1]);
                            convertirCasilla_A_Rellena(matrixTextView[i][j + 2]);
                            soundPlayer.playShoot();
                        }
                    }
                    // si j - 2
                    if (j - 2 >= 0) {
                        if (esCasillaBuscada(matrixTextView[i][j - 2], R.drawable.target75)) {
                            Log.d(TAG, "destino i=" + i + " | j=" + (j - 2));
                            convertirCasilla_A_Vacia(matrixTextView[i][j]);
                            convertirCasilla_A_Vacia(matrixTextView[i][j - 1]);
                            convertirCasilla_A_Rellena(matrixTextView[i][j - 2]);
                            soundPlayer.playShoot();
                        }
                    }
                }
            }
        }
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



    private void cambiarBackground(@NonNull TextView textView, int idRes) {
        textView.setBackgroundResource(idRes);
    }



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
        cambiarBackground(textView, R.drawable.target75);
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
        menu.findItem(R.id.help_menu_item).setIntent(
                new Intent(this, HelpActivityPeg.class));
        menu.findItem(R.id.setting_menu_item).setIntent(
                new Intent(this, SettingActivityPeg.class));
        return true;
    }

}

