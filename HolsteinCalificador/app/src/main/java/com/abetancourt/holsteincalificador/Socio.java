package com.abetancourt.holsteincalificador;

/**
 * Created by Alex Betancourt on 19/01/2016.
 */
public class Socio {
    private int id;
    private String NoHato;
    private String Nombre;

    public Socio(){}

    public Socio(int id, String noHato, String nombre) {
        this.id = id;
        NoHato = noHato;
        Nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoHato() {
        return NoHato;
    }

    public void setNoHato(String noHato) {
        NoHato = noHato;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
