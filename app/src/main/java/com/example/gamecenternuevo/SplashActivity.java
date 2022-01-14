package com.example.gamecenternuevo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView eft = findViewById(R.id.tv_eft);

        Animation mover = AnimationUtils.loadAnimation(this, R.anim.animation_splash);

        // animacion
        eft.startAnimation(mover);

        // animationListener
        mover.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(SplashActivity.this, EligeJuegoActivity.class));
                SplashActivity.this.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }


}
