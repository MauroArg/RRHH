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
public class Payroll 
{
    private int    pln_id;
    private String pln_fecha;
    private Double pln_total;
    private int pln_estado;

    public Payroll() {
    }

    public Payroll(int pln_id, String pln_fecha, Double pln_total, int pln_estado) {
        this.pln_id = pln_id;
        this.pln_fecha = pln_fecha;
        this.pln_total = pln_total;
        this.pln_estado = pln_estado;
    }

    public int getPln_id() {
        return pln_id;
    }

    public void setPln_id(int pln_id) {
        this.pln_id = pln_id;
    }

    public String getPln_fecha() {
        return pln_fecha;
    }

    public void setPln_fecha(String pln_fecha) {
        this.pln_fecha = pln_fecha;
    }

    public Double getPln_total() {
        return pln_total;
    }

    public void setPln_total(Double pln_total) {
        this.pln_total = pln_total;
    }

    public int getPln_estado() {
        return pln_estado;
    }

    public void setPln_estado(int pln_estado) {
        this.pln_estado = pln_estado;
    }
    
    

    
    
    
}
