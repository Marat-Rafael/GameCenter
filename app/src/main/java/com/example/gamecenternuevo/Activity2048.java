package com.example.gamecenternuevo;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import androidx.constraintlayout.widget.ConstraintLayout;


/**
 * Para detectar movimientos hay que implementar OnTouchListener
 */
public class Activity2048 extends AppCompatActivity implements View.OnTouchListener, GestureDetector.OnGestureListener {
    // implementando interfaz OnTouchListener nos obliga sobreescribir onTouch metod
    // implementando interfaz GestureDetector nos obliga sobreescribir sus metodos

    private TextView TextViewpuntuacion;
    private int puntuacionJuegoActual;
    private int puntuacionMaxima;
    private int puntuacionMaximaDelJugadorActual;

    String usuarioActual;
    final String TAG = "juego";
    TextView auxTextView;
    private boolean gameOver = false;
    GestureDetector gestureDetector;
    int[][] matrizValores;
    private ConstraintLayout constraintLayout;
    private static SoundPlayer soundPlayer2048;
    TextView textView_usuarioActual;
    TextView textView_puntuacionMax;
    UsuariosHelper helper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2048);
        // creamos un objeto de gestureDetector
        gestureDetector = new GestureDetector(this, this);
        // detectamos layout con tablero
        constraintLayout = findViewById(R.id.layout_activity2048);
        // tenemos que polocar OnTouchListener a la pantalla
        constraintLayout.setOnTouchListener(this);
        soundPlayer2048 = new SoundPlayer(this);

        textView_usuarioActual = findViewById(R.id.actividad_2048_usuarioActual);
        textView_puntuacionMax = findViewById(R.id.actividad_2048_max_puntuacion);

        // cojemos datos del usuario actual
        usuarioActual = getIntent().getStringExtra("USUARIO");
        helper = new UsuariosHelper(this);
        db = helper.getReadableDatabase();

        // sacamos campo puntuacion maxima de BBDD
        puntuacionMaxima = helper.buscarPuntuacionMax2048(db);

        // cojemos puntuacion maxima del jugador actual
        puntuacionMaximaDelJugadorActual = helper.buscarPuntuacion2048DelUsuario(usuarioActual,db);


        // insertamos valores del usuario actual y puntuacion maxima en textView
        textView_usuarioActual.setText(usuarioActual);
        textView_puntuacionMax.setText("Puntuacion Maxima: "+ puntuacionMaxima);

        animarBackground2048();

        juego();

        Log.d(TAG, "salimos onCreate");
    }

    /**
     * Metodo para animar Background
     */
    private void animarBackground2048() {
        // ----------------animamos background del constraint layout -------------------------------
        ConstraintLayout constraintLayout = findViewById(R.id.layout_activity2048);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(2500);
        animationDrawable.start();
        //-------------------------------fin animacion----------------------------------------------
    }

    public void juego() {

        // creamos matriz de valores int[][]
        crearMatrizValores();
        // pintamos tablero con casillas segun valor de casilla
        repintarValoresEnCasillas();
        // creamos primer numaro random
        crearRandom2();

    }

    public void mostrarToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    /**
     * metodo del interfaz OnTouchListener, tenemos que paras le obligatoriamente un objeto
     * de gestiureDetector creado anteriormente
     *
     * @param view
     * @param motionEvent
     * @return tiene que devolver true
     */
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        gestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        // Log.d(TAG, "onDown");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
        // Log.d(TAG, "onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        // Log.d(TAG, "onSingleTapUp");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        // Log.d(TAG, "onScroll");
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        // Log.d(TAG, "onLongPress");
    }

    /**
     * @param motionEvent  punto del inicio
     * @param motionEvent1 punto del final
     * @param v            float
     * @param v1           float
     * @return
     */
    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {

        // calculamos ancho del gesto
        // en eje X , desde punto final restamos punto de inicio
        float ancho = Math.abs(motionEvent1.getX() - motionEvent.getX());

        // calculamos alto del gesto
        // en eje Y , desde punto final restamos punto de inicio
        float alto = Math.abs(motionEvent1.getY() - motionEvent.getY());

        if (ancho > alto) {
            // si ancho es mas grande que  alto, pensamos que es un movimiento horizontal

            if (motionEvent1.getX() > motionEvent.getX()) {
                // si EJE X punto final es mas grande que punto inicial consideramos que es  movimiento a la derecha
                moverDerechaMatrizValores();
                //soundPlayer2048.playRifle();
                soundPlayer2048.playSbor();

                Log.d(TAG, "onFling a derecha");

                // si punto final es menor en eje X , es movimiento Izquerdo
            } else if (motionEvent1.getX() < motionEvent.getX()) {
                moverIzquerdaMatrizValores();
                //soundPlayer2048.playRifle();
                soundPlayer2048.playSbor();
                Log.d(TAG, " onFling a izquerda");

            }
            // si alto mas grande que ancho
        } else {
            // si
            if (motionEvent1.getY() > motionEvent.getY()) {
                Log.d(TAG, "onFling abajo");
                //soundPlayer2048.playRifle();
                soundPlayer2048.playSbor();
                moverAbajoMatrizValores();


            } else if (motionEvent1.getY() < motionEvent.getY()) {
                Log.d(TAG, "onFling arriba");
                //soundPlayer2048.playRifle();
                soundPlayer2048.playSbor();
                moverArribaMatrizValores();
            }
        }

        return false;
    }


    /**
     *
     */
    public void crearMatrizValores() {
        matrizValores = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };
    }

    /**
     * Metodo para mostrar valores de matriz int[][] en el tablero
     */
    public void repintarValoresEnCasillas() {

        for (int i = 0; i < matrizValores.length; i++) {
            for (int j = 0; j < matrizValores[0].length; j++) {
                String textViewID = "tv_" + i + "" + j;
                // OJO !!! usamos getResouces().getIdentifier() !!!!!
                int resId = getResources().getIdentifier(textViewID, "id", getPackageName());
                // Log.d(TAG, "" + resId);
                auxTextView = findViewById(resId);
                // auxTextView.setText(i+""+j);

                auxTextView.setText("" + matrizValores[i][j]);
                // colocamos colores segun valor
                if (matrizValores[i][j] == 0) {
                    auxTextView.setBackground(getResources().getDrawable(R.drawable.celda_2048_0, null));
                } else if (matrizValores[i][j] == 2) {
                    auxTextView.setBackground(getResources().getDrawable(R.drawable.celda_2048_2, null));
                } else if (matrizValores[i][j] == 4) {
                    auxTextView.setBackground(getResources().getDrawable(R.drawable.celda_2048_4, null));
                } else if (matrizValores[i][j] == 8) {
                    auxTextView.setBackground(getResources().getDrawable(R.drawable.celda_2048_8, null));
                } else if (matrizValores[i][j] == 16) {
                    auxTextView.setBackground(getResources().getDrawable(R.drawable.celda_2048_16, null));
                } else if (matrizValores[i][j] == 32) {
                    auxTextView.setBackground(getResources().getDrawable(R.drawable.celda_2048_32, null));
                } else if (matrizValores[i][j] == 64) {
                    auxTextView.setBackground(getResources().getDrawable(R.drawable.celda_2048_64, null));
                } else if (matrizValores[i][j] == 128) {
                    auxTextView.setBackground(getResources().getDrawable(R.drawable.celda_2048_128, null));
                } else if (matrizValores[i][j] == 256) {
                    auxTextView.setBackground(getResources().getDrawable(R.drawable.celda_2048_256, null));
                } else if (matrizValores[i][j] == 512) {
                    auxTextView.setBackground(getResources().getDrawable(R.drawable.celda_2048_512, null));
                } else if (matrizValores[i][j] == 1024) {
                    auxTextView.setBackground(getResources().getDrawable(R.drawable.celda_2048_1024, null));
                } else if (matrizValores[i][j] == 2048) {
                    auxTextView.setBackground(getResources().getDrawable(R.drawable.celda_2048_2048, null));
                } else if (matrizValores[i][j] == 4096) {
                    auxTextView.setBackground(getResources().getDrawable(R.drawable.celda_2048_new, null));
                } else {
                    auxTextView.setBackground(getResources().getDrawable(R.drawable.celda_2048_2048, null));
                }
                TextViewpuntuacion = findViewById(R.id.tv_puntuacion_2048_campoNumero);
                TextViewpuntuacion.setText(""+ puntuacionJuegoActual);

            }
        }
    }



    /**
     * Metodo para generar un numero 2 o 4 en una casilla random
     * si esta casilla esta ocupado por algun numero diferente de cero volvemos a generar
     */
    private void crearRandom2() {
        boolean encontradoEspacioLibre = false;
        int num1;
        int num2;
        do {
            Random random1 = new Random();
            num1 = random1.nextInt(matrizValores.length);
            //Log.d(TAG, "primer random = " + num1);
            Random random2 = new Random();
            num2 = random2.nextInt(matrizValores[0].length);
            //Log.d(TAG, "segundo random = " + num2);
            //Log.d(TAG, "valor actual de casilla es=" + matrizValores[num1][num2]);
            if (matrizValores[num1][num2] == 0) {
                matrizValores[num1][num2] = generar2o4();
                encontradoEspacioLibre = true;
            }
        } while (!encontradoEspacioLibre);
        // pintamos todas casillas, valor y color segun valor
        repintarValoresEnCasillas();

        // buscamos textView y pintamos en otro color
        String textViewID = "tv_" + num1 + "" + num2;
        // OJO !!! usamos getResouces().getIdentifier() !!!!!
        int resId = getResources().getIdentifier(textViewID, "id", getPackageName());

        // destacamos celda nueva pintando en otro color
        TextView textView = findViewById(resId);
        textView.setBackgroundResource(R.drawable.celda_2048_new);
    }

    /**
     * metodo que genera  numero 2 con probabilidad 90%
     * o 4 con probabilidad 10%
     *
     * @return numero
     */
    public int generar2o4() {
        Random random = new Random();
        int numero = random.nextInt(100);
        if (numero < 90) {
            return 2;
        } else {
            return 4;
        }
    }

    /**
     * Comprobamos numero de casillas ocupadas
     */
    public void comprobarGameOver() {
        // tiene que empezar por uno, porque ya tenemos generado un numero random en el tablero
        int num = 1;
        for (int i = 0; i < matrizValores.length; i++) {
            for (int j = 0; j < matrizValores[0].length; j++) {
                if (matrizValores[i][j] != 0) {
                    num++;
                }
            }
        }

        Log.d(TAG, "LOG: numero de casillas con numeros es "+num);
        if (num == 16) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("perdido");
            builder.setIcon(R.drawable.eft_250);
            builder.setMessage("Has perdido partida");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(getApplicationContext(), MenuActivity2048.class);
                    //

                    Activity2048.this.finish();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }


    private void comprobarSiHayMovimientosDisponibles(){
        boolean hay = true;
        for (int i = 0; i < matrizValores.length; i++) {
            for (int j = 0; j < matrizValores[0].length; j++) {
                if(matrizValores[i][j] == matrizValores[i + 1][j]){

                }
            }
        }

    }

