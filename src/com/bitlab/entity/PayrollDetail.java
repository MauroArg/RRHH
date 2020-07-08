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
public class PayrollDetail 
{
    private int      det_pln_id;
    private double   det_pln_total;
    private double   det_pln_total_descuentos;
    private double   det_pln_renta;
    private double   det_pln_isss;
    private double   det_pln_afp;
    private byte     det_pln_cantidad_horas_extra_diurnas;
    private byte     det_pln_cantidad_horas_extra_nocturnas;
    private double   det_pln_bono_horas_extra;
    private int      det_pln_id_pln;
    private Employe  employe;

    public PayrollDetail() {
    }

    public PayrollDetail(int det_pln_id, double det_pln_total, double det_pln_total_descuentos, double det_pln_renta, double det_pln_isss, double det_pln_afp, byte det_pln_cantidad_horas_extra_diurnas, byte det_pln_cantidad_horas_extra_nocturnas, double det_pln_bono_horas_extra, int det_pln_id_pln, Employe employe) {
        this.det_pln_id = det_pln_id;
        this.det_pln_total = det_pln_total;
        this.det_pln_total_descuentos = det_pln_total_descuentos;
        this.det_pln_renta = det_pln_renta;
        this.det_pln_isss = det_pln_isss;
        this.det_pln_afp = det_pln_afp;
        this.det_pln_cantidad_horas_extra_diurnas = det_pln_cantidad_horas_extra_diurnas;
        this.det_pln_cantidad_horas_extra_nocturnas = det_pln_cantidad_horas_extra_nocturnas;
        this.det_pln_bono_horas_extra = det_pln_bono_horas_extra;
        this.det_pln_id_pln = det_pln_id_pln;
        this.employe = employe;
    }

    public int getDet_pln_id() {
        return det_pln_id;
    }

    public void setDet_pln_id(int det_pln_id) {
        this.det_pln_id = det_pln_id;
    }

    public double getDet_pln_total() {
        return det_pln_total;
    }

    public void setDet_pln_total(double det_pln_total) {
        this.det_pln_total = det_pln_total;
    }

    public double getDet_pln_total_descuentos() {
        return det_pln_total_descuentos;
    }

    public void setDet_pln_total_descuentos(double det_pln_total_descuentos) {
        this.det_pln_total_descuentos = det_pln_total_descuentos;
    }

    public double getDet_pln_renta() {
        return det_pln_renta;
    }

    public void setDet_pln_renta(double det_pln_renta) {
        this.det_pln_renta = det_pln_renta;
    }

    public double getDet_pln_isss() {
        return det_pln_isss;
    }

    public void setDet_pln_isss(double det_pln_isss) {
        this.det_pln_isss = det_pln_isss;
    }

    public double getDet_pln_afp() {
        return det_pln_afp;
    }

    public void setDet_pln_afp(double det_pln_afp) {
        this.det_pln_afp = det_pln_afp;
    }

    public byte getDet_pln_cantidad_horas_extra_diurnas() {
        return det_pln_cantidad_horas_extra_diurnas;
    }

    public void setDet_pln_cantidad_horas_extra_diurnas(byte det_pln_cantidad_horas_extra_diurnas) {
        this.det_pln_cantidad_horas_extra_diurnas = det_pln_cantidad_horas_extra_diurnas;
    }

    public byte getDet_pln_cantidad_horas_extra_nocturnas() {
        return det_pln_cantidad_horas_extra_nocturnas;
    }

    public void setDet_pln_cantidad_horas_extra_nocturnas(byte det_pln_cantidad_horas_extra_nocturnas) {
        this.det_pln_cantidad_horas_extra_nocturnas = det_pln_cantidad_horas_extra_nocturnas;
    }

    public double getDet_pln_bono_horas_extra() {
        return det_pln_bono_horas_extra;
    }

    public void setDet_pln_bono_horas_extra(double det_pln_bono_horas_extra) {
        this.det_pln_bono_horas_extra = det_pln_bono_horas_extra;
    }

    public int getDet_pln_id_pln() {
        return det_pln_id_pln;
    }

    public void setDet_pln_id_pln(int det_pln_id_pln) {
        this.det_pln_id_pln = det_pln_id_pln;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }
    
}
