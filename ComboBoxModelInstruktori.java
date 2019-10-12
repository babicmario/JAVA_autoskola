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
public class ComboBoxModelInstruktori extends VozilaModel{
    
     public ComboBoxModelInstruktori(String id, String naziv, String godina, String servis) {
        super(id, naziv, godina,servis);
    
}

    @Override
    public String toString() {
        return naziv;
    }
     
     
}
