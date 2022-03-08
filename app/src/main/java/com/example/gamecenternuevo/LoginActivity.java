package com.example.gamecenternuevo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText name;
    EditText password;
    Button login;
    Button cancelar;
    Button crearNuevoUsuario;
    String passwordDesdeBBDD;
    String passwordIntroducido;
    String nombreUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name = findViewById(R.id.login_activity_editText_name);
        password = findViewById(R.id.login_activity_editText_password);
        login = findViewById(R.id.login_activity_button_login);
        cancelar = findViewById(R.id.login_activity_button_cancelar);
        crearNuevoUsuario = findViewById(R.id.login_activity_button_crear_nuevo);
        // creamos instancia de BBDD
        UsuariosHelper helper = new UsuariosHelper(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = helper.getReadableDatabase();
                // cojemos datos del campo name
                nombreUsuario = name.getText().toString();
                passwordIntroducido = password.getText().toString();
                // cojemos contraseña de la base de datos
                passwordDesdeBBDD = helper.buscarContraseniaDelUsuario(nombreUsuario,db);
                // si datos coinciden
                if(passwordIntroducido.equals(passwordDesdeBBDD)){
                    password.setBackground(null);
                    // abrimos actividad de eligir juego
                    Intent intent = new Intent(LoginActivity.this, EligeJuegoActivity.class);
                    // pasamos datos del usuario actual
                    intent.putExtra("USUARIO",nombreUsuario);
                    startActivity(intent);
                }else{
                    // si datos NO coinciden
                    password.setBackgroundResource(R.drawable.casilla_red);
                    Toast.makeText(LoginActivity.this, "ontraseña erronea", Toast.LENGTH_SHORT).show();
                }
            }// fin onclick
        }); // fin setOnClick login

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setText("");
                password.setText("");
                password.setBackground(null);
                cerrarTeclado();
            }
        }); // fin setOnClick cancelar

        crearNuevoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,NuevoUsuarioActivity.class);
                startActivity(intent);
            }
        });
    }// fin onCreate

    /**
     * Metodo para ocultar teclado virtual
     */
    public void cerrarTeclado() {
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
}