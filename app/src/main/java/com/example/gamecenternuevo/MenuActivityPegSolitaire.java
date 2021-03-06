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

public class MenuActivityPegSolitaire extends AppCompatActivity {

    String [] items;
    ListView menuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_peg);
        setTitle("Elige una opcion");

        // identificamos ListView
        menuList = (ListView) findViewById(R.id.ListView_Menu_peg);

        // creamos array de strings
        items = new String[]{
                getResources().getString(R.string.menu_item_play),
                getResources().getString(R.string.menu_item_scores),
                getResources().getString(R.string.menu_item_setting),
                getResources().getString(R.string.menu_item_help),
        };

        //cargarListViewEstandart();
       cargarListViewPersonalizado();

       // animar background
        animarMenuBackgroundPeg();


       // creamos listener
       menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               TextView textView =(TextView) view; // vista que entra como parametro
               // guardamos texto del elemento pulsado para despues compararlo
               String strText = textView.getText().toString();
               switch (strText){
                   case "Play Game":
                       // mostrarToast("juego Peg inicia");
                       startActivity(new Intent(getApplicationContext(), ActivityPegSolitaire.class));
                       break;

                   case "View Scores":
                       // mostrarToast("puntuacion");
                       startActivity(new Intent(getApplicationContext(), ScoresActivityPeg.class));
                       break;

                   case "Help":
                       // mostrarToast("ayuda");
                       startActivity(new Intent(getApplicationContext(), HelpActivityPeg.class));
                       break;

                   case "Setting":
                       // mostrarToast("configuracion");
                       startActivity(new Intent(getApplicationContext(), SettingActivityPeg.class));
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

        // a??adimos adapter al ListView
        menuList.setAdapter(adapt);
    }
    public void cargarListViewPersonalizado(){

        // OJO! menu_item no tiene que tener layout, directamente TextView
        // adaptador con menu personalizado
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(getApplicationContext(),R.layout.menu_item,items);

        // a??adimos adapter al ListView
        menuList.setAdapter(adapt);
    }

    /**
     * Metodo para animar Background
     */
    private void animarMenuBackgroundPeg() {
        // ----------------animamos background del constraint layout -------------------------------
        RelativeLayout relativeLayout = findViewById(R.id.layout_relative_menu_peg);
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(2500);
        animationDrawable.start();
        //-------------------------------fin animacion----------------------------------------------
    }
}