//    /**
//     * AQUI ALGO FALLA ???
//     * metodo nos devuelve coordenada de una casilla random
//     *
//     * @return
//     */
//    private int[] determinarUnNumeroRandomParaNuevaCasilla() {
//        int numerosCero = 0;
//        // creamos array para guardar dos numeros I y J
//        int[] coordenadas = new int[2];
//        // determinamos cuantas casillas vacias hay
//        for (int i = 0; i < matrizValores.length; i++) {
//            for (int j = 0; j < matrizValores[0].length; j++) {
//                if (matrizValores[i][j] == 0) {
//                    numerosCero++;
//                }
//            }
//        }
//        Random random_I = new Random();
//        Random random_J = new Random();
//        int numero_en_fila_I = random_I.nextInt(numerosCero);
//        int numero_en_fila_J = random_J.nextInt(numerosCero);
//
//        // recorremos segunda vez matriz para determinar esta casilla
//        int contador = 0;
//        for (int i = 0; i < matrizValores.length; i++) {
//            for (int j = 0; j < matrizValores[0].length; j++) {
//                if (matrizValores[i][j] == 0) {
//                    contador++;
//                    if (contador == numero_en_fila_I) {
//                        coordenadas[0] = i;
//                    }
//                    if (contador == numero_en_fila_J) {
//                        coordenadas[1] = j;
//                    }
//                }
//            }
//        }
//        return coordenadas;
//    } // end of determinarUnNumeroRandomParaNuevaCasilla()

