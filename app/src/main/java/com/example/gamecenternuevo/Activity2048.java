package com.example.gamecenternuevo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class Activity2048 extends AppCompatActivity {

    private boolean salir = false;
    GridLayout gridLayout;
    int[] simpleMatriz;
    private GestureDetector mGestureDetector;// declaramos objeto de GestureDetector

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2048);
        setTitle("2048");

        do {
            setTitle("2048");

            estadoInicialSimpleMatriz();

            mGestureDetector = new GestureDetector(this, new EscuchaGestos());
            //creamos objeto de la clase GestureDetector
            // pasamos al constructor context - esta activity y un objeto de la clase que herede de SimpleOnGestureListener
        }
        while (salir);

    }

    // sobreescribimos metodo OnTouchEvent
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    // creamos clase interna que herrede de SimpleOnGestureListener
    class EscuchaGestos extends GestureDetector.SimpleOnGestureListener {

        @Override
        public void onLongPress(MotionEvent e) {
            //Toast.makeText(getApplicationContext(),"una precion larga",Toast.LENGTH_LONG).show();
            super.onLongPress(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            //Toast.makeText(getApplicationContext(),"doble click",Toast.LENGTH_LONG).show();
            return super.onDoubleTap(e);
        }

        @Override
        public void onShowPress(MotionEvent e) {
            //Toast.makeText(getApplicationContext(),"simple click",Toast.LENGTH_LONG).show();
            super.onShowPress(e);
        }


        /**
         * Metodo para detectar movimiento
         *
         * @param e1        MotionEvent
         * @param e2        MotionEvent
         * @param velocityX float
         * @param velocityY float
         * @return boolean
         */
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float ancho = Math.abs(e2.getX() - e1.getX());
            float alto = Math.abs(e2.getY() - e1.getY());

            if (ancho > alto) {
                if (e2.getX() > e1.getX()) {
                    mostrarToast("movimiento a la derecha");
                    derecha();
                    derecha();
                    derecha();
                    actualizarMatrizConNuevoNumeroRandom();
                } else if (e2.getX() < e1.getX()) {
                    mostrarToast("movimiento a la izquerda");
                    izquerda();
                    izquerda();
                    izquerda();
                    actualizarMatrizConNuevoNumeroRandom();
                }
            } else {
                if (e2.getY() > e1.getY()) {
                    mostrarToast("movimiento abajo");
                    abajo();
                    abajo();
                    abajo();
                    actualizarMatrizConNuevoNumeroRandom();

                } else if (e2.getY() < e1.getY()) {
                    mostrarToast("movimiento ariba");
                    arriba();
                    arriba();
                    arriba();
                    actualizarMatrizConNuevoNumeroRandom();

                }
            }
            actualizarTableroSimple();
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }


    /**
     * metodo para generar un numero aleatorio en rango
     *
     * @param rango numero maximo
     * @return numero aleatorio
     */
    public int generarNumRandom(int rango) {
        Random r = new Random();
        return r.nextInt(rango);
    }

    /**
     * metodo que genera  numero 2 con probabilidad 80% o 4 con probabilidad 20%
     *
     * @return numero
     */
    public int generar2or4() {

        int numero = generarNumRandom(100);
        if (numero < 70) {
            return 2;
        } else {
            return 4;
        }
    }

    //------------------generamos estado inicial de la tabla----------------------------------------
    public void estadoInicialSimpleMatriz() {
        // identificamos tablero
        gridLayout = findViewById(R.id.tablero);
        // matriz
        simpleMatriz = new int[gridLayout.getChildCount()];

        int numeroRandom = generarNumRandom(gridLayout.getChildCount()); // generamos numero random

        // colocamos todos valores a 0
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            simpleMatriz[i] = 0;
        }
        // en matriz colocamos un numero '2' o '4'
        simpleMatriz[numeroRandom] = generar2or4();
        actualizarTableroSimple();

    }

    @SuppressLint("SetTextI18n")
    private void actualizarTableroSimple() {
        TextView t0 = findViewById(R.id.textView0);
        t0.setText("" + simpleMatriz[0]);
        pintarCeldaNegro(R.id.textView0);


        TextView t1 = findViewById(R.id.textView1);
        t1.setText("" + simpleMatriz[1]);
        pintarCeldaNegro(R.id.textView1);


        TextView t2 = findViewById(R.id.textView2);
        t2.setText("" + simpleMatriz[2]);
        pintarCeldaNegro(R.id.textView2);


        TextView t3 = findViewById(R.id.textView3);
        t3.setText("" + simpleMatriz[3]);
        pintarCeldaNegro(R.id.textView3);


        TextView t4 = findViewById(R.id.textView4);
        t4.setText("" + simpleMatriz[4]);
        pintarCeldaNegro(R.id.textView4);

        TextView t5 = findViewById(R.id.textView5);
        t5.setText("" + simpleMatriz[5]);
        pintarCeldaNegro(R.id.textView5);

        TextView t6 = findViewById(R.id.textView6);
        t6.setText("" + simpleMatriz[6]);
        pintarCeldaNegro(R.id.textView6);


        TextView t7 = findViewById(R.id.textView7);
        t7.setText("" + simpleMatriz[7]);
        pintarCeldaNegro(R.id.textView7);

        TextView t8 = findViewById(R.id.textView8);
        t8.setText("" + simpleMatriz[8]);
        pintarCeldaNegro(R.id.textView8);

        TextView t9 = findViewById(R.id.textView9);
        t9.setText("" + simpleMatriz[9]);
        pintarCeldaNegro(R.id.textView9);

        TextView t10 = findViewById(R.id.textView10);
        t10.setText("" + simpleMatriz[10]);
        pintarCeldaNegro(R.id.textView10);

        TextView t11 = findViewById(R.id.textView11);
        t11.setText("" + simpleMatriz[11]);
        pintarCeldaNegro(R.id.textView11);

        TextView t12 = findViewById(R.id.textView12);
        t12.setText("" + simpleMatriz[12]);
        pintarCeldaNegro(R.id.textView12);

        TextView t13 = findViewById(R.id.textView13);
        t13.setText("" + simpleMatriz[13]);
        pintarCeldaNegro(R.id.textView13);

        TextView t14 = findViewById(R.id.textView14);
        t14.setText("" + simpleMatriz[14]);
        pintarCeldaNegro(R.id.textView14);

        TextView t15 = findViewById(R.id.textView15);
        t15.setText("" + simpleMatriz[15]);
        pintarCeldaNegro(R.id.textView15);
    }


    //-----------------------generamos un nuevo numero en celda vacia ------------------------------
    public int generarProximoRandom() {
        int numRandom;
        int index;
        // creamos array para guardar index de casillas que pueden participar en random( son ceros )
        ArrayList<Integer> listaCasillas = new ArrayList<>();

        for (int i = 0; i < simpleMatriz.length; i++) {
            if (simpleMatriz[i] == 0) {
                // si es cero añadimos al arraylist
                listaCasillas.add(i);
            }
        }
        // si solo no hay casilla con valor cero terminamos juego
        if (listaCasillas.size() == 0) {
            juegoTerminado();
        }
        // generamos un numero random entre arraylist de casillas con cero
        numRandom = generarNumRandom(listaCasillas.size());
        index = listaCasillas.get(numRandom);
        Log.d("WTF", "numero random" + numRandom + " | index " + index);
        // devolvemos index de la casilla que vamos a aumentar
        return index;
    }

    //-----------------------como terminamos el juego-----------------------------------------------
    private void juegoTerminado() {
        salir = true;
    }

    //------------------------Metodo para mostrar Toast --------------------------------------------
    public void mostrarToast(String mensaje) {
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
    }

    //------------------------añade nuevo numero a la tabla-----------------------------------------
    public void actualizarMatrizConNuevoNumeroRandom() {
        int indexConValorCeroQueVamosAumentar = generarProximoRandom();
        // en la casilla random que tenia valor cero generamos un numero , o 2 o 4
        simpleMatriz[indexConValorCeroQueVamosAumentar] = generar2or4();

    }

    //----------------------Calculo hacia arriba----------------------------------------------------
    private void arriba() {

        for (int i = 4; i < simpleMatriz.length; i++) { // empezamos por la segunda fila, si no sale erro
            if (simpleMatriz[i - 4] == 0) {
                int aux = simpleMatriz[i];
                simpleMatriz[i] = 0;
                simpleMatriz[i - 4] = aux;
            } else {
                if (simpleMatriz[i - 4] == simpleMatriz[i]) {
                    int aux = simpleMatriz[i - 4];
                    simpleMatriz[i] = 0;
                    simpleMatriz[i - 4] = aux * 2;
                }
            }
        }
    }
    //----------------------Fin calculo hacia arriba------------------------------------------------

    //----------------------Calculo hacia abajo-----------------------------------------------------
    private void abajo() {
        abajoCuatro();
        abajoCeros();
        abajoTriples();
        abajoDobles();
    }

    private void abajoCeros() {
        for (int i = 0; i < simpleMatriz.length - 4; i++) {
            if (simpleMatriz[i + 4] == 0) {
                int aux = simpleMatriz[i];
                simpleMatriz[i] = 0;
                simpleMatriz[i + 4] = aux;
            }
        }
    }

    private void abajoCuatro() {
        for (int i = 0; i < 3; i++) {
            if (simpleMatriz[i] == simpleMatriz[i + 4] && simpleMatriz[i] == simpleMatriz[i + 8] && simpleMatriz[i] == simpleMatriz[i + 12]) {
                int aux = simpleMatriz[i];
                simpleMatriz[i] = 0;
                simpleMatriz[i + 4] = 0;
                simpleMatriz[i + 8] = 0;
                simpleMatriz[i + 12] = aux * 4;
            }
        }
    }

    private void abajoTriples() {
        for (int i = 0; i < simpleMatriz.length - 8; i++) {
            if (simpleMatriz[i] == simpleMatriz[i + 4] && simpleMatriz[i] == simpleMatriz[i + 8]) {
                int aux = simpleMatriz[i];
                simpleMatriz[i] = 0;
                simpleMatriz[i + 8] = aux * 2;
            }
        }
    }

    private void abajoDobles() {

        for (int i = 15; i > 3; i--) {
            if (simpleMatriz[i] == simpleMatriz[i - 4]) {
                int aux = simpleMatriz[i - 4];
                simpleMatriz[i - 4] = 0;
                simpleMatriz[i] = aux * 2;
            }
        }

//        for (int i = 0; i < simpleMatriz.length - 4; i++) {
//            if (simpleMatriz[i] == simpleMatriz[i + 4]) {
//                int aux = simpleMatriz[i];
//                simpleMatriz[i] = 0;
//                simpleMatriz[i + 4] = aux * 2;
//            }
//        }
    }

    //----------------------fin calculo hacia abajo-------------------------------------------------
    //
    //
    //
    // -----------------------calculo para mover a la derecha---------------------------------------
    private void derechaPrimeraFila() {
        derechaPrimeraFilaCuatro();
        derechaPrimeraFilaCeros();
        derechaPrimeraFilaTriples();
        derechaPrimeraFilaDobles();

    }

    private void derechaPrimeraFilaCuatro() {
        if (simpleMatriz[0] == simpleMatriz[1] && simpleMatriz[0] == simpleMatriz[2] && simpleMatriz[0] == simpleMatriz[3]) {
            int aux = simpleMatriz[0];
            simpleMatriz[0] = 0;
            simpleMatriz[1] = 0;
            simpleMatriz[2] = 0;
            simpleMatriz[3] = aux * 4;
        }
    }

    private void derechaPrimeraFilaCeros() {
        for (int i = 0; i < 3; i++) {
            if (simpleMatriz[i + 1] == 0) {
                int aux = simpleMatriz[i];
                simpleMatriz[i] = 0;
                simpleMatriz[i + 1] = aux;
            }
        }
    }

    private void derechaPrimeraFilaTriples() {
        for (int i = 0; i < 1; i++) {
            if (simpleMatriz[i] == simpleMatriz[i + 1] && simpleMatriz[i] == simpleMatriz[i + 2]) {
                int aux = simpleMatriz[i];
                simpleMatriz[i] = 0;
                simpleMatriz[i + 2] = aux * 2;
            }
        }
    }

    private void derechaPrimeraFilaDobles() {

        for (int i = 3; i > 0; i--) {
            if (simpleMatriz[i] == simpleMatriz[i - 1]) {
                int aux = simpleMatriz[i - 1];
                simpleMatriz[i - 1] = 0;
                simpleMatriz[i] = aux * 2;
            }
        }
    }

    //----------derecha segunda fila
    private void derechaSegundaFila() {
        derechaSegundaFilaCuatro();
        derechaSegundafilaCeros();
        derechaSegundaFilaTriples();
        derechaSegundaFilaDobles();

    }

    private void derechaSegundaFilaCuatro() {
        if (simpleMatriz[4] == simpleMatriz[5] && simpleMatriz[4] == simpleMatriz[6] && simpleMatriz[4] == simpleMatriz[7]) {
            int aux = simpleMatriz[4];
            simpleMatriz[4] = 0;
            simpleMatriz[5] = 0;
            simpleMatriz[6] = 0;
            simpleMatriz[7] = aux * 4;
        }
    }

    private void derechaSegundafilaCeros() {
        for (int i = 4; i < 7; i++) {
            if (simpleMatriz[i + 1] == 0) {
                int aux = simpleMatriz[i];
                simpleMatriz[i] = 0;
                simpleMatriz[i + 1] = aux;
            }
        }
    }

    private void derechaSegundaFilaTriples() {
        for (int i = 4; i < 6; i++) {
            if (simpleMatriz[i] == simpleMatriz[i + 1] && simpleMatriz[i] == simpleMatriz[i + 2]) {
                int aux = simpleMatriz[i];
                simpleMatriz[i] = 0;
                simpleMatriz[i + 2] = aux * 2;
            }
        }
    }

    private void derechaSegundaFilaDobles() {

        for (int i = 7; i > 4; i--) {
            if (simpleMatriz[i] == simpleMatriz[i - 1]) {
                int aux = simpleMatriz[i - 1];
                simpleMatriz[i - 1] = 0;
                simpleMatriz[i] = aux * 2;
            }
        }
    }

    // -------------derecha tercera fila
    public void derechaTerceraFila() {
        derechaTerceraFilaCuatro();
        derechaTerceraFilaCeros();
        derechaTerceraFilaTriple();
        derechaTerceraFilaDobles();

    }

    private void derechaTerceraFilaCuatro() {
        if (simpleMatriz[8] == simpleMatriz[9] && simpleMatriz[8] == simpleMatriz[10] && simpleMatriz[8] == simpleMatriz[11]) {
            int aux = simpleMatriz[8];
            simpleMatriz[8] = 0;
            simpleMatriz[9] = 0;
            simpleMatriz[10] = 0;
            simpleMatriz[11] = aux * 4;
        }
    }

    private void derechaTerceraFilaCeros() {
        for (int i = 8; i < 11; i++) {
            if (simpleMatriz[i + 1] == 0) {
                int aux = simpleMatriz[i];
                simpleMatriz[i] = 0;
                simpleMatriz[i + 1] = aux;
            }
        }
    }

    private void derechaTerceraFilaTriple() {
        for (int i = 8; i < 10; i++) {
            if (simpleMatriz[i] == simpleMatriz[i + 1] && simpleMatriz[i] == simpleMatriz[i + 2]) {
                int aux = simpleMatriz[i];
                simpleMatriz[i] = 0;
                simpleMatriz[i + 2] = aux * 2;
            }
        }
    }

    private void derechaTerceraFilaDobles() {

        for (int i = 11; i > 8; i--) {
            if (simpleMatriz[i] == simpleMatriz[i - 1]) {
                int aux = simpleMatriz[i - 1];
                simpleMatriz[i - 1] = 0;
                simpleMatriz[i] = aux * 2;
            }
        }
    }


    //--------------derecha cuarta fila
    private void derechaCuartaFila() {
        derechaCuartaFilaCuatro();
        derechaCuartaFilaCeros();
        derechaCuartaFilaTriples();
        derechaCuartaFilaDobles();

    }

    private void derechaCuartaFilaCuatro() {
        if (simpleMatriz[12] == simpleMatriz[13] && simpleMatriz[12] == simpleMatriz[14] && simpleMatriz[12] == simpleMatriz[15]) {
            int aux = simpleMatriz[12];
            simpleMatriz[12] = 0;
            simpleMatriz[13] = 0;
            simpleMatriz[14] = 0;
            simpleMatriz[15] = aux * 4;
        }
    }

    private void derechaCuartaFilaCeros() {
        for (int i = 12; i < 15; i++) {
            if (simpleMatriz[i + 1] == 0) {
                int aux = simpleMatriz[i];
                simpleMatriz[i] = 0;
                simpleMatriz[i + 1] = aux;
            }
        }
    }

    private void derechaCuartaFilaTriples() {
        for (int i = 12; i < 14; i++) {
            if (simpleMatriz[i] == simpleMatriz[i + 1] && simpleMatriz[i] == simpleMatriz[i + 2]) {
                int aux = simpleMatriz[i];
                simpleMatriz[i] = 0;
                simpleMatriz[i + 2] = aux;
            }
        }
    }

    private void derechaCuartaFilaDobles() {

        for (int i = 15; i > 12; i--) {
            if (simpleMatriz[i] == simpleMatriz[i - 1]) {
                int aux = simpleMatriz[i - 1];
                simpleMatriz[i - 1] = 0;
                simpleMatriz[i] = aux * 2;
            }
        }
    }

    public void derecha() {
        derechaPrimeraFila();
        derechaSegundaFila();
        derechaTerceraFila();
        derechaCuartaFila();
    }
    //----------------------fin calculo a la derecha------------------------------------------------

    //--------------------Calculo para mover a la izquerda-----------------------------------------
    public void izquerdaPrimeraFila() {
        izquerdaPrimeraFilaCuatro();
        izquerdaPrimeraFilaCeros();
        izquerdaPrimeraFilaTriples();
        izquerdaPrimeraFilaDobles();

    }

    private void izquerdaPrimeraFilaCuatro() {
        if (simpleMatriz[0] == simpleMatriz[1] && simpleMatriz[0] == simpleMatriz[2] && simpleMatriz[0] == simpleMatriz[3]) {
            int aux = simpleMatriz[3];
            simpleMatriz[0] = aux * 4;
            simpleMatriz[1] = 0;
            simpleMatriz[2] = 0;
            simpleMatriz[3] = 0;
        }
    }

    private void izquerdaPrimeraFilaCeros() {
        for (int i = 3; i > 0; i--) {
            if (simpleMatriz[i - 1] == 0) {
                int aux = simpleMatriz[i];
                simpleMatriz[i] = 0;
                simpleMatriz[i - 1] = aux;
            }
        }
    }

    private void izquerdaPrimeraFilaTriples() {
        for (int i = 3; i > 1; i--) {
            if (simpleMatriz[i] == simpleMatriz[i - 1] && simpleMatriz[i] == simpleMatriz[i - 2]) {
                int aux = simpleMatriz[i];
                simpleMatriz[i] = 0;
                simpleMatriz[i - 2] = aux * 2;
            }
        }
    }

    private void izquerdaPrimeraFilaDobles() {
        for (int i = 3; i > 0; i--) {
            if (simpleMatriz[i] == simpleMatriz[i - 1]) {
                int aux = simpleMatriz[i];
                simpleMatriz[i] = 0;
                simpleMatriz[i - 1] = aux * 2;
            }
        }
    }

    // ---------------- izquerda segunda fila
    public void izquerdaSegundaFila() {
        izquerdaSegundaFilaCuatro();
        izquerdaSegundaFilaCeros();
        izquerdaSegundaFilaTriples();
        izquerdaSegundaFilaDobles();

    }

    private void izquerdaSegundaFilaCuatro() {
        if (simpleMatriz[4] == simpleMatriz[5] && simpleMatriz[4] == simpleMatriz[6] && simpleMatriz[4] == simpleMatriz[7]) {
            int aux = simpleMatriz[7];
            simpleMatriz[4] = aux * 4;
            simpleMatriz[5] = 0;
            simpleMatriz[6] = 0;
            simpleMatriz[7] = 0;
        }
    }

    private void izquerdaSegundaFilaCeros() {
        for (int i = 7; i > 4; i--) {
            if (simpleMatriz[i - 1] == 0) {
                int aux = simpleMatriz[i];
                simpleMatriz[i] = 0;
                simpleMatriz[i - 1] = aux;
            }
        }
    }

    private void izquerdaSegundaFilaTriples() {
        for (int i = 7; i > 5; i--) {
            if (simpleMatriz[i] == simpleMatriz[i - 1] && simpleMatriz[i] == simpleMatriz[i - 2]) {
                int aux = simpleMatriz[i];
                simpleMatriz[i] = 0;
                simpleMatriz[i - 2] = aux * 2;
            }
        }
    }

    private void izquerdaSegundaFilaDobles() {
        for (int i = 7; i > 4; i--) {
            if (simpleMatriz[i] == simpleMatriz[i - 1]) {
                int aux = simpleMatriz[i];
                simpleMatriz[i] = 0;
                simpleMatriz[i - 1] = aux * 2;
            }
        }
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
        getMenuInflater().inflate(R.menu.gameoption2048, menu);
        menu.findItem(R.id.help_menu_item).setIntent(
                new Intent(this, HelpActivity2048.class));
        menu.findItem(R.id.setting_menu_item).setIntent(
                new Intent(this, SettingActivity2048.class));
        return true;
    }

    //----izquerda tercera fila
    public void izquerdaTerceraFila() {
        izquerdaTerceraFilaCuatro();
        izquerdaTerceraFilaCeros();
        izquerdaTerceraFilaTriples();
        izquerdaTerceraFilaDobles();

    }

    private void izquerdaTerceraFilaCuatro() {
        if (simpleMatriz[8] == simpleMatriz[9] && simpleMatriz[8] == simpleMatriz[10] && simpleMatriz[8] == simpleMatriz[11]) {
            int aux = simpleMatriz[11];
            simpleMatriz[8] = aux * 4;
            simpleMatriz[9] = 0;
            simpleMatriz[10] = 0;
            simpleMatriz[11] = 0;
        }
    }

    private void izquerdaTerceraFilaCeros() {
        for (int i = 11; i > 8; i--) {
            if (simpleMatriz[i - 1] == 0) {
                int aux = simpleMatriz[i];
                simpleMatriz[i] = 0;
                simpleMatriz[i - 1] = aux;
            }
        }
    }

    private void izquerdaTerceraFilaTriples() {
        for (int i = 11; i > 9; i--) {
            if (simpleMatriz[i] == simpleMatriz[i - 1] && simpleMatriz[i] == simpleMatriz[i - 2]) {
                int aux = simpleMatriz[i];
                simpleMatriz[i] = 0;
                simpleMatriz[i - 2] = aux * 2;
            }
        }
    }

    private void izquerdaTerceraFilaDobles() {
        for (int i = 11; i > 8; i--) {
            if (simpleMatriz[i] == simpleMatriz[i - 1]) {
                int aux = simpleMatriz[i];
                simpleMatriz[i] = 0;
                simpleMatriz[i - 1] = aux * 2;
            }
        }
    }

    //-----------izquerda cuarta fila
    public void izquerdaCuartaFila() {
        izquerdaCuartaFilaCuatro();
        izquerdacuartaFilaCeros();
        izquerdaCuartaFilaTriples();
        izquerdaCuartaFilaDobles();

    }

    private void izquerdaCuartaFilaCuatro() {
        if (simpleMatriz[12] == simpleMatriz[13] && simpleMatriz[12] == simpleMatriz[14] && simpleMatriz[12] == simpleMatriz[15]) {
            int aux = simpleMatriz[15];
            simpleMatriz[12] = aux * 4;
            simpleMatriz[13] = 0;
            simpleMatriz[14] = 0;
            simpleMatriz[15] = 0;
        }
    }

    private void izquerdacuartaFilaCeros() {
        for (int i = 15; i > 12; i--) {
            if (simpleMatriz[i - 1] == 0) {
                int aux = simpleMatriz[i];
                simpleMatriz[i] = 0;
                simpleMatriz[i - 1] = aux;
            }
        }
    }

    private void izquerdaCuartaFilaTriples() {
        for (int i = 15; i > 13; i--) {
            if (simpleMatriz[i] == simpleMatriz[i - 1] && simpleMatriz[i] == simpleMatriz[i - 2]) {
                int aux = simpleMatriz[i];
                simpleMatriz[i] = 0;
                simpleMatriz[i - 2] = aux * 2;
            }
        }
    }

    private void izquerdaCuartaFilaDobles() {
        for (int i = 15; i > 12; i--) {
            if (simpleMatriz[i] == simpleMatriz[i - 1]) {
                int aux = simpleMatriz[i];
                simpleMatriz[i] = 0;
                simpleMatriz[i - 1] = aux * 2;
            }
        }
    }

    public void izquerda() {
        izquerdaPrimeraFila();
        izquerdaSegundaFila();
        izquerdaTerceraFila();
        izquerdaCuartaFila();
    }

//-------------------fin calculo a la izquerda------------------------------------------------------


    public void pintar(int identificador) {
        TextView t = findViewById(identificador);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            if (t.getId() == identificador) {
                t.setBackgroundResource(R.drawable.destacado);
            } else {
                t.setBackgroundResource(R.drawable.celda);
            }
        }
    }

    public void pintarCeldaNegro(int identificador) {
        TextView celda = findViewById(identificador);
        celda.setBackgroundResource(R.drawable.celda);
    }

    public void pintarCeldaDestacada(int identificador) {
        TextView celda = findViewById(identificador);
        celda.setBackgroundResource(R.drawable.destacado);
    }

}