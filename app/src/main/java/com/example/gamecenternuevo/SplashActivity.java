package com.example.gamecenternuevo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setTitle("Espantapajaros, Mago Oz");

        // detectamos layout principal
        LinearLayout linearLayout = findViewById(R.id.linear_layout_principal);
        // identificamos imagen
        TextView espantapajarosImagen = findViewById(R.id.tv_espantapajaros);
        // identificamos nueva clase de TextWriter, campo donde va aparecer texto
        TypeWriter typeWriter = findViewById(R.id.tv_desc_espanta);




        // cargamos animacion de fadeIn
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        // cargamos animacion de fadeOut
        Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);

        // animacion para que aparecen todos los elementos de activity
        linearLayout.startAnimation(fadeIn);
        espantapajarosImagen.startAnimation(fadeIn);
        typeWriter.startAnimation(fadeIn);

        // animationListener para detectar cuando acaba animacion
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            /**
             * cuando animacion fadeIn termina
             * @param animation
             */
            @Override
            public void onAnimationEnd(Animation animation) {
                String t1 = "\"SI TAN SOLO TUVIERA UN CEREBRO....\"\n";
                String t2 = "\n(El mago de Oz)\nEspantapájaros";
                // String texto = " \"SI TAN SOLO TUVIERA UN CEREBRO....\" \n      \"Espantapajaros\" Mago Oz  ";
                String texto = t1+t2;

                typeWriter.setText("");
                typeWriter.setCharacterDelay(150);
                typeWriter.animateText(texto);


                // animacion fadeOut empieza en 15 segundos
                linearLayout.startAnimation(fadeOut);
                espantapajarosImagen.startAnimation(fadeOut);
                typeWriter.startAnimation(fadeOut);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            /**
             * Al terminar animacion abrimos otra actividad
             * y cerramos actividad del splash
             * @param animation
             */
            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(SplashActivity.this, EligeJuegoActivity.class));
                // cerramos splash activity, NO podemos volver a este activity
                SplashActivity.this.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }


}
