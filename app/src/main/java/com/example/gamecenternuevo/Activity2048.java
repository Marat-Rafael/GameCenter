package com.example.gamecenternuevo;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

import androidx.constraintlayout.widget.ConstraintLayout;


/**
 * Para detectar movimientos hay que implementar OnTouchListener
 */
public class Activity2048 extends AppCompatActivity implements View.OnTouchListener, GestureDetector.OnGestureListener {
    // implementando interfaz OnTouchListener nos obliga sobreescribir onTouch metod
    // implementando interfaz GestureDetector nos obliga sobreescribir sus metodos


    final String TAG = "juego";
    TextView auxTextView;
    private boolean gameOver = false;
    GestureDetector gestureDetector;
    TextView[][] matriz;
    int[][] matrizValores;
    private ConstraintLayout constraintLayout;
    private static SoundPlayer soundPlayer2048;


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

//        matrizValores = new int[][]{
//                {2, 2, 2, 2},
//                {2, 2, 2, 2},
//                {2, 2, 2, 2},
//                {2, 2, 2, 2}
//        };


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

        crearMatrizValores();
        repintarValoresEnCasillas();
        crearRandom();

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
                soundPlayer2048.playRifle();
                Log.d(TAG, "onFling a derecha");

                // si punto final es menor en eje X , es movimiento Izquerdo
            } else if (motionEvent1.getX() < motionEvent.getX()) {
                moverIzquerdaMatrizValores();
                soundPlayer2048.playRifle();
                Log.d(TAG, " onFling a izquerda");

            }
            // si alto mas grande que ancho
        } else {
            // si
            if (motionEvent1.getY() > motionEvent.getY()) {
                Log.d(TAG, "onFling abajo");
                soundPlayer2048.playRifle();
                moverAbajoMatrizValores();


            } else if (motionEvent1.getY() < motionEvent.getY()) {
                Log.d(TAG, "onFling arriba");
                soundPlayer2048.playRifle();
                moverArribaMatrizValores();
            }
        }
        return false;
    }

    /**
     * metodo para mostrar coordenadas de la celda
     */
    public void pintarValores() {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                matriz[i][j].setText(i + " " + j);
            }
        }
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
            }
        }
    }


    /**
     * OBSOLETO
     * metodo para recorrer matriz de arriba abajo
     * de izquerda a derecha
     */
    public void recorrido1() {
        int num = 0;
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                matriz[i][j].setText("" + num);
                num++;
            }
        }
    }

    /**
     * OBSOLETO
     * recorre matriz de arriba abajo
     * y de derecha a izquerda
     */
    public void recorrido2() {
        int num = 0;
        for (int i = 0; i < matriz.length; i++) {
            for (int j = matriz[0].length - 1; j >= 0; j--) {
                matriz[i][j].setText("" + num);
                num++;
            }
        }
    }

    /**
     * OBSOLETO
     * recorre matriz de abajo acia arriba
     * y de izquerda a derecha
     */
    public void recorrido3() {
        int num = 0;
        for (int i = matriz.length - 1; i >= 0; i--) {
            for (int j = 0; j < matriz[0].length; j++) {
                matriz[i][j].setText("" + num);
                num++;
            }
        }
    }

    /**
     * OBSOLETO
     * recorre matriz de abajo ariba
     * y de derecha a izquerda
     */
    public void recorrido4() {
        int num = 0;
        for (int i = matriz.length - 1; i >= 0; i--) {
            for (int j = matriz[0].length - 1; j >= 0; j--) {
                matriz[i][j].setText("" + num);
                num++;
            }
        }
    }

    /**
     * creamos 2 numeros random
     * para coordenada de una casilla random
     */
    public void crearRandom() {
        Random random1 = new Random();
        int num1 = random1.nextInt(matrizValores.length);
        Log.d(TAG, "primer random = " + num1);
        Random random2 = new Random();
        int num2 = random2.nextInt(matrizValores[0].length);
        Log.d(TAG, "segundo random = " + num2);
        matrizValores[num1][num2] = 2;
        repintarValoresEnCasillas();
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
     * metodo nos devuelve coordenada de una casilla random
     *
     * @return
     */
    private int[] determinarUnNumeroRandomParaNuevaCasilla() {
        int numerosCero = 0;
        // creamos array para guardar dos numeros I y J
        int[] coordenadas = new int[2];
        // determinamos cuantas casillas vacias hay
        for (int i = 0; i < matrizValores.length; i++) {
            for (int j = 0; j < matrizValores[0].length; j++) {
                if (matrizValores[i][j] == 0) {
                    numerosCero++;
                }
            }
        }
        Random random_I = new Random();
        Random random_J = new Random();
        int numero_en_fila_I = random_I.nextInt(numerosCero);
        int numero_en_fila_J = random_J.nextInt(numerosCero);

        // recorremos segunda vez matriz para determinar esta casilla
        int contador = 0;
        for (int i = 0; i < matrizValores.length; i++) {
            for (int j = 0; j < matrizValores[0].length; j++) {
                if (matrizValores[i][j] == 0) {
                    contador++;
                    if (contador == numero_en_fila_I) {
                        coordenadas[0] = i;
                    }
                    if (contador == numero_en_fila_J) {
                        coordenadas[1] = j;
                    }
                }
            }
        }
        return coordenadas;
    } // end of determinarUnNumeroRandomParaNuevaCasilla()

    private void generarNuevoNumeroEnTablero() {
        int[] arraycoordenadas = determinarUnNumeroRandomParaNuevaCasilla();

        int coordenada_I = arraycoordenadas[0];
        int coordenada_J = arraycoordenadas[1];
        int numeroRandom = generar2o4();
        matrizValores[coordenada_I][coordenada_J] = numeroRandom;

    }


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
        // ----
        //  a cada array simple aplicamos metodo manejarArray();
        int[] columna1 = manejarArray(arrayColumna1);
        int[] columna2 = manejarArray(arrayColumna2);
        int[] columna3 = manejarArray(arrayColumna3);
        int[] columna4 = manejarArray(arrayColumna4);

        // pasamos
        pasarDatosDeColumnasAlMatriz(columna1, columna2, columna3, columna4);

        // generamos un nuevo numero en el tablero
        generarNuevoNumeroEnTablero();

        // pasamos datos de matriz int[][] a layout, para mostrar
        repintarValoresEnCasillas();

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
        //  a cada array simple aplicamos metodo manejarArray();
        // tenemos que invertir array , porque se le aplica metodo que lleva todos numeros abajo
        int[] columna1 = manejarArray(arrayColumna1);
        // invertimos
        int[] col1_INV = invertiraArray(columna1);

        int[] columna2 = manejarArray(arrayColumna2);
        // invertimos
        int[] col2_INV = invertiraArray(columna2);

        int[] columna3 = manejarArray(arrayColumna3);
        // invertimos
        int[] col3_INV = invertiraArray(columna3);

        int[] columna4 = manejarArray(arrayColumna4);
        // invertimos
        int[] col4_INV = invertiraArray(columna4);

        // pasamos
        pasarDatosDeColumnasAlMatriz(col1_INV, col2_INV, col3_INV, col4_INV);

        // nuevo random en tablero
        generarNuevoNumeroEnTablero();

        // pasamos datos de matriz int[][] a layout, para mostrar
        repintarValoresEnCasillas();

    }// end moverArribaMatrizValores()

    /**
     * IZQUERDA !!!
     * metodo para mover valores a la izquerda
     */
    private void moverIzquerdaMatrizValores() {

        // creamos arrays simples int[] para usar metodos de ordenar
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

        // nos devuelve array con numeros a la derecha, hay que invertirlo
        int[] fila1 = manejarArray(arrayFila1);
        // invertimos array
        int[] fila1_izq = invertiraArray(fila1);

        int[] fila2 = manejarArray(arrayFila2);
        // invertimos array
        int[] fila2_izq = invertiraArray(fila2);

        int[] fila3 = manejarArray(arrayFila3);
        // invertimos array
        int[] fila3_izq = invertiraArray(fila3);

        int[] fila4 = manejarArray(arrayFila4);
        // invertimos array
        int[] fila4_izq = invertiraArray(fila4);

        // pasamos datos de los 4 arrays simples a int[][] matrizValores
        pasarDatosDeFilasAlMatriz(fila1_izq, fila2_izq, fila3_izq, fila4_izq);

        generarNuevoNumeroEnTablero();

        // volvemos a repintar datos de matrizValores en el layout
        repintarValoresEnCasillas();

    } // end of moverIzquerdaMatrizValores()


    /**
     * DERECHA !!!
     * metodo para mover numeros de las filas a la derecha
     * y sumar iguales
     */
    public void moverDerechaMatrizValores() {

        // creamos arrays simples para trabajar
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

        // a las arrays simples aplicamos metodo que mueve numeros y summa iguales
        int[] fila1 = manejarArray(arrayFila1);
        int[] fila2 = manejarArray(arrayFila2);
        int[] fila3 = manejarArray(arrayFila3);
        int[] fila4 = manejarArray(arrayFila4);

        // pasamos datos de mos arrays simples a la matrizValores int[][]
        pasarDatosDeFilasAlMatriz(fila1, fila2, fila3, fila4);

        // generamos un nuevo numero random en el tablero
        generarNuevoNumeroEnTablero();

        // volvemos a repintar numeros de matrizValores en layout
        repintarValoresEnCasillas();
    }

    /**
     * INVERTIR
     * porque otro metodo lo deja alineado a la derecha
     * al invertir sera alineado a la izquerda
     *
     * @param array
     * @return
     */
    private int[] invertiraArray(int[] array) {
        int[] arrayInvertido = new int[array.length];

        for (int i = 0, j = array.length - 1; i < array.length; i++, j--) {
            arrayInvertido[j] = array[i];
        }
        return arrayInvertido;
    } // end invertirArray()


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
        // ---------primera fila-------
        matrizValores[0][0] = arrayFila0[0];
        matrizValores[0][1] = arrayFila0[1];
        matrizValores[0][2] = arrayFila0[2];
        matrizValores[0][3] = arrayFila0[3];
        // ---------segunda fila-------
        matrizValores[1][0] = arrayFila1[0];
        matrizValores[1][1] = arrayFila1[1];
        matrizValores[1][2] = arrayFila1[2];
        matrizValores[1][3] = arrayFila1[3];
        // ---------tercera fila-------
        matrizValores[2][0] = arrayFila2[0];
        matrizValores[2][1] = arrayFila2[1];
        matrizValores[2][2] = arrayFila2[2];
        matrizValores[2][3] = arrayFila2[3];
        // ---------primera fila-------
        matrizValores[3][0] = arrayFila3[0];
        matrizValores[3][1] = arrayFila3[1];
        matrizValores[3][2] = arrayFila3[2];
        matrizValores[3][3] = arrayFila3[3];

    }

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
        // ---------primera columna-------
        matrizValores[0][0] = arrayColumna0[0];
        matrizValores[1][0] = arrayColumna0[1];
        matrizValores[2][0] = arrayColumna0[2];
        matrizValores[3][0] = arrayColumna0[3];
        // ---------segunda columna-------
        matrizValores[0][1] = arrayColumna1[0];
        matrizValores[1][1] = arrayColumna1[1];
        matrizValores[2][1] = arrayColumna1[2];
        matrizValores[3][1] = arrayColumna1[3];
        // ---------tercera columna-------
        matrizValores[0][2] = arrayColumna2[0];
        matrizValores[1][2] = arrayColumna2[1];
        matrizValores[2][2] = arrayColumna2[2];
        matrizValores[3][2] = arrayColumna2[3];
        // ---------primera columna-------
        matrizValores[0][3] = arrayColumna3[0];
        matrizValores[1][3] = arrayColumna3[1];
        matrizValores[2][3] = arrayColumna3[2];
        matrizValores[3][3] = arrayColumna3[3];
    }// end pasarDatosDeColumnaAlMatriz

    /**
     * MOVER SUMAR MOVER
     * metodp que lleva dentro 3 metodos
     * 1 - ordena array , todos numeros se mueven a la derecha, zeros a la izquerda
     * 2 - summa si hay dos numeros iguales al lado
     * 3 - vuelve a mover numeros a la derecha, porque al sumar podrian aparecer espacios con cero
     *
     * @param array
     * @return
     */
    private int[] manejarArray(int[] array) {
        int[] array1 = moverNumerosUnArrayDerecha(array);
        int[] array2 = sumarNumerosIgualesDerecha(array1);
        int[] array3 = moverNumerosUnArrayDerecha(array2);
        return array3;
    }

    /**
     * MOVER NUMEROS
     * recibimos como parametro un array
     * y movemos sus numeros que no son ceros a la derecha
     *
     * @param array
     * @return
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
    }

    /**
     * SUMAR NUMEROS
     * recibe como parametro una array de numeros y si encuentra numeros iguales los suma
     * al sumar puede aparecer espacios en blanco, asi que volvemos a mover numeros
     *
     * @param array
     * @return
     */
    private int[] sumarNumerosIgualesDerecha(int[] array) {
        // estu numero guarda valor nuevo despues de sumar dos casillas iguales
        // lo aplicamos despues para evitar que casillas sumadas vuelven a sumarse
        int numeroSumado = -1;
        //-------------------
        for (int i = array.length; i >= 0; i--) {
            for (int j = array.length - 1; j >= 0; j--) {
                if (j - 1 >= 0) {
                    // comprobamos si son iguales y que no sea valor que acabamos de sumar ya
                    if ((array[j - 1] == array[j]) && (array[j - 1] != numeroSumado)) {
                        int aux = array[j];
                        array[j - 1] = 0;
                        array[j] = aux * 2;
                        numeroSumado = aux * 2;
                    }
                }
            }
        }
        return array;
    }

    /**
     *  metodo sobreescrito para usar menu de ayuda
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
                new Intent(this, SettingActivity2048.class));

        return true;
    }
} // -- fin main



