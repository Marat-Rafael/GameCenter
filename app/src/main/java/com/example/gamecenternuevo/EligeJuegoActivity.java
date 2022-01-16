package com.example.gamecenternuevo;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class EligeJuegoActivity extends AppCompatActivity {

    ListView menuListEligeJuego;
    String [] juegos;

    private static SoundPlayer soundPlayer ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elige_juego);
        setTitle("Elige Juego");

        soundPlayer = new SoundPlayer(this);

        // ----------------animamos background del constraint layout -------------------------------
        ConstraintLayout constraintLayout = findViewById(R.id.layout_elige_juego);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(2500);
        animationDrawable.start();
        //-------------------------------fin animacion----------------------------------------------

        // identificamos ListView
        menuListEligeJuego = (ListView) findViewById(R.id.listView_elige_juego);

        // creamos array de strings
        juegos = new String[]{
                getString(R.string.starWars2048),
                getString(R.string.starWarsPeg)
        };

        cargarListViewPersonalizado();

        // ----------------- Iniciamos sonido  || por que no va bien ?------------------------------
        soundPlayer.playImperialSong();

        // creamos listener
        menuListEligeJuego.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = (TextView) view;  // pasa como parametro
                // guardamos texto del elemento pulsado para despues compararlo
                String texto = textView.getText().toString();
                switch(texto){
                    case "2048":
                        mostrarToast("2048");
                        // abrimos actividad del menu para juego 2048
                        startActivity(new Intent(getApplicationContext(), MenuActivity2048.class));
                        break;
                    case "PEG":
                        mostrarToast("PEG");
                        // abrimos actividad PegSolitaire
                        startActivity(new Intent(getApplicationContext(), com.example.gamecenternuevo.MenuActivityPegSolitaire.class));
                        break;
                    default:
                        mostrarToast("error");
                        break;
                }
            }
        });

    }


    /**
     * metodo para mostrar toast
     * @param string
     */
    public void mostrarToast(String string){
        Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();
    }

    /**
     * metodo para cargar ListView estandarte
     */
    public void cargarListViewEstandart(){
        // creamos adapter con simple vista, predeterminada
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1,
                juegos);

        // añadimos adapter al ListView
        menuListEligeJuego.setAdapter(adapt);
    }

    /**
     * metodo para cargar ListView personalizado
     */
    public void cargarListViewPersonalizado(){

        // OJO! menu_item no tiene que tener layout, directamente TextView
        // adaptador con menu personalizado
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(getApplicationContext(),R.layout.menu_elige_juego, juegos);

        // añadimos adapter al ListView
        menuListEligeJuego.setAdapter(adapt);
    }
}