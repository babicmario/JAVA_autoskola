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
public class ComboBoxModel extends InstruktoriModel {

    public ComboBoxModel(String id, String ime, String prezime, String vozilo, String kategorija) {
        super(id, ime, prezime, vozilo, kategorija);
    }

    @Override
    public String toString() {
        return ime + " " + prezime;
    }
    
    
}
