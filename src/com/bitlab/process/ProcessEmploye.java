/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitlab.process;

import com.bitlab.entity.Departament;
import com.bitlab.entity.Employe;
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
public class ProcessEmploye {
    static Employe employe = new Employe();
    static Departament departament = new Departament();
    static ArrayList<Employe> employeListJSON = new ArrayList();    
    
    //Obtain departament from socket
    public static void getDep(BufferedReader in, PrintWriter out, Scanner read)
    {
            String serverResponse = "";//Server petitions
            String logResponse = "";// log result
            JSONParser parser = new JSONParser();//JSON Parser
            employe = new Employe();
            departament = new Departament();
            
            
        try {
            employeListJSON.clear();
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
                employe.setEmp_codigo(object.get("codigo").toString());
                employe.setEmp_correo(object.get("correo").toString());
                employe.setEmp_dui(object.get("dui").toString());
                employe.setEmp_telefono(object.get("telefono").toString());
                employe.setEmp_nit(object.get("nit").toString());
                employe.setEmp_estado(Byte.parseByte(object.get("estado").toString()));
                employe.setEmp_jef_id(Integer.parseInt(object.get("jefe_id").toString()));
                employe.setEmp_sueldo(Double.parseDouble(object.get("sueldo").toString()));
                employe.setEmp_jef_nombre(object.get("jefe_nombre").toString());
                departament.setDep_id(Integer.parseInt(object.get("dep_id").toString()));
                departament.setDep_nombre(object.get("dep_nombre").toString());
                employe.setDepartament(departament);
                employeListJSON.add(employe);
            }
        } catch (IOException | ParseException ex) 
        {
            Logger.getLogger(ProcessEmploye.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void showEmploy()
    {
        System.out.println("\nNo\tCodigo\tNombres\t\tApellidos\t\tCorreo\t\tDireccion\t\tTelefono\t\tDUI\t\tNIT\t\tSueldo\t\tJefe\t\tDepartamento");
        for (int i = 0; i < employeListJSON.size(); i++) 
        {
            System.out.println(i + 1 + "\t" + employeListJSON.get(i).getEmp_nombres()+ "\t" + employeListJSON.get(i).getEmp_apellidos()
            + "\t" + employeListJSON.get(i).getEmp_correo() + "\t" + employeListJSON.get(i).getEmp_direccion() + "\t" + employeListJSON.get(i).getEmp_telefono() +
                    "\t" + employeListJSON.get(i).getEmp_dui() + "\t" + employeListJSON.get(i).getEmp_nit() + "\t" + "$ " + employeListJSON.get(i).getEmp_sueldo() + 
                    "\t" + employeListJSON.get(i).getEmp_jef_nombre() + "\t" + employeListJSON.get(i).getDepartament().getDep_nombre() + "\n");
        }
    }
    
    public static void createEmploy(BufferedReader in, PrintWriter out, Scanner read)
    {
        
    }
    
    public static void deleteEmploy(BufferedReader in, PrintWriter out, Scanner read)
    {
        String very;
        String empl;
        showEmploy();
        boolean log = true;
        System.out.println("Ingrese el numero del empleado que desea desactivar");
        empl = read.nextLine();
    }
}
