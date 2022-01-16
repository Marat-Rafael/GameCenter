package com.example.gamecenternuevo;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;

/**
 * Clase para dar effecto de aparecion al texto
 * 'robado en internet'...
 */
public class TypeWriter extends androidx.appcompat.widget.AppCompatTextView {
    // ------------------------------ ATRIBUTOS ----------------------------------------------------
    private CharSequence mText;
    private int mIndex;
    private long mDelay = 150; // in ms
    private SoundPlayer soundPlayer = new SoundPlayer(this.getContext());

    // -----------------------------CONSTRUCTORES --------------------------------------------------
    public TypeWriter(Context context) {
        super(context);
    }

    public TypeWriter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // ---------------------------------metodos ----------------------------------------------------

    private Handler mHandler = new Handler();
    private Runnable characterAdder = new Runnable() {
        @Override
        public void run() {
            setText(mText.subSequence(0, mIndex++));
            if (mIndex <= mText.length()) {
                // ---------añadimos sonido tambien !
                soundPlayer.playType();
                // ----------- para eliminar sonido borramos linea anterior
                mHandler.postDelayed(characterAdder, mDelay);
            }
        }
    };


    public void animateText(CharSequence txt) {
        mText = txt;
        mIndex = 0;
        setText("");
        mHandler.removeCallbacks(characterAdder);
        mHandler.postDelayed(characterAdder, mDelay);
    }
    public void setCharacterDelay(long m) {
        mDelay = m;
    }
}