//    /**
//     * metodo que pasa a matriz de valores nuevo numero generado
//     * indicando coordenadas de I y J
//     */
//    private void generarNuevoNumeroEnTablero() {
//        int[] arraycoordenadas = determinarUnNumeroRandomParaNuevaCasilla();
//
//        int coordenada_I = arraycoordenadas[0];
//        int coordenada_J = arraycoordenadas[1];
//        int numeroRandom = generar2o4();
//        matrizValores[coordenada_I][coordenada_J] = numeroRandom;
//
//    }


    /**
     * ABAJO !!!
     * metodo para mover numeros de matriz abajo y sumar iguales
     */
    private void moverAbajoMatrizValores() {

        //  identificamos  4 arrays simple con las que trabajamos , son columnas del matriz
        int[] arrayColumna1 = new int[]{
                matrizValores[0][0],
                matrizValores[1][0],
                matrizValores[2][0],
                matrizValores[3][0],
        };
        int[] arrayColumna2 = new int[]{
                matrizValores[0][1],
                matrizValores[1][1],
                matrizValores[2][1],
                matrizValores[3][1],
        };
        int[] arrayColumna3 = new int[]{
                matrizValores[0][2],
                matrizValores[1][2],
                matrizValores[2][2],
                matrizValores[3][2],
        };
        int[] arrayColumna4 = new int[]{
                matrizValores[0][3],
                matrizValores[1][3],
                matrizValores[2][3],
                matrizValores[3][3],
        };
        //  a cada array simple aplicamos metodo manejarArray();
        int[] columna1 = manejarArrayDerecha(arrayColumna1);
        int[] columna2 = manejarArrayDerecha(arrayColumna2);
        int[] columna3 = manejarArrayDerecha(arrayColumna3);
        int[] columna4 = manejarArrayDerecha(arrayColumna4);

        // pasamos
        pasarDatosDeColumnasAlMatriz(columna1, columna2, columna3, columna4);

        //
        comprobarGameOver();

        // generamos un nuevo numero en el tablero
        //generarNuevoNumeroEnTablero();
        crearRandom2();

        // pasamos datos de matriz int[][] a layout, para mostrar
        // repintarValoresEnCasillas();

    }// end moverAbajoMatrizValores()

    /**
     * ARRIBA !!!
     * metodo para mover numeros de matriz abajo y sumar iguales
     */
    private void moverArribaMatrizValores() {

        //  identificamos  4 arrays simple con las que trabajamos , son columnas del matriz
        int[] arrayColumna1 = new int[]{
                matrizValores[0][0],
                matrizValores[1][0],
                matrizValores[2][0],
                matrizValores[3][0],
        };
        int[] arrayColumna2 = new int[]{
                matrizValores[0][1],
                matrizValores[1][1],
                matrizValores[2][1],
                matrizValores[3][1],
        };
        int[] arrayColumna3 = new int[]{
                matrizValores[0][2],
                matrizValores[1][2],
                matrizValores[2][2],
                matrizValores[3][2],
        };
        int[] arrayColumna4 = new int[]{
                matrizValores[0][3],
                matrizValores[1][3],
                matrizValores[2][3],
                matrizValores[3][3],
        };
        // ----
        //  a cada array simple aplicamos metodo manejarArrayIzquerda();
        int[] columna1 = manejarArrayIzquerda(arrayColumna1);
        int[] columna2 = manejarArrayIzquerda(arrayColumna2);
        int[] columna3 = manejarArrayIzquerda(arrayColumna3);
        int[] columna4 = manejarArrayIzquerda(arrayColumna4);

        // pasamos datos de los arrays simples a matriz
        pasarDatosDeColumnasAlMatriz(columna1, columna2, columna3, columna4);

        // comprobamos si terminamos juego
        comprobarGameOver();

        // nuevo random en tablero
        //generarNuevoNumeroEnTablero();
        crearRandom2();

        // pasamos datos de matriz int[][] a layout, para mostrar
        // repintarValoresEnCasillas();

    }// end of moverArribaMatrizValores()

    /**
     * IZQUERDA !!!
     * metodo para mover valores a la izquerda
     */
    private void moverIzquerdaMatrizValores() {

        // creamos arrays simples int[] donde guardamos filas del matriz
        int[] arrayFila1 = new int[]{
                matrizValores[0][0],
                matrizValores[0][1],
                matrizValores[0][2],
                matrizValores[0][3],
        };
        int[] arrayFila2 = new int[]{
                matrizValores[1][0],
                matrizValores[1][1],
                matrizValores[1][2],
                matrizValores[1][3],
        };
        int[] arrayFila3 = new int[]{
                matrizValores[2][0],
                matrizValores[2][1],
                matrizValores[2][2],
                matrizValores[2][3],
        };
        int[] arrayFila4 = new int[]{
                matrizValores[3][0],
                matrizValores[3][1],
                matrizValores[3][2],
                matrizValores[3][3],
        };

        // a cada fila aplicamos metodos que mueven, suma, y mueve otravae numeros, para ordenar
        int[] fila1 = manejarArrayIzquerda(arrayFila1);
        int[] fila2 = manejarArrayIzquerda(arrayFila2);
        int[] fila3 = manejarArrayIzquerda(arrayFila3);
        int[] fila4 = manejarArrayIzquerda(arrayFila4);

        // pasamos datos de los 4 arrays simples a int[][] matrizValores
        pasarDatosDeFilasAlMatriz(fila1, fila2, fila3, fila4);

        // comprobamos si podemos jugar o terminamos
        comprobarGameOver();

        // generamos un nuevo numero en el tablero
        //generarNuevoNumeroEnTablero();
        crearRandom2();

        // volvemos a repintar datos de matrizValores en el layout
        // repintarValoresEnCasillas();

    } // end of moverIzquerdaMatrizValores()


    /**
     * DERECHA !!!
     * metodo para mover numeros de las filas a la derecha
     * y sumar iguales
     */
    public void moverDerechaMatrizValores() {

        // creamos arrays simples para  guardar datos de cada fila
        int[] arrayFila1 = new int[]{
                matrizValores[0][0],
                matrizValores[0][1],
                matrizValores[0][2],
                matrizValores[0][3],
        };
        int[] arrayFila2 = new int[]{
                matrizValores[1][0],
                matrizValores[1][1],
                matrizValores[1][2],
                matrizValores[1][3],
        };
        int[] arrayFila3 = new int[]{
                matrizValores[2][0],
                matrizValores[2][1],
                matrizValores[2][2],
                matrizValores[2][3],
        };
        int[] arrayFila4 = new int[]{
                matrizValores[3][0],
                matrizValores[3][1],
                matrizValores[3][2],
                matrizValores[3][3],
        };

        // a las arrays simples aplicamos metodo que mueve numeros y suma iguales
        int[] fila1 = manejarArrayDerecha(arrayFila1);
        int[] fila2 = manejarArrayDerecha(arrayFila2);
        int[] fila3 = manejarArrayDerecha(arrayFila3);
        int[] fila4 = manejarArrayDerecha(arrayFila4);

        // pasamos datos de mos arrays simples a la matrizValores int[][]
        pasarDatosDeFilasAlMatriz(fila1, fila2, fila3, fila4);

        // comprobamos si podemos jugar o terminamos
        comprobarGameOver();

        // generamos un nuevo numero random en el tablero
        //generarNuevoNumeroEnTablero();
        crearRandom2();

        // volvemos a repintar numeros de matrizValores en layout
        // repintarValoresEnCasillas();
    }


    /**
     * DATOS EN FILAS
     * metodo para pasar datos de los arrays silmpes a la matriz principal
     *
     * @param arrayFila0
     * @param arrayFila1
     * @param arrayFila2
     * @param arrayFila3
     */
    private void pasarDatosDeFilasAlMatriz(int[] arrayFila0, int[] arrayFila1, int[] arrayFila2, int[] arrayFila3) {

        for (int i = 0; i < matrizValores.length; i++) {
            for (int j = 0; j < matrizValores[0].length; j++) {
                if (i == 0) {
                    matrizValores[i][j] = arrayFila0[j];
                }
                if (i == 1) {
                    matrizValores[i][j] = arrayFila1[j];
                }
                if (i == 2) {
                    matrizValores[i][j] = arrayFila2[j];
                }
                if (i == 3) {
                    matrizValores[i][j] = arrayFila3[j];
                }
            }
        }
//
//
//        // ---------primera fila-------
//        matrizValores[0][0] = arrayFila0[0];
//        matrizValores[0][1] = arrayFila0[1];
//        matrizValores[0][2] = arrayFila0[2];
//        matrizValores[0][3] = arrayFila0[3];
//        // ---------segunda fila-------
//        matrizValores[1][0] = arrayFila1[0];
//        matrizValores[1][1] = arrayFila1[1];
//        matrizValores[1][2] = arrayFila1[2];
//        matrizValores[1][3] = arrayFila1[3];
//        // ---------tercera fila-------
//        matrizValores[2][0] = arrayFila2[0];
//        matrizValores[2][1] = arrayFila2[1];
//        matrizValores[2][2] = arrayFila2[2];
//        matrizValores[2][3] = arrayFila2[3];
//        // ---------cuarta fila-------
//        matrizValores[3][0] = arrayFila3[0];
//        matrizValores[3][1] = arrayFila3[1];
//        matrizValores[3][2] = arrayFila3[2];
//        matrizValores[3][3] = arrayFila3[3];

    } // end of pasarDatosDeFilasAlMatriz

    /**
     * DATOS EN COLUMNAS
     * metodo para pasar datos de los arrays simple a las columnas
     *
     * @param arrayColumna0
     * @param arrayColumna1
     * @param arrayColumna2
     * @param arrayColumna3
     */
    private void pasarDatosDeColumnasAlMatriz(int[] arrayColumna0, int[] arrayColumna1, int[] arrayColumna2, int[] arrayColumna3) {

        for (int i = 0; i < matrizValores.length; i++) {
            for (int j = 0; j < matrizValores[0].length; j++) {
                if (j == 0) {
                    matrizValores[i][j] = arrayColumna0[i];
                }
                if (j == 1) {
                    matrizValores[i][j] = arrayColumna1[i];
                }
                if (j == 2) {
                    matrizValores[i][j] = arrayColumna2[i];
                }
                if (j == 3) {
                    matrizValores[i][j] = arrayColumna3[i];
                }
            }
        }
//
//        // ---------primera columna-------
//        matrizValores[0][0] = arrayColumna0[0];
//        matrizValores[1][0] = arrayColumna0[1];
//        matrizValores[2][0] = arrayColumna0[2];
//        matrizValores[3][0] = arrayColumna0[3];
//        // ---------segunda columna-------
//        matrizValores[0][1] = arrayColumna1[0];
//        matrizValores[1][1] = arrayColumna1[1];
//        matrizValores[2][1] = arrayColumna1[2];
//        matrizValores[3][1] = arrayColumna1[3];
//        // ---------tercera columna-------
//        matrizValores[0][2] = arrayColumna2[0];
//        matrizValores[1][2] = arrayColumna2[1];
//        matrizValores[2][2] = arrayColumna2[2];
//        matrizValores[3][2] = arrayColumna2[3];
//        // ---------cuarta columna-------
//        matrizValores[0][3] = arrayColumna3[0];
//        matrizValores[1][3] = arrayColumna3[1];
//        matrizValores[2][3] = arrayColumna3[2];
//        matrizValores[3][3] = arrayColumna3[3];
    }// end pasarDatosDeColumnaAlMatriz

    /**
     * DERECHA CONJUNTO
     * metodp que lleva dentro 3 metodos
     * 1 - ordena array , todos numeros se mueven a la derecha, zeros a la izquerda
     * 2 - summa si hay dos numeros iguales al lado, pero solo una vez
     * 3 - vuelve a mover numeros a la derecha, porque al sumar podrian aparecer espacios con cero
     *
     * @param array
     * @return
     */
    private int[] manejarArrayDerecha(int[] array) {
        int[] array1 = moverNumerosUnArrayDerecha(array);
        int[] array2 = sumarNumerosIgualesDerecha(array1);
        int[] array3 = moverNumerosUnArrayDerecha(array2);
        return array3;
    } // end of manejarArrayDerecha()

    /**
     * IZQUERDA CONJUNTO
     * 1. metodo que contiene un metodo para mover a la izquerda, intercambiar numeros con ceros
     * 2. despued suma numeros si son iguales, pero solo una vez
     * 3. y despues otra vez apliza movimiento , por si despues de sumar ha parecido mas ceros
     *
     * @param array
     * @return
     */
    private int[] manejarArrayIzquerda(int[] array) {
        int[] array1 = moverNumerosUnArrayIzquerda(array);
        int[] array2 = sumarNumerosIgualesIzquerda(array1);
        int[] array3 = moverNumerosUnArrayIzquerda(array2);
        return array3;
    } // end of manejarArrayIzquerda()

    /**
     * MOVER NUMEROS DERECHA
     * recibimos como parametro un array
     * y movemos sus numeros que no son ceros a la derecha
     *
     * @param array array de 4 numeros que son filas o columnas de matriz
     * @return devuelve array ordenado, numeros movidos a la derecha
     */
    private int[] moverNumerosUnArrayDerecha(int[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - 1; j++) {
                if (array[j + 1] == 0) {
                    int aux = array[j];
                    array[j + 1] = aux;
                    array[j] = 0;
                }
            }
        }
        return array;
    } // end of moverNumerosUnArrayDerecha()


    /**
     * MOVER NUMEROS IZQUERDA
     * recibimos como parametro un array
     * y movemos sus numeros que no son ceros a la izquerda
     *
     * @param array array de 4 posiciones ( filas o columnas )
     * @return devuelve array ordenado, intercambiados ceros y numeros, a la izquerda numeros
     */
    private int[] moverNumerosUnArrayIzquerda(int[] array) {
        for (int i = array.length; i > 0; i--) {
            for (int j = array.length - 1; j > 0; j--) {
                if (array[j - 1] == 0) {
                    int aux = array[j];
                    array[j] = 0;
                    array[j - 1] = aux;
                }
            }
        }
        return array;
    } // end of moverNumerosUnArrayIzquerda()

    /**
     * SUMAR NUMEROS DERECHA
     * recibe como parametro una array de numeros y si encuentra numeros iguales los suma
     * usamos algo parecido 'metodo burbuja' , doble for para 'ordenar'
     *
     * @param array array de 4 numeros que representan fila o columna para mover a la izquerda/ arriba
     * @return devuelve array ordenado - sumando numeros iguales
     */
    private int[] sumarNumerosIgualesDerecha(int[] array) {
        // estu numero guarda valor nuevo despues de sumar dos casillas iguales
        // lo aplicamos despues para evitar que casillas sumadas vuelven a sumarse
        int numeroSumado = -1;
        //-------------------
        for (int i = array.length - 1; i > 0; i--) {
            for (int j = array.length - 1; j > 0; j--) {
                // comprobamos si son iguales, !y que no sea valor que acabamos de sumar ya !
                if ((array[j] == array[j - 1]) && (array[j - 1] != numeroSumado)) {
                    //Log.d(TAG, "-----------------------");
                    //Log.d(TAG, "array j = "+array[j]+ "|  array j - 1 = " + array[j-1]);
                    int aux = array[j];
                    array[j - 1] = 0;
                    //Log.d(TAG, " array j - 1 = " + array[j-1]);
                    array[j] = aux * 2;
                    //Log.d(TAG, "array j = "+array[j]);
                    numeroSumado = aux * 2;
                    //Log.d(TAG, "numeroSumado = "+ numeroSumado);
                    //Log.d(TAG, "-----------------------");
                    puntuacionJuegoActual = puntuacionJuegoActual + numeroSumado;

                    // comprobamos si superamos puntuacion maxima del juego
                    if(puntuacionJuegoActual > puntuacionMaximaDelJugadorActual){
                        Log.d(TAG," entramos if puntuacion actual supera la del BBDD");
                        // aqui guardamos puntuacion ??
                        helper.modificarPuntuacionDelUsuario2048(usuarioActual,db, puntuacionJuegoActual);

                        int puntuacionActualizada = helper.buscarPuntuacionMax2048(db);

                        textView_puntuacionMax.setText("Puntuacion Maxima: "+puntuacionActualizada);

                    }
                }
            }
        }
        return array;
    } // end of sumarNumerosIgualesDerecha()

    /**
     * SUMAR NUMEROS IZQUERDA
     * recibe como parametro una array de numeros y si encuentra numeros iguales los suma
     * uso, algo parecido 'metodo burbuja' , doble for para 'ordenar'
     *
     * @param array array de 4 numeros que representan fila o columna para mover a la izquerda/ arriba
     * @return devuelve array ordenado - sumando numeros iguales
     */
    private int[] sumarNumerosIgualesIzquerda(int[] array) {
        // estu numero guarda valor nuevo despues de sumar dos casillas iguales
        // lo aplicamos despues para evitar que casillas sumadas vuelven a sumarse
        int numeroSumado = -1;
        //-------------------
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - 1; j++) {
                if ((array[j] == array[j + 1]) && (array[j + 1] != numeroSumado)) {
                    int aux = array[j];
                    array[j] = 0;
                    array[j + 1] = aux * 2;
                    numeroSumado = aux * 2;
                    puntuacionJuegoActual = puntuacionJuegoActual +numeroSumado;

                     // comprobamos si superamos puntuacion maxima del juego
                    if(puntuacionJuegoActual > puntuacionMaximaDelJugadorActual){
                        Log.d(TAG," entramos if puntuacion actual supera la del BBDD");
                        // aqui guardamos puntuacion
                        helper.modificarPuntuacionDelUsuario2048(usuarioActual,db, puntuacionJuegoActual);

                        int puntuacionActualizada = helper.buscarPuntuacionMax2048(db);

                        textView_puntuacionMax.setText("Puntuacion Maxima: "+puntuacionActualizada);
                    }
                }
            }
        }
        return array;
    } // end of sumarNumerosIgualesIzquerda()


    /**
     * metodo sobreescrito para usar menu de ayuda de tres puntos verticales ...
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu); // borramos return !!! que se genera automaticamente
        getMenuInflater().inflate(R.menu.gameoption2048, menu);

        // creamos help menu
        menu.findItem(R.id.help_menu_item_help_2048).setIntent(
                new Intent(this, HelpActivity2048.class));
        menu.findItem(R.id.help_menu_item_setting_2048).setIntent(
                new Intent(this, SettingActivity2048.class));
        menu.findItem(R.id.help_menu_item_score_2048).setIntent(
                new Intent(this, ScoresActivity2048.class));

        return true;
    }

} // -- fin main



