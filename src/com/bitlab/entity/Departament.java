/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitlab.entity;

/**
 *
 * @author Mauricio Argumedo
 */
public class Departament 
{
    private int dep_id;
    private String dep_nombre;

    public Departament() {
    }

    public Departament(int dep_id, String dep_nombre) {
        this.dep_id = dep_id;
        this.dep_nombre = dep_nombre;
    }

    public int getDep_id() {
        return dep_id;
    }

    public void setDep_id(int dep_id) {
        this.dep_id = dep_id;
    }

    public String getDep_nombre() {
        return dep_nombre;
    }

    public void setDep_nombre(String dep_nombre) {
        this.dep_nombre = dep_nombre;
    }
    
    
    
}
