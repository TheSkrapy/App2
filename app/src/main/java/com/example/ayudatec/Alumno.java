package com.example.ayudatec;

import android.media.Image;

public class Alumno {
    private int ncontrol;
    private String nombre;
    private Image foto;
    private String carrera;

    public Alumno(int ncontrol, String nombre, Image foto, String carrera){
        this.ncontrol = ncontrol;
        this.nombre = nombre;
        this.foto = foto;
        this.carrera = carrera;
    }

    public int getNcontrol() {
        return ncontrol;
    }

    public void setNcontrol(int ncontrol) {
        this.ncontrol = ncontrol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Image getFoto() {
        return foto;
    }

    public void setFoto(Image foto) {
        this.foto = foto;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }
}
