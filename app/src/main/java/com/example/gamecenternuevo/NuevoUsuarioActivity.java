package com.example.gamecenternuevo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Actividad donde registramos nuevo usuario a BBDD
 */
public class NuevoUsuarioActivity extends AppCompatActivity {

    private static final String TAG = "BBDD";

    Button button_crear;
    Button button_cancelar;

    EditText editText_usuario;
    EditText editText_contrasenia;
    EditText editText_repite_contrasenia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_usuario);

        // hacemos referencia a los view del xml
        button_crear = findViewById(R.id.activity_nuevo_usuario_button_crear_nuevo_usuario);
        editText_usuario = findViewById(R.id.activity_nuevo_usuario_editText_nombre);
        editText_contrasenia = findViewById(R.id.activity_nuevo_usuario_editText_contraseña);
        editText_repite_contrasenia = findViewById(R.id.activity_nuevo_usuario_editText_repite_contrasenia);
        button_cancelar = findViewById(R.id.activity_nuevo_usuario_button_cancelar);

        // creamos instancia de Helper
        UsuariosHelper helper = new UsuariosHelper(getApplicationContext());

        button_crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = editText_usuario.getText().toString();
                String pass = editText_contrasenia.getText().toString();
                String pass_repit = editText_repite_contrasenia.getText().toString();
                if(pass.equals(pass_repit)){
                    // pasamos datos
                    helper.insertUsuarioContrasenia(user,pass);
                    Toast.makeText(NuevoUsuarioActivity.this, "insertado con exito", Toast.LENGTH_SHORT).show();
                    editText_contrasenia.setBackground(null);
                    editText_repite_contrasenia.setBackground(null);
                    Log.d(TAG,"pass validado, pass: "+ pass+ " |  pass repetido: "+ pass_repit);
                    Intent intent = new Intent(NuevoUsuarioActivity.this, LoginActivity.class);
                    startActivity(intent);

                }else {
                    editText_contrasenia.setBackgroundResource(R.color.red);
                    editText_repite_contrasenia.setBackgroundResource(R.color.red);
                    Toast.makeText(NuevoUsuarioActivity.this, "contraseña no coincide", Toast.LENGTH_SHORT).show();
                    Log.d(TAG,"pass NO validado, pass: "+ pass+ " |  pass repetido: "+ pass_repit);
                }

            }
        });

        button_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(NuevoUsuarioActivity.this, LoginActivity.class));

            }
        });
    }
}