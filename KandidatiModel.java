/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.mavenproject3;

import javafx.scene.control.Button;

/**
 *
 * @author mario
 */
public class KandidatiModel {

     int id;
     String ime,prezime,jmbg,instruktor_ime;

    public KandidatiModel(int id, String ime, String prezime, String jmbg, String instruktor_ime) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.jmbg = jmbg;
        this.instruktor_ime = instruktor_ime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getInstruktor_ime() {
        return instruktor_ime;
    }

    public void setInstruktor_id(String instruktor_ime) {
        this.instruktor_ime = instruktor_ime;
    }

   

}