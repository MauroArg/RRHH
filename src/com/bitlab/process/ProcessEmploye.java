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
    public static void getEmploye(BufferedReader in, PrintWriter out, Scanner read)
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
    
    public static void showEmployAvailable()
    {
        System.out.println("\nNo\tCodigo\tNombres\t\tApellidos\t\tCorreo\t\tDireccion\t\tTelefono\t\tDUI\t\tNIT\t\tSueldo\t\tJefe\t\tDepartamento");
        for (int i = 0; i < employeListJSON.size(); i++) 
        {
            if (employeListJSON.get(i).getEmp_estado() == 0) 
            {
                System.out.println(i + 1 + "\t" + employeListJSON.get(i).getEmp_nombres()+ "\t" + employeListJSON.get(i).getEmp_apellidos()
                    + "\t" + employeListJSON.get(i).getEmp_correo() + "\t" + employeListJSON.get(i).getEmp_direccion() + "\t" + employeListJSON.get(i).getEmp_telefono() +
                        "\t" + employeListJSON.get(i).getEmp_dui() + "\t" + employeListJSON.get(i).getEmp_nit() + "\t" + "$ " + employeListJSON.get(i).getEmp_sueldo() + 
                        "\t" + employeListJSON.get(i).getEmp_jef_nombre() + "\t" + employeListJSON.get(i).getDepartament().getDep_nombre() + "\n");
            }
        }
    }
    
    public static void createEmploy(BufferedReader in, PrintWriter out, Scanner read)
    {
        //Needed variables
        String response = "";
        String option;
        
        //List of users tyoe
        ArrayList<Employe> list = new ArrayList();
        
        //Instance objects
        Employe employe = new Employe();
        Employe boss = new Employe();
        Departament departament = new Departament();
        
        //Variables to create the JSON
        JSONObject detailsJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        
        //Request all data to add Employe
        System.out.print("Ingrese el nombre del empleado: ");
        employe.setEmp_nombres(read.nextLine());
        System.out.print("Ingrese el apellido del empleado: ");
        employe.setEmp_apellidos(read.nextLine());
        System.out.print("Ingrese el codigo del empleado: ");
        employe.setEmp_codigo(read.nextLine());
        System.out.print("Ingrese el correo del empleado: ");
        employe.setEmp_correo(read.nextLine());
        System.out.print("Ingrese la direccion del empleado: ");
        employe.setEmp_direccion(read.nextLine());
        System.out.print("Ingrese el telefono del empleado: ");
        employe.setEmp_telefono(read.nextLine());
        System.out.print("Ingrese el DUI el empleado: ");
        employe.setEmp_dui(read.nextLine());
        System.out.print("Ingrese el NIT del empleado: ");
        employe.setEmp_nit(read.nextLine());
        System.out.print("Ingrese el sueldo del empleado: $");
        employe.setEmp_sueldo(Double.parseDouble(read.nextLine()));
        showEmployAvailable();
        System.out.println("Ingrese el numero del jefe del empleado: ");
        option = read.nextLine();
        int index = Integer.parseInt(option);
        employe.setEmp_jef_id(employeListJSON.get(index -1).getEmp_id());
        
        ProcessDepartament.showDepartament();
        option = read.nextLine();
        int index2 = Integer.parseInt(option);
        departament = new Departament();
        departament.setDep_id(ProcessDepartament.departamentListJSON.get(index -1).getDep_id());
        employe.setDepartament(departament);
        
        
        
        //Add data of the list to a json
        JSONObject json = new JSONObject();
        json.put("nombres", employe.getEmp_nombres());
        json.put("apellidos", employe.getEmp_apellidos());
        json.put("codigo", employe.getEmp_codigo());
        json.put("correo", employe.getEmp_correo());
        json.put("telefono",employe.getEmp_telefono());
        json.put("dui", employe.getEmp_dui());
        json.put("nit", employe.getEmp_nit());
        json.put("sueldo", employe.getEmp_sueldo());
        json.put("jefe_id", employe.getEmp_jef_id());
        json.put("dep_id", employe.getDepartament().getDep_id());
        
        //Send the json
        detailsJson.put("employe", json);
        out.println(detailsJson);   
        try 
        {
            System.out.println(in.readLine());
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(ProcessUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void deleteEmploy(BufferedReader in, PrintWriter out, Scanner read)
    {
        String very;
        String empl;
        int id = 0;
        showEmploy();
        boolean log = true;
        boolean flag = true;
        
        while(flag)
        {
            System.out.println("Ingrese el numero del empleado que desea desactivar");
            empl = read.nextLine();

            if (isNumeric(empl)) {
                flag = false;
                try {
                    for (int i = 0; i < employeListJSON.size(); i++) {
                        if (i == Integer.parseInt(empl) - 1) {
                            id = employeListJSON.get(i).getEmp_id();
                            System.out.print("¿Esta seguro que desea desactivar este empleado: " + employeListJSON.get(i).getEmp_nombres() + " " + employeListJSON.get(i).getEmp_apellidos()+  "?"
                                    + "\n [1]Si, borrar \n[2]No,cancelar");
                        }
                    }
                    very = read.nextLine();

                    while (log) {
                        switch (very) {
                            case "1":
                                log = false;
                                out.println(id);
                                System.out.println(in.readLine());
                                break;
                            case "2":
                                log = false;
                                out.println("cancel");
                                break;
                            default:
                                System.out.println("Por favor ingrese un valor valido");
                                break;
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(ProcessUser.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("Por favor ingrese un valor numerico");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ProcessUser.class.getName()).log(Level.SEVERE, null, ex);
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
