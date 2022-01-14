package com.example.gamecenternuevo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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


    private static final String TAG = "JUEGO";
    public TextView[][] matrixTextView;

    public ArrayList<TextView> arrayListCasillasJugables;
    public ArrayList<TextView> arrayListCassillasVacias;
    public ArrayList<TextView> arrayListCassillasSaltoPermitido;

    public TextView casillaElegida;
    public TextView auxiliar;
    public boolean terminado;
    public boolean saltoRealizado = false;

    // public static boolean booleanCasillaEligida = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peg_solitaire);
        setTitle("Peg Solitaire");

        arrayListCasillasJugables = new ArrayList<>();
        arrayListCassillasVacias = new ArrayList<>();
        arrayListCassillasSaltoPermitido = new ArrayList<>();
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
        detectarBotonPulsada();

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
                cambiarBackground(matrixTextView[i][j], R.drawable.casilla_rellena);
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
                    arrayListCassillasVacias.add(matrixTextView[i][j]);
                }
            }
        }
        Log.d(TAG, "sale generar casilla vacia matriz");
    }


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
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "sale buscar todas casillas jugables  --- V2 ---");
    }

    private void animarRotate(TextView textView) {

        Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.scale2);
        textView.startAnimation(animation2);

    }


    public void detectarBotonPulsada() {
        Log.d(TAG, "entra detectar boton pulsado");

        for (int i = 0; i < arrayListCasillasJugables.size(); i++) {

            int finalI = i;
            arrayListCasillasJugables.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // casilla elegida coje valor de la casilla pulsada
                    casillaElegida = findViewById(arrayListCasillasJugables.get(finalI).getId());

                    // resto de casillas convertimos en casillas rellenas
                    for (int i = 0; i < arrayListCasillasJugables.size(); i++) {
                        convertirCasilla_A_Rellenas(arrayListCasillasJugables.get(i));
                    }
                    // convertimos en la casilla_selecionada
                    convertirCasilla_A_Selecionada(casillaElegida);

                    // quitamos a listaDecasiilasJugables listener
                    eliminarListenerAlArrayList(arrayListCasillasJugables);

                    calcularMovimientosDisponiblesParaCasillaElegida();

                }
            });
        }
        Log.d(TAG, "sale detectar boton pulsado");

    }

    /**
     * Nuevo metodo para detectar elemento pulsado
     *
     * @return TextView pulsado
     */
    public TextView detectarBotonPulsada2() {
        Log.d(TAG, "entra detectar boton pulsado -- V2 --");
        // recorremos lista de casillas jugables
        for (int i = 0; i < arrayListCasillasJugables.size(); i++) {
            int finalI = i; // que es y porque me lo coloca ?¿?¿?
            // y ponemos a la escucha
            arrayListCasillasJugables.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    auxiliar = findViewById(arrayListCasillasJugables.get(finalI).getId());
                }
            });
        }
        // quitamos onClickListener a todos
        eliminarListenerAlArrayList(arrayListCasillasJugables);
        // limpiamos arraylist de casillasJugables
        arrayListCasillasJugables.clear();
        // devolvemos TextView que esta pulsada
        Log.d(TAG, "sale detectar boton pulsado -- V2 --");
        return auxiliar;

    }

    /**
     * metodo para encontrar coordenadas de un TextView en la matriz
     * devuelve un array con dos posiciones
     * array[0] = i
     * array[1] = j
     *
     * @param textView
     */
    public int[] buscarCoordenadasDelElemento(TextView textView) {
        Log.d(TAG, "entra buscar coordenadasDelElemento");
        int[] coordenadasEnMatriz = new int[2];
        // buscamos en la matriz nuestro objeto
        for (int i = 0; i < matrixTextView.length; i++) {
            for (int j = 0; j < matrixTextView[0].length; j++) {
                if (textView.getId() == matrixTextView[i][j].getId()) {
                    coordenadasEnMatriz[0] = i;
                    coordenadasEnMatriz[1] = j;
                    return coordenadasEnMatriz;
                }
            }
        }
        mostrarToast("no se encuentran coordenadas del TextView");
        Log.d(TAG, "sale buscarCoordenadasDelElemento");
        return null;
    }


    /**
     * Al arrayList borramos  OnClicklistener
     *
     * @param arrayList
     */
    public void eliminarListenerAlArrayList(ArrayList<TextView> arrayList) {
        Log.d(TAG, "entra eliminarListenerAlArrayList");
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList.get(i).setOnClickListener(null);
        }
        Log.d(TAG, "sale eliminarListenerAlArrayList");
    }

    /**
     * metodo para detectar movimientos disponibles para una casilla selecionada
     */
    private void calcularMovimientosDisponiblesParaCasillaElegida() {
        Log.d(TAG, "entra CalcularMovimientosPermitidosParaCasillaElegida");
        calcularMovimientosDisponibles_I_masDos();
        calcularMovimientosDisponibles_J_masDos();
        calcularMovimientosDisponibles_I_menosDos();
        calcularMovimientosDisponibles_J_menosDos();
        realizarSalto();
        Log.d(TAG, "sale CalcularMovimientosPermitidosParaCasillaElegida");

        buscarTodasCasillasJugables();
    }

    private void calcularMovimientosDisponibles_I_masDos() {
        for (int i = 0; i < matrixTextView.length; i++) {
            for (int j = 0; j < matrixTextView[0].length; j++) {
                if (matrixTextView[i][j].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.casilla_selecionada, null).getConstantState())) {
                    //comprobamos i + 2, que no sale de la pantalla y esta habilitada
                    if ((i + 2 < matrixTextView.length) && (matrixTextView[i + 2][j].isEnabled())) {
                        // comprobamos primera casilla. tiene que ser rellena
                        if (matrixTextView[i + 1][j].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.casilla_rellena, null).getConstantState())) {
                            // comprobamos que la segunda casilla esta libre
                            if (matrixTextView[i + 2][j].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.casilla_vacia, null).getConstantState())) {
                                // matrixTextView[i + 2][j].setBackgroundResource(R.drawable.casilla_salto_permitido);
                                convertirCasilla_A_Salto_Permitido(matrixTextView[i + 2][j]);

                                // añadimos al arrayDeSaltoPermitido para poner a la escucha
                                arrayListCassillasSaltoPermitido.add(matrixTextView[i + 2][j]);
                                // realizamos movimiento
                                // realizarSalto();
                            }
                        }
                    }
                }
            }
        }
    }

    private void calcularMovimientosDisponibles_I_menosDos() {
        for (int i = 0; i < matrixTextView.length; i++) {
            for (int j = 0; j < matrixTextView[0].length; j++) {
                if (matrixTextView[i][j].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.casilla_selecionada, null).getConstantState())) {
                    //comprobamos i - 2, que no sale de la pantalla y esta habilitada
                    if ((i - 2 >= 0) && (matrixTextView[i - 2][j].isEnabled())) {
                        // comprobamos primera casilla. tiene que ser rellena
                        if (matrixTextView[i - 1][j].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.casilla_rellena, null).getConstantState())) {
                            // comprobamos que la segunda casilla esta libre
                            if (matrixTextView[i - 2][j].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.casilla_vacia, null).getConstantState())) {
                                // matrixTextView[i - 2][j].setBackgroundResource(R.drawable.casilla_salto_permitido);
                                convertirCasilla_A_Salto_Permitido(matrixTextView[i - 2][j]);
                                // añadimos al arrayDeSaltoPermitido para poner a la escucha
                                arrayListCassillasSaltoPermitido.add(matrixTextView[i - 2][j]);
                                // realizamos movimiento
                                // realizarSalto();
                            }
                        }
                    }
                }
            }
        }
    }

    private void calcularMovimientosDisponibles_J_masDos() {
        for (int i = 0; i < matrixTextView.length; i++) {
            for (int j = 0; j < matrixTextView[0].length; j++) {
                if (matrixTextView[i][j].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.casilla_selecionada, null).getConstantState())) {
                    //comprobamos i + 2, que no sale de la pantalla y esta habilitada
                    if ((j + 2 < matrixTextView[0].length) && (matrixTextView[j + 2][j].isEnabled())) {
                        // comprobamos primera casilla. tiene que ser rellena
                        if (matrixTextView[i][j + 1].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.casilla_rellena, null).getConstantState())) {
                            // comprobamos que la segunda casilla esta libre
                            if (matrixTextView[i][j + 2].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.casilla_vacia, null).getConstantState())) {
                                //matrixTextView[i][j + 2].setBackgroundResource(R.drawable.casilla_salto_permitido);
                                convertirCasilla_A_Salto_Permitido(matrixTextView[i][j + 2]);
                                // añadimos al arrayDeSaltoPermitido para poner a la escucha
                                arrayListCassillasSaltoPermitido.add(matrixTextView[i][j + 2]);
                                // realizamos movimiento
                                // realizarSalto();
                            }
                        }
                    }
                }
            }
        }
    }

    private void calcularMovimientosDisponibles_J_menosDos() {
        for (int i = 0; i < matrixTextView.length; i++) {
            for (int j = 0; j < matrixTextView[0].length; j++) {
                if (matrixTextView[i][j].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.casilla_selecionada, null).getConstantState())) {
                    //comprobamos j - 2, que no sale de la pantalla y esta habilitada
                    if ((j - 2 >= 0) && (matrixTextView[j - 2][j].isEnabled())) {
                        // comprobamos primera casilla. tiene que ser rellena
                        if (matrixTextView[i][j - 1].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.casilla_rellena, null).getConstantState())) {
                            // comprobamos que la segunda casilla esta libre
                            if (matrixTextView[i][j - 2].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.casilla_vacia, null).getConstantState())) {
                                // matrixTextView[i][j - 2].setBackgroundResource(R.drawable.casilla_salto_permitido);
                                convertirCasilla_A_Salto_Permitido(matrixTextView[i][j - 2]);
                                // añadimos al arrayDeSaltoPermitido para poner a la escucha
                                arrayListCassillasSaltoPermitido.add(matrixTextView[i][j - 2]);
                                // realizamos movimiento
                                // realizarSalto();
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * metodo que pone a la escucha a todos los casillas que pueden ser selecionadas.
     * para esto usamos arrayList de casiilas que tienen salto permitido
     */
    private void realizarSalto() {
        Log.d(TAG, "entra realizarSalto");
        for (int i = 0; i < arrayListCassillasSaltoPermitido.size(); i++) {
            arrayListCassillasSaltoPermitido.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // recoremos matriz para identificar casilla del origen
                    int[] coordenadasOrigen = identificarCasillaEnMatriz(casillaElegida);
                    // recorremos matrix para identificar casilla del destino
                    int[] coordenadasDestino = identificarCasillaEnMatriz(view);

                    int[] coordenadasCasillaBorrar = devuelveCoordenadasCasillaParaBorrar(coordenadasOrigen, coordenadasDestino);

                    // repintamos  background de los tres
                    // //------------ !!!???  AQUI HAY ERROR !!!???--------------

                    convertirCasilla_A_Vacia(matrixTextView[coordenadasCasillaBorrar[0]][coordenadasCasillaBorrar[1]]);

                    convertirCasilla_A_Vacia(matrixTextView[coordenadasOrigen[0]][coordenadasOrigen[1]]);

                    convertirCasilla_A_Rellenas(matrixTextView[coordenadasDestino[0]][coordenadasDestino[1]]);

                    //------------------------------------------------------------------------------
                    //------------------------------------------------------------------------------
                    // OJO !!! AQUI VOLVEMOS A BUSCAR  JUGADAS !!!!
                    //------------------------------------------------------------------------------
                    buscarTodasCasillasJugables();
                    detectarBotonPulsada();
                    //------------------------------------------------------------------------------
                    //------------------------------------------------------------------------------


                    // añadimos al arraylist casillas vacias (no lo uso de momento)
                    //arrayListCassillasVacias.add(matrixTextView[coordenadasCasillaBorrar[0]][coordenadasCasillaBorrar[1]]);
                }
            });

        }
        // despues de realizar salto limpiamos arraylist de las  casillasSaltoPermitido y casillasJugables
        arrayListCassillasSaltoPermitido.clear();
        arrayListCasillasJugables.clear();
        arrayListCassillasVacias.clear();


        Log.d(TAG, "sale RealizarSalto");

    }

    /**
     * metodo para calcular coordenadas de la casilla que esta entre origin y destino
     *
     * @param origen  coordenadas de la casilla de origen
     * @param destino coordenadas de la casilla de destino
     * @return devuelve coordenadas de la casilla que se borra
     */
    public int[] devuelveCoordenadasCasillaParaBorrar(int[] origen, int[] destino) {
        Log.d(TAG, "entra devuelveCoordenadaCasillaParaborra");
        int i_origen = origen[0];
        int j_origen = origen[1];

        int i_destino = destino[0];
        int j_destino = destino[1];

        int i_resultado = -1;
        int j_resultado = -1;

        int[] listaCoordenadasAdevolver = new int[2];

        if (i_origen == i_destino) { // estamos en la misma fila
            i_resultado = i_origen;
            if (j_origen > j_destino) {
                j_resultado = j_origen - 1;
            } else {
                j_resultado = j_origen + 1;
            }
        } else if (j_origen == j_destino) { // estan en la misma columna
            j_resultado = j_origen;
            if (i_origen > i_destino) {
                i_resultado = i_origen - 1;
            } else {
                i_resultado = i_origen + 1;
            }
        }
        listaCoordenadasAdevolver[0] = i_resultado;
        listaCoordenadasAdevolver[1] = j_resultado;
        Log.d(TAG, "sale devuelveCoordenadaCasillaParaBorrar");

        return listaCoordenadasAdevolver;
    }


    /**
     * metodo para identificar una casilla en el matriz
     *
     * @param view vista
     * @return devuelve simple matriz con coordenadas de la casilla I J
     */
    private int[] identificarCasillaEnMatriz(View view) {
        Log.d(TAG, "entra identificarCasillaEnMatriz");
        int[] listacoordenadas = new int[2];
        for (int i = 0; i < matrixTextView.length; i++) {
            for (int j = 0; j < matrixTextView[0].length; j++) {
                if (matrixTextView[i][j].getId() == view.getId()) {
                    listacoordenadas[0] = i;
                    listacoordenadas[1] = j;
                }
            }
        }
        // mostrarToast("i=" + listacoordenadas[0] + " j=" + listacoordenadas[1]);
        Log.d(TAG, "sale identificarCasillaEnMatriz");
        return listacoordenadas;
    }


    private void cambiarBackground(@NonNull TextView textView, int idRes) {
        textView.setBackgroundResource(idRes);
    }

    public void convertirCasilla_A_Rellenas(TextView textView) {
        //Log.d(TAG, "entra convertirCasillaRellena");
        cambiarBackground(textView, R.drawable.casilla_rellena);
        //Log.d(TAG, "sale convertirCasillaRellena");
    }

    public void convertirCasilla_A_Vacia(TextView textView) {
        //Log.d(TAG, "entra convertirCasillaVacia");
        cambiarBackground(textView, R.drawable.casilla_vacia);
        arrayListCassillasVacias.add(textView);
        //Log.d(TAG, "sale convertirCasillaVacia");
    }

    /**
     * Metodo para cambiar fondo de la casilla a la casilla jugable
     * y añadimos al arayList de las casillas jugables
     *
     * @param textView casilla que convertimos a casilla jugable
     */
    public void convertirCasilla_A_Jugable(TextView textView) {
        //Log.d(TAG, "entra convertirCasilla_Jugable");
        cambiarBackground(textView, R.drawable.casilla_jugable);
        arrayListCasillasJugables.add(textView);
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

