package com.example.gamecenternuevo;

/**
 * Clase para guardar usuarios en la base de datos
 */
public class Usuario {
    int id;
    String nombre;
    String password;
    int puntuacion_2048;
    int puntuacion_peg;

    // CONSTRUCTOR
    // CONSTRUCTOR CON TODOS ATRIBUTOS
    public Usuario(int id, String nombre, String password, int puntuacion_2048, int puntuacion_peg) {
        this.id = id;
        this.nombre = nombre;
        this.password = password;
        this.puntuacion_2048 = puntuacion_2048;
        this.puntuacion_peg = puntuacion_peg;
    }
    // CONSTRUCTOR SIN ID
    public Usuario(String nombre, String password, int puntuacion_2048, int puntuacion_peg) {
        this.nombre = nombre;
        this.password = password;
        this.puntuacion_2048 = puntuacion_2048;
        this.puntuacion_peg = puntuacion_peg;
    }
    // CONSTRUCTOR SIN ID Y PUNTUACION
    public Usuario(String nombre, String password) {
        this.nombre = nombre;
        this.password = password;
    }

    // GETTER Y SETTER
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPuntuacion_2048() {
        return puntuacion_2048;
    }

    public void setPuntuacion_2048(int puntuacion_2048) {

        this.puntuacion_2048 = puntuacion_2048;
    }

    public int getPuntuacion_peg() {

        return puntuacion_peg;
    }

    public void setPuntuacion_peg(int puntuacion_peg) {
        this.puntuacion_peg = puntuacion_peg;
    }
}
