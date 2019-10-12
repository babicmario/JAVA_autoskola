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
public class VozilaModel {
    
    
        String id, naziv, godina, servis;

    public VozilaModel(String id, String naziv, String godina, String servis) {
        this.id = id;
        this.naziv = naziv;
        this.godina = godina;
        this.servis = servis;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getGodina() {
        return godina;
    }

    public void setGodina(String godina) {
        this.godina = godina;
    }

    public String getServis() {
        return servis;
    }

    public void setServis(String servis) {
        this.servis = servis;
    }

    

    
}
