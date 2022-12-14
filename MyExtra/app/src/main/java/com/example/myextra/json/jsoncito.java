package com.example.myextra.json;

import java.io.Serializable;

public class jsoncito implements Serializable {
    private String jusr;
    private String jcontra;
    private String jmail;
    private String[] jplat;
    private Boolean jpref;
    private String jdate;
    private String jtel;
    private String jgenfav;
    private Boolean booleador;
    private String jpseud;

    public Boolean getBooleador() {
        return booleador;
    }
    public void setBooleador(Boolean booleador) {
        this.booleador = booleador;
    }

    public String getJpseud() {
        return jpseud;
    }

    public void setJpseud(String jpseud) {
        this.jpseud = jpseud;
    }

    public String getJgenfav() {
        return jgenfav;
    }

    public void setJgenfav(String jgenfav) {
        this.jgenfav = jgenfav;
    }
    public jsoncito(){
    }
    
    
    public String getJusr() {
        return jusr;
    }

    public void setJusr(String jusr) {
        this.jusr = jusr;
    }

    public String getJcontra() {
        return jcontra;
    }

    public void setJcontra(String jcontra) {
        this.jcontra = jcontra;
    }

    public String getJmail() {
        return jmail;
    }

    public void setJmail(String jmail) {
        this.jmail = jmail;
    }

    public String[] getJplat() {
        return jplat;
    }

    public void setJplat(String[] jplat) {
        this.jplat = jplat;
    }

    public Boolean getJpref() {
        return jpref;
    }

    public void setJpref(Boolean jpref) {
        this.jpref = jpref;
    }

    public String getJdate() {
        return jdate;
    }

    public void setJdate(String jdate) {
        this.jdate = jdate;
    }

    public String getJtel() {
        return jtel;
    }

    public void setJtel(String jtel) {
        this.jtel = jtel;
    }


}
