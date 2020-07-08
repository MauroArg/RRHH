/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitlab.process;

import com.bitlab.entity.Departament;
import com.bitlab.entity.Employe;
import com.bitlab.entity.Payroll;
import com.bitlab.entity.PayrollDetail;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Mauricio Argumedo
 */
public class ProcessPayroll 
{
    static Payroll payroll = new Payroll();
    static Employe employe = new Employe();
    static PayrollDetail payrollDetail = new PayrollDetail();
    static ArrayList<Payroll> payrollListJSON = new ArrayList();   
    static ArrayList<PayrollDetail> payrollDetailListJSON = new ArrayList();
    
    public static void getPayroll(BufferedReader in, PrintWriter out, Scanner read)
    {
        try {
            String serverResponse = "";//Server petitions
            String logResponse = "";// log result
            JSONParser parser = new JSONParser();//JSON Parser
            employe = new Employe();
            payroll = new Payroll();
            
            
            payrollListJSON.clear();
            logResponse = in.readLine();
            JSONObject obj = (JSONObject) parser.parse(logResponse);
            JSONArray array = (JSONArray) obj.get("payrolls");
            for (Object item : array) 
            {
                payroll = new Payroll();
                JSONObject object = (JSONObject) item;
                payroll.setPln_id(Integer.parseInt(object.get("id").toString()));
                payroll.setPln_fecha(object.get("fecha").toString());
                payroll.setPln_total(Double.parseDouble(object.get("total").toString()));
                payrollListJSON.add(payroll);
            }
        } catch (ParseException | IOException ex) 
        {
            Logger.getLogger(ProcessPayroll.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void showPayrol()
    {
        String estado = "";
        System.out.println("\nNo\tFecha\tTotal\tEstado");
        for (int i = 0; i < payrollListJSON.size(); i++) 
        {
            if (payrollListJSON.get(i).getPln_estado() == 0) 
            {
                estado = "Pagado";
            }
            else
            {
                estado = "Pendiente";
            }
            System.out.println(i + 1 + "\t" + payrollListJSON.get(i).getPln_fecha() + "\t" + payrollListJSON.get(i).getPln_total()
            + "\t" + estado);
        }
    }
    
    public static void showPending()
    {
        String estado = "";
        System.out.println("\nNo\tFecha\tTotal");
        for (int i = 0; i < payrollListJSON.size(); i++) 
        {
            if (payrollListJSON.get(i).getPln_estado() == 1) 
            {
                System.out.println(i + 1 + "\t" + payrollListJSON.get(i).getPln_fecha() + "\t" + payrollListJSON.get(i).getPln_total());
            }
        }
    }
    
    public static void showPayrollWithMenu(BufferedReader in, PrintWriter out, Scanner read)
    {
        String estado = "";
        String resp = "";
        boolean flag = true;
        System.out.println("\nNo\tFecha\t\tTotal\t\tEstado");
        for (int i = 0; i < payrollListJSON.size(); i++) 
        {
            if (payrollListJSON.get(i).getPln_estado() == 0) 
            {
                estado = "Pagado";
            }
            else
            {
                estado = "Pendiente";
            }
            System.out.println(i + 1 + "\t" + payrollListJSON.get(i).getPln_fecha() + "\t" + payrollListJSON.get(i).getPln_total()
            + "\t" + estado);
        }
        while(flag)
        {
            System.out.println("¿Que desea hacer?\n[1]Ver un planilla especifica\n[2]Crear una nueva planilla\n[3]Pagar planilla\n[4] Salir");
            System.out.print("Escoga una opcion: ");
            resp = read.nextLine();
            switch(resp)
            {
                case "1":
                    out.println("getPayrollDetail");
                    getDetailPayroll(in, out, read);
                    break;
                case "2":
                    out.println("generate");
                    generatePayroll(in, out, read);
                    break;
                case "3":
                    out.println("payPayroll");
                    payPayroll(in, out, read);
                    break;
                case "4":
                    flag = false;
                    out.println("exit");
                    break;
            }
        }
    }
    
    public static void getDetailPayroll(BufferedReader in, PrintWriter out, Scanner read)
    {
            String logResponse = "";// log result
            JSONParser parser = new JSONParser();//JSON Parser
            int id = 0;
            String option;
            boolean flag = true;
            showPayrol();
            
            while(flag) 
            {
                System.out.print("Ingrese el numero de la planilla que desea ver: ");
                option = read.nextLine();
                if (isNumeric(option))
                {
                    flag = false;
                    id = payrollListJSON.get(Integer.parseInt(option) -1).getPln_id();
                    out.println(id);
                }
                else
                {
                    System.out.println("Por favor ingrese un valor valido");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ProcessPayroll.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            payrollDetail = new PayrollDetail();
            payrollDetailListJSON.clear();
            
        try 
        {
            logResponse = in.readLine();
            JSONObject obj = (JSONObject) parser.parse(logResponse);
            JSONArray array = (JSONArray) obj.get("detail");
            
            System.out.println("No\tEmpleado\t\t\tAFP\tISSS\tRenta\tTotal descuentos\tHoras extras diurnas\tHoras extras nocturnas\tBono por horas extra"
                    + "\tTotal a pagar");
            
            int iterator = 0;
            for (Object item : array) 
            {
                employe = new Employe();
                payrollDetail = new PayrollDetail();
                JSONObject object = (JSONObject) item;
                payrollDetail.setDet_pln_id(Integer.parseInt(object.get("id").toString()));
                payrollDetail.setDet_pln_afp(Double.parseDouble(object.get("afp").toString()));
                payrollDetail.setDet_pln_cantidad_horas_extra_diurnas(Byte.parseByte(object.get("diurnas").toString()));
                payrollDetail.setDet_pln_cantidad_horas_extra_nocturnas(Byte.parseByte(object.get("nocturnas").toString()));
                payrollDetail.setDet_pln_bono_horas_extra(Byte.parseByte(object.get("bono").toString()));
                payrollDetail.setDet_pln_isss(Double.parseDouble(object.get("isss").toString()));
                payrollDetail.setDet_pln_renta(Double.parseDouble(object.get("renta").toString()));
                payrollDetail.setDet_pln_total(Double.parseDouble(object.get("total").toString()));
                payrollDetail.setDet_pln_total_descuentos(Double.parseDouble(object.get("descuentos").toString()));
                payrollDetail.setDet_pln_id_pln(Integer.parseInt(object.get("planilla").toString()));
                id = Integer.parseInt(object.get("empleado").toString());
                for(int i = 0; i < ProcessEmploye.employeListJSON.size(); i++)
                {
                    if (id == ProcessEmploye.employeListJSON.get(i).getEmp_id()) 
                    {
                        employe.setEmp_id(id);
                        employe.setEmp_nombres(ProcessEmploye.employeListJSON.get(i).getEmp_nombres());
                    }
                }
                payrollDetail.setEmploye(employe);
                
                System.out.println(iterator+1 + "\t" + payrollDetail.getEmploye().getEmp_nombres() + "\t" + payrollDetail.getDet_pln_afp() + "\t"
                + payrollDetail.getDet_pln_isss() + "\t" + payrollDetail.getDet_pln_total_descuentos() + "\t" + payrollDetail.getDet_pln_cantidad_horas_extra_diurnas()
                + "\t" + payrollDetail.getDet_pln_cantidad_horas_extra_nocturnas() + "\t" + payrollDetail.getDet_pln_bono_horas_extra() + "\t $" + payrollDetail.getDet_pln_total());
                iterator ++;
                
            }
        } 
        catch (IOException | ParseException ex) {
            Logger.getLogger(ProcessPayroll.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    public static void generatePayroll(BufferedReader in, PrintWriter out, Scanner read)
    {
        try {
            if (in.readLine().equals("available"))
            {
                String readDiurnas, readNocturnas;
                boolean flag = true;
                int diurnas = 0, nocturnas = 0;//- Cantidad de horas extras
                Double totalP = 0.0;
                Double sueldo = 0.0;//-Sueldo neto = sueldoEmpleado + (bonoHorasDiurnas) + (BonoHorasNocturnas);
                Double bonoDiurnas = 0.0;//- Bono total por horas diurnas
                Double bonoNocturnas = 0.0;///- Bono total por horas nocturnas
                Double bonoTotal = 0.0;//-Bono total por horas extra
                Double sueldoI = 0.0;//-Sueldo exacto del empelado
                Double sueldoPorHora = 0.0;//-Calculo de sueldo por hora
                //-Bonos por horas extras y sueldo para descuentos
                //-Sueldo por hora
                JSONArray data = new JSONArray();

                for(int i = 0; i < ProcessEmploye.employeListJSON.size(); i++)
                {
                    sueldoI = ProcessEmploye.employeListJSON.get(i).getEmp_sueldo();
                    sueldoPorHora = (sueldoI / 30) / 8;
                    //-Bono por hora extra diurna
                    flag = true;
                    while(flag)
                    {
                        System.out.print("Ingrese la cantidad de horas extra diurnas del empleado (si no hizo coloque 0)");
                        readDiurnas = read.nextLine();
                        if (isNumeric(readDiurnas)) 
                        {
                            flag = false;
                            diurnas = Integer.parseInt(readDiurnas);
                        }
                        else
                        {
                            System.out.println("Por favor ingrese un valor valido");
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(ProcessPayroll.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    bonoDiurnas = diurnas * (sueldoPorHora * 2);
                    //-Bono por hora extra nocturna

                    flag = true;
                    while(flag)
                    {
                        System.out.print("Ingrese la cantidad de horas extra nocturnas del empleado (si no hizo coloque 0)");
                        readNocturnas = read.nextLine();
                        if (isNumeric(readNocturnas)) 
                        {
                            flag = false;
                            diurnas = Integer.parseInt(readNocturnas);
                        }
                        else
                        {
                            System.out.println("Por favor ingrese un valor valido");
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(ProcessPayroll.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    bonoNocturnas = nocturnas * (sueldoPorHora * 2.25);
                    //-Bono total por horas extras
                    bonoTotal = bonoDiurnas + bonoNocturnas;
                    //-sueldo para descuentos
                    sueldo = sueldoI + bonoTotal;
                    //-Variables para descuentos
                    Double nSueldo = 0.0;//- Sueldo para renta
                    Double afp = 0.0, isss = 0.0, renta = 0.0, cuota = 0.0;// descuentos
                    Double tDescuentos = 0.0,total = 0.0;//- Total a pagar(Sueldo - descuentos) y total descuetnos
                    //AFP 7.25%
                    afp = sueldo * 0.0725;

                    //ISSS 3% MÃ�XIMO $30 MENSUALES
                    isss = sueldo * 0.03;
                    if(isss>30.0){isss=30.0;}

                    //- SUELDO TO RENTA
                    nSueldo = sueldo - afp - isss;

                    //----------- RENTA --------------
                    //TRAMO 1 
                    //- DE 0.01 A 472.0
                    if(nSueldo>=0.01 && nSueldo<=472.0){
                        cuota = 0.0;
                        renta = 0.0;
                    }

                    //TRAMO 2
                    //- DE 472.01 A 895.24
                    if(nSueldo>=472.01 && nSueldo<=895.24){
                        cuota = 17.67;
                        renta =((sueldo - 472.0) * 0.1) + cuota;
                    }

                    //TRAMO 3
                    //- DE 895.25 A 2038.10
                    if(nSueldo>=895.25 && nSueldo<=2038.10){
                        cuota = 60.00;
                        renta =((sueldo - 895.24) * 0.2) + cuota;
                    }

                    //TRAMO 4
                    //- DE 2038.11 ->
                    if(nSueldo>=2038.11){
                        cuota = 288.57;
                        renta =((sueldo - 2038.10) * 0.3) + cuota;
                    }

                    //-Total de descuentos
                    tDescuentos = renta + isss + afp;
                    //-Total a pagar al empleado
                    total = sueldo - tDescuentos;
                    totalP += total;
                    JSONObject ob = new JSONObject();
                    ob.put("empleado",ProcessEmploye.employeListJSON.get(i).getEmp_id());
                    ob.put("total", total);
                    ob.put("diurnas", diurnas);
                    ob.put("nocturnas",nocturnas);
                    ob.put("descuentos", tDescuentos);
                    ob.put("afp", afp);
                    ob.put("renta", renta);
                    ob.put("isss", isss);
                    ob.put("bono", bonoTotal);
                    data.add(ob);
                }
                JSONObject json = new JSONObject();
                json.put("total", totalP);
                json.put("detail", data);
                out.println(json.toJSONString());
                try 
                {
                    System.out.println(in.readLine());
                } catch (IOException ex) {
                    Logger.getLogger(ProcessPayroll.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
            {
                System.out.println("Ya fue creada la planilla de este mes");
            }
        } catch (IOException ex) {
            Logger.getLogger(ProcessPayroll.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void payPayroll(BufferedReader in, PrintWriter out, Scanner read)
    {
        boolean flag = true;
        boolean flag2 = true;
        String option;
        String very;
        
        showPending();
        while(flag)
        {
            System.out.print("Ingrese el numero de la planilla que desea pagar: ");
            option = read.nextLine();
            if (isNumeric(option) && Integer.parseInt(option) <= payrollListJSON.size() && Integer.parseInt(option) > 0) 
            {
                flag = false;
                while(flag2)
                {
                    System.out.println("Quiere realizar el pago de la planilla de " + payrollListJSON.get(Integer.parseInt(option) -1).getPln_fecha() + "\n[1]Si\n[2]Cancelar");
                    System.out.print("Eliga una opcion");
                    very = read.nextLine();
                    switch(very)
                    {
                        case "1":
                            out.println(payrollListJSON.get(Integer.parseInt(option) -1).getPln_id()); 
                            try 
                            {
                                System.out.println(in.readLine());
                                Thread.sleep(1000);
                            } 
                            catch (InterruptedException | IOException ex) 
                            {
                                Logger.getLogger(ProcessPayroll.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            flag2 = false;
                            break;


                        case "2":
                            flag2 = false;
                            break;
                        default:
                            System.out.println("Por favor ingrese un valor valido");
                        {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(ProcessPayroll.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                            break;

                    }
                }
            }
            else
            {
                System.out.println("Por favor ingrese un valor valido");
                try 
                {
                    Thread.sleep(1000);
                } 
                catch (InterruptedException ex) 
                {
                    Logger.getLogger(ProcessPayroll.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    } 
    
    //Verify if the input is a number
    private static boolean isNumeric(String string)
    {
	try 
        {
		Integer.parseInt(string);
		return true;
	} 
        catch (NumberFormatException nfe)
        {
		return false;
	}
    }
    
    
}
