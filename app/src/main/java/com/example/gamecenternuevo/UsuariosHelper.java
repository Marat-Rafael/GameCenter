package com.example.gamecenternuevo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * CLASE PARA MANEJAR BBDD
 */
public class UsuariosHelper extends SQLiteOpenHelper {

    //  variable para log
    // private static final String TAG = UsuariosHelper.class.getSimpleName();
    private static final String TAG = "BBDD";

    // CONSTANTES PARA TABLA
    private static final int DATABASE_VERSION = 1;              // tiene que empezar por uno
    public static final String DATABASE_NAME = "Users";         //  nombre de la base de datos
    public static final String TABLE_NAME = "datosUsuarios";    // nombre de la tabla
    public static final String COMA_SEPARADORA = " ,";
    public static final String TEXT_TYPE = " TEXT";
    public static final String INTEGER_TYPE = " INTEGER";

    // COLUMNAS QUE TENDRA TABLA
    public static final String COLUMNA_ID = "_Id";
    public static final String COLUMNA_USUARIO = "Usuario";
    public static final String COLUMNA_CONTRASENIA = "Contrasenia";
    public static final String COLUMNA_PUNTUACION_2048 = "Puntuacion_2048";
    public static final String COLUMNA_PUNTUACION_PEG = "Puntuacion_Peg";

    // CREAMOS ARRAY DE COLUMNAS
    //private static final String[] COLUMNAS = {COLUMNA_ID, COLUMNA_USUARIO, COLUMNA_CONTRASENIA, COLUMNA_PUNTUACION_2048, COLUMNA_PUNTUACION_PEG};

    // ARRAYLIST DE USUARIOS INICIALES
    private ArrayList<Usuario> listaUsuariosIniciales = new ArrayList<>();

    // COMANDO PARA CREAR TABLA
    private static final String SQL_CREATE_ENTRIES =
            // OJO CON PARENTESIS
            "CREATE TABLE " + TABLE_NAME + " (" +
                    // ID tendra autoincrement si no pasamos valor
                    COLUMNA_ID +INTEGER_TYPE + " PRIMARY KEY " + COMA_SEPARADORA +
                    COLUMNA_USUARIO + TEXT_TYPE + COMA_SEPARADORA +
                    COLUMNA_PUNTUACION_2048 + INTEGER_TYPE + COMA_SEPARADORA +
                    COLUMNA_PUNTUACION_PEG + INTEGER_TYPE + COMA_SEPARADORA +
                    COLUMNA_CONTRASENIA + TEXT_TYPE + " );";  // OJO , CERRAR PARENTESIS Y PUNTO-COMA


