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
    
    public static void updateEmploy(BufferedReader in, PrintWriter out, Scanner read)
    {
        int id = 0;
        String nombres = "";
        String apellidos = "";
        String direccion = "";
        String dui = "";
        String nit = "";
        Double sueldo = 0.0;
        String codigo = "";
        String correo = "";
        String telefono = "";
        byte estado = 0;
        int depId = 0;
        int jefeId = 0;
        
        
        String empl = "";
        String change = "";
        String campo  = "";
        String option = "";
        
        //Needed variables to JSON
        ArrayList<Employe> list = new ArrayList();
        JSONObject detailsJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        
        //Instance of Objects
        Employe jefe = new Employe();
        Employe employe = new Employe();
        Departament departament = new Departament();
        
        //Show all users
        showEmploy();
        
        //Request the user to update
        System.out.print("Ingrese el empleado que desea Modificar ");
        empl = read.nextLine();
        boolean log = true;
        boolean flag = true;
        while(flag)
        {
            if (isNumeric(empl)) 
            {
                flag = false;
                for (int i = 0; i < employeListJSON.size(); i++) 
                {
                    if (i == Integer.parseInt(empl) - 1) 
                    {
                        id = employeListJSON.get(i).getEmp_id();
                        nombres = employeListJSON.get(i).getEmp_nombres();
                        apellidos = employeListJSON.get(i).getEmp_apellidos();
                        codigo = employeListJSON.get(i).getEmp_codigo();
                        dui = employeListJSON.get(i).getEmp_dui();
                        nit = employeListJSON.get(i).getEmp_nit();
                        direccion = employeListJSON.get(i).getEmp_direccion();
                        correo = employeListJSON.get(i).getEmp_correo();
                        sueldo = employeListJSON.get(i).getEmp_sueldo();
                        estado = employeListJSON.get(i).getEmp_estado();
                        jefeId = employeListJSON.get(i).getEmp_jef_id();
                        telefono = employeListJSON.get(i).getEmp_telefono();
                        depId = employeListJSON.get(i).getDepartament().getDep_id();
                    }
                }

                while (log) 
                {
                    System.out.println("Que desea editar del empleado " + nombres);
                    System.out.println("[1] Nombre del empleado\n[2] Apellido del empleado\n[3] Codigo del empleado\n[4] DUI del empleado\n[5] NIT del empleado\n[6] Direccion del empleado" + 
                            "\n[7] Correo del empleado\n[8] Sueldo del empleado\n[9] Jefe del empleado\n[10] Telefono del empleado\n[11] Departamento del empleado");
                    System.out.print("Ingrese un valor: ");
                    campo = read.nextLine();
                    switch(campo)
                    {
                        case "1":
                            log = false;
                            System.out.print("Ingrese el nuevo nombre del empleado: ");
                            nombres = read.nextLine();
                            break;
                        case "2":
                            log = false;
                            System.out.print("Ingrese el nuevo apellido del empleado: ");
                            apellidos = read.nextLine();
                            break;
                        case "3":
                            log = false;
                            System.out.print("Ingrese el nuevo codigo del empleado: ");
                            codigo = read.nextLine();
                            break;
                        case "4":
                            log = false;
                            System.out.print("Ingrese el DUI del empleado: ");
                            dui = read.nextLine();
                            break;
                        case "5":
                            log = false;
                            System.out.print("Ingree el nuevo NIT del empleado: ");
                            nit = read.nextLine();
                            break;
                        case "6":
                            log = false;
                            System.out.print("Ingrese la nueva direccion del empleado: ");
                            direccion = read.nextLine();
                            break;
                        case "7":
                            log = false;
                            System.out.print("Ingrese el nuevo correo del empleado: ");
                            direccion = read.nextLine();
                            break;
                        case "8":
                            log = false;
                            System.out.print("Ingrese el nuevo sueldo del empleado: ");
                            sueldo = Double.parseDouble(read.nextLine());
                            break;
                        case "9":
                            log = false;
                            showEmployAvailable();
                            System.out.print("Ingrese el numero del nuevo jefe del empleado: ");
                            option = read.nextLine();
                            int index = Integer.parseInt(option);
                            employe.setEmp_jef_id(employeListJSON.get(index-1).getEmp_id());
                            break;
                        case "10":
                            log = false;
                            System.out.print("Ingrese el nuevo telefono del empleadoo: ");
                            telefono = read.nextLine();
                            break;
                        case "11":
                            log = false;
                            ProcessDepartament.showDepartament();
                            System.out.print("Ingrese el numero del nuevo departamento del empleado: ");
                            option = read.nextLine();
                            int index2 = Integer.parseInt(option);
                            departament = new Departament();
                            departament.setDep_id(ProcessDepartament.departamentListJSON.get(index2-1).getDep_id());
                            break;
                        default:
                            System.out.println("Por favor ingrese un valor valido");
                            break;
                    }
                }
                employe.setEmp_id(id);
                employe.setEmp_nombres(nombres);
                employe.setEmp_apellidos(apellidos);
                employe.setEmp_direccion(direccion);
                employe.setEmp_correo(correo);
                employe.setEmp_codigo(codigo);
                employe.setEmp_dui(dui);
                employe.setEmp_nit(nit);
                employe.setEmp_estado(estado);
                employe.setDepartament(departament);
                employe.setEmp_telefono(telefono);
                employe.setEmp_sueldo(sueldo);
                employe.setEmp_jef_id(jefeId);
                
                
                //Add data to a json
                JSONObject json = new JSONObject();
                json.put("id", employe.getEmp_id());
                json.put("nombres", employe.getEmp_nombres());
                json.put("apellidos", employe.getEmp_apellidos());
                json.put("codigo", employe.getEmp_codigo());
                json.put("dui", employe.getEmp_dui());
                json.put("dui", employe.getEmp_dui());
                json.put("nit", employe.getEmp_nit());
                json.put("telefono", employe.getEmp_telefono());
                json.put("direccion", employe.getEmp_direccion());
                json.put("telefono", employe.getEmp_telefono());
                json.put("dep_id", employe.getDepartament().getDep_id());
                json.put("jefe_id", employe.getEmp_jef_id());
                json.put("sueldo", employe.getEmp_sueldo());
                
                //Send json with the data
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
            else
            {
                System.out.println("Por favor ingrese un valor numerico");
                try 
                {
                    Thread.sleep(1000);
                } 
                catch (InterruptedException ex) 
                {
                    Logger.getLogger(ProcessUser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
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
