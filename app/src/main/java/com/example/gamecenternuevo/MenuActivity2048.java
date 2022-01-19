package com.example.gamecenternuevo;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MenuActivity2048 extends AppCompatActivity {

    String [] items;
    ListView menuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_2048);
        setTitle("Elige una opcion");

        // identificamos ListView
        menuList = (ListView) findViewById(R.id.ListView_Menu_2048);

        // creamos array de strings
        items = new String[]{
                getResources().getString(R.string.menu_item_play),
                getResources().getString(R.string.menu_item_scores),
                getResources().getString(R.string.menu_item_settings),
                getResources().getString(R.string.menu_item_help),
        };

        //cargarListViewEstandart();
       cargarListViewPersonalizado();

       animarMenuBackground2048();

       // creamos listener
       menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               TextView textView =(TextView) view; // vista que entra como parametro
               // guardamos texto del elemento pulsado para despues compararlo
               String strText = textView.getText().toString();
               switch (strText){
                   case "Play Game":
                       mostrarToast("juego");
                       // abrimos actividad juego 2048
                       startActivity(new Intent(getApplicationContext(), com.example.gamecenternuevo.Activity2048.class));

                       break;
                   case "View Scores":
                       mostrarToast("puntuacion");
                       break;
                   case "Help":
                       mostrarToast("ayuda");
                       break;
                   case "Setting":
                       mostrarToast("configuracion");
                       break;
                   default:
                       break;
               }

           }
       });
    }



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
                items);

        // añadimos adapter al ListView
        menuList.setAdapter(adapt);
    }
    public void cargarListViewPersonalizado(){

        // OJO! menu_item no tiene que tener layout, directamente TextView
        // adaptador con menu personalizado
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(getApplicationContext(),R.layout.menu_item,items);

        // añadimos adapter al ListView
        menuList.setAdapter(adapt);
    }

    /**
     * Metodo para animar Background
     */
    private void animarMenuBackground2048() {
        // ----------------animamos background del constraint layout -------------------------------
        RelativeLayout relativeLayout = findViewById(R.id.layout_relative_menu_2048);
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(2500);
        animationDrawable.start();
        //-------------------------------fin animacion----------------------------------------------
    }
}