    // COMANDO PARA BORRAR TABLA
    private static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    // CONSTRUCTOR
    public UsuariosHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "Creamos UsuariosHelper");
    }

    // VARIABLES PARA DDBB DE ESCRITURA Y LECTURA
    private SQLiteDatabase mWritableDB;
    private SQLiteDatabase mReadableDB;


    /**
     * Metodo para crear una BBDD
     *
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
        llenarDatabaseConDatosIniciales(sqLiteDatabase);
    } // fin onCreate

    /**
     * Metodo para introducir datos basicos a la base de datos
     *
     * @param sqLiteDatabase
     */
    private void llenarDatabaseConDatosIniciales(SQLiteDatabase sqLiteDatabase) {
        Log.d(TAG, "entramos llenar datos iniciales");
        // en arraylist metemos usuarios principales
        listaUsuariosIniciales.add(new Usuario("admin", "admin", 0, 0));
        listaUsuariosIniciales.add(new Usuario("marat", "marat", 0, 0));
        listaUsuariosIniciales.add(new Usuario("rafael", "rafael", 0, 0));

        // creamos contenedor para datos
        ContentValues values = new ContentValues();

        // recorremos array y llenamos datos
        for (int i = 0; i < listaUsuariosIniciales.size(); i++) {
            values.put(COLUMNA_USUARIO, listaUsuariosIniciales.get(i).getNombre());
            values.put(COLUMNA_CONTRASENIA, listaUsuariosIniciales.get(i).getPassword());
            //values.put(COLUMNA_PUNTUACION_2048, listaUsuariosIniciales.get(i).getPuntuacion_2048());
            values.put(String.valueOf(COLUMNA_PUNTUACION_2048), listaUsuariosIniciales.get(i).getPuntuacion_2048());
            values.put(String.valueOf(COLUMNA_PUNTUACION_PEG), listaUsuariosIniciales.get(i).getPuntuacion_peg());
            sqLiteDatabase.insert(TABLE_NAME, null, values);
            Log.d(TAG, " usuarios iniciales: Nombre" + listaUsuariosIniciales.get(i).getNombre() +
                    " | Contraseña: " + listaUsuariosIniciales.get(i).getPassword() +
                    " | Puntuacion 2048 " + listaUsuariosIniciales.get(i).getPuntuacion_2048() +
                    " | Puntuacion Peg " + listaUsuariosIniciales.get(i).getPuntuacion_peg());
        }
        Log.d(TAG, "salimos crear datos iniciales");
    } // fin fillDatabase

    /**
     * Metodo para actualizar BBDD
     *
     * @param sqLiteDatabase
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(TAG, "Actualizamos Base de Datos desde version:  " + oldVersion + "\n" +
                "A nueva version: " + newVersion + " , lo que destrue toda veja informacion");
        sqLiteDatabase.execSQL(SQL_DELETE_TABLE);
        onCreate(sqLiteDatabase);
    }// fin onUpgrade

    /**
     * Metodo para insertar una nueva fila en la BBDD
     */
    public void insertUsuarioContrasenia(String usuario, String contasenia) {
        Log.d(TAG, "entramos insertUsuariocontraseña");
        // si operacion falla metodo devuelve 0
        long newId = 0;

        // creamos variable para guardar fila
        ContentValues values = new ContentValues();
        values.put(COLUMNA_USUARIO, usuario);
        values.put(COLUMNA_CONTRASENIA, contasenia);
        values.put(String.valueOf(COLUMNA_PUNTUACION_2048), 0);
        values.put(String.valueOf(COLUMNA_PUNTUACION_PEG), 0);
        // usamos try-catch
        try {
            // si no esta BBDD, creamos una
            if (mWritableDB == null) {
                mWritableDB = getWritableDatabase();
            }
            newId = mWritableDB.insert(TABLE_NAME, null, values);
            // mWritableDB.insert(TABLE_NAME, null, values);
            Log.d(TAG, "Se guardo con la clave: " + newId +
                    " nombre: " + usuario +
                    " password: " + contasenia +
                    " puntuacion 2048  0" +
                    " puntuacion peg  0");

        } catch (Exception e) {
            Log.d(TAG, "INSERT EXCEPCION! " + e.getMessage());
        }
        Log.d(TAG, "salimos insertUsuariocontraseña");
    } // fin insert


    /**
     * Metodo devuelve contraseña del un usuario
     * resultadA DE busqueda query nos devuelve un cursor
     *
     * @param usuario
     */
    public String buscarContraseniaDelUsuario(String usuario, SQLiteDatabase db) {
        Log.d(TAG, "entramos buscarContraseña");

        // array de columnas que nos devuelve consulta, aqui nos interesa solo CONTRASEÑA, nombre coje del edit text
        String[] projection = {
                COLUMNA_USUARIO,
                COLUMNA_CONTRASENIA
        };

        // filtro WHERE para consulta, para consulta preparada
        String selection = COLUMNA_USUARIO + " = ?";
        // de donde vienen criterios
        String[] selectionArgs = {usuario};

        String user = null;
        String passDelUser = null;

        // nos devuelve una tabla virtual resultSet

        // sort
        //String sortOrder = COLUMNA_USUARIO + " + DESC";
        try {

            // consulta
            Cursor cursor = db.query(
                    TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );

            // hay que leer resultSet
            // colocamos cursor en la primera posicion
            cursor.moveToFirst();

            user = cursor.getString(0); // cojemos columna cero
            passDelUser = cursor.getString(1); // cojemos columna 1
            Log.d(TAG, "cursor BBDD   usuario: " + user + "  contraseña: " + passDelUser);
        } catch (Exception e) {
            Log.d(TAG, "usuario: no encontrado");
        }

        Log.d(TAG, "salimos buscarContraseñao");
        return user;
    }// fin buscarContraseniaDelUsuario


    /**
     * metodo para devolver puntuacion del juego 2048
     *
     * @param usuario
     * @param db
     * @return
     */
    public int buscarPuntuacion2048DelUsuario(String usuario, SQLiteDatabase db) {
        Log.d(TAG, "entramos buscarPuntuacion2048 del usuario");

        // array de columnas que nos devuelve consulta, aqui nos interesa puntuacion2048
        String[] projection = {
                COLUMNA_USUARIO,
                String.valueOf(COLUMNA_PUNTUACION_2048)
        };

        // filtro WHERE para consulta, para consulta preparada
        String selection = COLUMNA_USUARIO + " = ?";
        // de donde vienen criterios
        String[] selectionArgs = {usuario};

        String user = null;
        int puntuacionDelUser = Integer.parseInt(null);

        // nos devuelve una tabla virtual resultSet
        // sort
        //String sortOrder = COLUMNA_USUARIO + " + DESC";

        try {
            // consulta
            Cursor cursor = db.query(
                    TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );

            // hay que leer resultSet
            // colocamos cursor en la primera posicion
            cursor.moveToFirst();

            user = cursor.getString(0); // cojemos columna cero
            puntuacionDelUser = cursor.getInt(1); // cojemos columna 1
            Log.d(TAG, "cursor  usuario: " + user + "  puntuacioon 2048: " + puntuacionDelUser);
        } catch (Exception e) {
            Log.d(TAG, "usuario: no encontrado");
        }
        Log.d(TAG, "salimos buscarPuntuacion 2048 del usuario");
        // devolvemos puntuacion
        return puntuacionDelUser;
    }// fin buscarPuntuacion2048DelUsuario


    /**
     * Metodo para actualizar BBDD
     * insertamos en campo puntuacion 2048 nuevo valor para un usuario concreto
     *
     * @param usuario
     * @param db
     * @param puntuacion2048
     */
    public void modificarPuntuacionDelUsuario2048(String usuario, SQLiteDatabase db, int puntuacion2048) {
        Log.d(TAG, "entramos modificar puntuacio del usuario " + usuario + " | puntuacion nueva es: " + puntuacion2048);
        ContentValues values = new ContentValues();
        values.put(String.valueOf(COLUMNA_PUNTUACION_2048), puntuacion2048);

        String selection = COLUMNA_USUARIO + " LIKE ?";
        String[] selectionArgs = {usuario};
        int count = db.update(
                TABLE_NAME,
                values,
                selection,
                selectionArgs
        );
        Log.d(TAG, "salimos modificar puntuacion 2048, nuevo valor " + puntuacion2048+" filas afectadas: "+count);
    }

    /**
     * Metodo para borra un usuario
     *
     * @param usuario
     * @param db
     */
    public void borrarUsuario(String usuario, SQLiteDatabase db) {
        Log.d(TAG, "entramos borrarUsuario");
        String selection = COLUMNA_USUARIO + " LIKE ?";
        String[] selectionArgs = {usuario};
        db.delete(TABLE_NAME, selection, selectionArgs);
        Log.d(TAG, "usuario borrado " + usuario);
        Log.d(TAG, "salimos borrarUsuario");
    }// fin borrarUsuario


    /**
     * Metodo para buscar en la BBDD mayor puntuacion 2048
     *
     * @param db
     * @return
     */
    public int buscarPuntuacionMax2048(SQLiteDatabase db) {
        Log.d(TAG, "entramos buscarPuntuacion2048");

        // array de columnas que nos devuelve consulta, aqui nos interesa puntuacion2048
        String[] projection = {
                //COLUMNA_USUARIO,
                COLUMNA_PUNTUACION_2048
        };

        String user;
        int puntuacionMaxEnDDBB = 0;

        // nos devuelve una tabla virtual resultSet
        // sort
        String sortOrder = COLUMNA_PUNTUACION_2048 + " DESC";
        try {

            // consulta
            Cursor cursor = db.query(
                    TABLE_NAME,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    sortOrder
            );

            // hay que leer resultSet
            // colocamos cursor en la primera posicion
            cursor.moveToFirst();

            //user = cursor.getString(0); // cojemos columna cero
            puntuacionMaxEnDDBB = cursor.getInt(0); // cojemos columna 1
            Log.d(TAG, "cursor  |  puntuacioon max 2048: " + puntuacionMaxEnDDBB);
        } catch (Exception e) {
            Log.d(TAG, "usuario: no encontrado");
        }
        Log.d(TAG, "salimos buscarPuntuacionMaxima ");
        // devolvemos puntuacion
        return puntuacionMaxEnDDBB;
    }// fin buscarPuntuacionMaxima2048


    /**
     * Metodo devuelve una fila de DDBB
     * @return
     */
