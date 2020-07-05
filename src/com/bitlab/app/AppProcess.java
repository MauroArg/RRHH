/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitlab.app;

import com.bitlab.entity.Departament;
import com.bitlab.entity.Employe;
import com.bitlab.entity.Payroll;
import com.bitlab.entity.PayrollDetail;
import com.bitlab.entity.Rol;
import com.bitlab.entity.User;
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
 * @author Maury
 */
public class AppProcess {
    
    Departament   departament = new Departament();
    Employe       employe = new Employe();
    Payroll       payroll = new Payroll();
    PayrollDetail payrollDetail = new PayrollDetail();
    Rol           rol = new Rol();
    User          user = new User();
    
    static ArrayList<Departament> departamentListJSON = new ArrayList();
    
    
    
    public static void getDep(BufferedReader in, PrintWriter out, Scanner read)
    {
        String serverResponse = "";//Server petitions
        String logResponse = "";// log result
        JSONParser parser = new JSONParser();//JSON Parser
        Departament departament = new Departament();
        
        try
        {
            logResponse = in.readLine();
            JSONObject obj = (JSONObject) parser.parse(logResponse);
            JSONArray array = (JSONArray) obj.get("departaments");
            for (Object item : array) 
            {
                departament = new Departament();
                JSONObject object = (JSONObject) item;
                departament.setDep_id(Integer.parseInt(object.get("id").toString()));
                departament.setDep_nombre(object.get("nombre").toString());
                departamentListJSON.add(departament);
            }

        } 
        catch (ParseException | IOException ex) 
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void getEmploye(BufferedReader in, PrintWriter out, Scanner read)
    {
        String serverResponse = "";//Server petitions
        String logResponse = "";// log result
        JSONParser parser = new JSONParser();//JSON Parser
        Employe employe = new Employe();
        Departament departament = new Departament();
        ArrayList<Employe> employeListJSON = new ArrayList();
        String nombre;
        String apellido;
        String direccion;
        String correo;
        String estado;
        String longitud;
        try
        {
            logResponse = in.readLine();
            JSONObject obj = (JSONObject) parser.parse(logResponse);
            JSONArray array = (JSONArray) obj.get("departaments");
            for (Object item : array) 
            {
                employe = new Employe();
                JSONObject object = (JSONObject) item;
                employe.setEmp_id(Integer.parseInt(object.get("id").toString()));
                employe.setEmp_nombres(object.get("nombres").toString());
                employe.setEmp_apellidos(object.get("apellidos").toString());
                employe.setEmp_dui(object.get("dui").toString());
                employe.setEmp_nit(object.get("nit").toString());
                employe.setEmp_correo(object.get("telefono").toString());
                employe.setEmp_sueldo(Double.parseDouble(object.get("sueldo").toString()));
                employe.setEmp_direccion(object.get("direccion").toString());
                employe.setEmp_estado(Byte.parseByte(object.get("estado").toString()));
                departament.setDep_id(Integer.parseInt(object.get("id_departamento").toString()));
                departament.setDep_nombre(object.get("nombre_departamento").toString());
                employe.setDepartament(departament);
                employeListJSON.add(employe);
            }
            
            for (int i = 0; i < employeListJSON.size(); i++) 
            {
                longitud = employeListJSON.get(i).getEmp_nombres();
                
                if (longitud.length() > 7) 
                {
                    nombre = longitud;
                }
                else
                {
                    nombre = longitud + "\t";
                }
                
                longitud = employeListJSON.get(i).getEmp_apellidos();
                if (longitud.length() > 7) 
                {
                    apellido = longitud;
                }
                else
                {
                    apellido = longitud + "\t";
                }
                
                longitud = employeListJSON.get(i).getEmp_direccion();
                if (longitud.length() > 7) 
                {
                    direccion = longitud;
                }
                else
                {
                    direccion = longitud + "\t";
                }
                
                longitud = employeListJSON.get(i).getEmp_correo();
                if (longitud.length() > 7) 
                {
                    correo = longitud;
                }
                else
                {
                    correo = longitud + "\t";
                }
                
                if (employeListJSON.get(i).getEmp_estado() == 0) 
                {
                    estado = "Activo";
                }
                else
                {
                    estado = "Inactivo";
                }
                
                System.out.println("\nId\tNombres\tApellios\tDUI\tNIT\tTelefono\tSueldo\tDireccion\tEstado\tDepartamento");
                
                System.out.println(employeListJSON.get(i).getEmp_id()+ "\t" +
                        nombre + "\t" + apellido + "\t" + employeListJSON.get(i).getEmp_dui() + "\t" +
                        employeListJSON.get(i).getEmp_nit() + "\t" + employe.getEmp_telefono() + "\t" + 
                        "$ " +employeListJSON.get(i).getEmp_sueldo() + "\t" + direccion + "\t" + estado +
                        "\t" + employeListJSON.get(i).getDepartament().getDep_nombre());
            }

        } 
        catch (ParseException | IOException ex) 
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void getUser(BufferedReader in, PrintWriter out, Scanner read)
    {
        String serverResponse = "";//Server petitions
        String logResponse = "";// log result
        JSONParser parser = new JSONParser();//JSON Parser
        
        
    }
    
    public static void showDepartament()
    {
            System.out.println("\nId\tNombre");
            for (int i = 0; i < departamentListJSON.size(); i++) 
            {
                System.out.println(departamentListJSON.get(i).getDep_id() + "\t" + departamentListJSON.get(i).getDep_nombre());
            }
    }
    
    
    public static void updateDep(BufferedReader in, PrintWriter out, Scanner read)
    {
        
    }
}
