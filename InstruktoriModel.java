/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.mavenproject3;

/**
 *
 * @author mario
 */
public class InstruktoriModel {
    String id,ime,prezime,kategorija, vozilo_naziv;

    public InstruktoriModel(String id, String ime, String prezime, String kategorija, String vozilo_naziv) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.kategorija = kategorija;
        this.vozilo_naziv = vozilo_naziv;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getKategorija() {
        return kategorija;
    }

    public void setKategorija(String kategorija) {
        this.kategorija = kategorija;
    }

    public String getVozilo_naziv() {
        return vozilo_naziv;
    }

    public void setVozilo_naziv(String vozilo_naziv) {
        this.vozilo_naziv = vozilo_naziv;
    }
    
    
}