//    public Cursor getRaw(SQLiteDatabase db){
//
//        Log.d(TAG, "entramos buscarPuntuacion2048 del usuario");
//
//        // array de columnas que nos devuelve consulta, cojemos 3 columnas : id, nombre y puntuacion 2048
//        String[] projection = {
//                COLUMNA_ID,
//                COLUMNA_USUARIO,
//                String.valueOf(COLUMNA_PUNTUACION_2048)
//        };
//        //Cursor cursor = db.rawQuery(projection,null);
//        return cursor;
//
//    }

    /**
     * Metodo para mostrar resultado
     */
    public void mostrarTodosUsuariosConPuntuacion(TextView textView, SQLiteDatabase db) {
        Log.d(TAG, "entramos buscarPuntuacion2048");

        // array de columnas que nos devuelve consulta, aqui nos interesa puntuacion2048
        String[] projection = {
                COLUMNA_USUARIO,
                COLUMNA_PUNTUACION_2048
        };

        int identificador;
        String user;
        int puntuacionDelUser = 0;

        // nos devuelve una tabla virtual resultSet
        // sort
        //String sortOrder = COLUMNA_ID + " DESC";
        try {

            // consulta
            Cursor cursor = db.query(
                    TABLE_NAME,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            // hay que leer resultSet
            // colocamos cursor en la primera posicion

            String fila = "\n";
            cursor.moveToFirst();
            user = cursor.getString(0); // cojemos columna 1
            puntuacionDelUser = cursor.getInt(1); // cojemos columna 2
            fila = fila + " usuariuo: "+user+" | puntuacion "+puntuacionDelUser +"\n";

            while (cursor.moveToNext()){

                user = cursor.getString(0); // cojemos columna 1
                puntuacionDelUser = cursor.getInt(1); // cojemos columna 2

                fila = fila + " usuariuo: "+user+" | puntuacion "+puntuacionDelUser +"\n";

                Log.d(TAG, "cursor  usuario: " + user + " |  puntuacioon 2048: " + puntuacionDelUser);
            }
            textView.setText(fila);
            cursor.close();


        } catch (Exception e) {
            Log.d(TAG, "usuario: no encontrado");
        }
        Log.d(TAG, "salimos buscarPuntuacionMaxima ");


    } // fin

}
