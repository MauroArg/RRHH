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
    
    //Entity classes
    Departament   departament = new Departament();
    Employe       employe = new Employe();
    Payroll       payroll = new Payroll();
    PayrollDetail payrollDetail = new PayrollDetail();
    Rol           rol = new Rol();
    User          user = new User();
    
    //List with the data
    static ArrayList<Departament> departamentListJSON = new ArrayList();
    static ArrayList<Employe> employeListJSON = new ArrayList();
    static ArrayList<Payroll> payrollListJSON = new ArrayList();
    static ArrayList<PayrollDetail> payrollDetailListJSON = new ArrayList();
    static ArrayList<Rol> rolListJSON = new ArrayList();
    static ArrayList<User> userListJSON = new ArrayList();
    
    //Comparing text variables
    String nombre;
    String apellido;
    String direccion;
    String correo;
    String estado;
    String longitud;
    static String depto;
    
    
    
    
    
    public static void getEmploye(BufferedReader in, PrintWriter out, Scanner read)
    {
        String serverResponse = "";//Server petitions
        String logResponse = "";// log result
        JSONParser parser = new JSONParser();//JSON Parser
        Employe employe = new Employe();
        Departament departament = new Departament();
        try
        {
            employeListJSON.clear();
            logResponse = in.readLine();
            JSONObject obj = (JSONObject) parser.parse(logResponse);
            JSONArray array = (JSONArray) obj.get("departaments");
            for (Object item : array) 
            {
                employe = new Employe();
                JSONObject object = (JSONObject) item;
                employe.setEmp_id(Integer.parseInt(object.get("id").toString()));
                employe.setEmp_codigo(object.get("codigo").toString());
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
        User user = new User();
        Rol rol = new Rol();
        
        try 
        {
            userListJSON.clear();
            logResponse = in.readLine();
            JSONObject obj = (JSONObject) parser.parse(logResponse);
            JSONArray array = (JSONArray) obj.get("users");
            
            int i = 0;
            for (Object item : array)
            {
                user = new User();
                JSONObject object = (JSONObject) item;
                user.setUs_id(Integer.parseInt(object.get("id").toString()));
                user.setUs_usuario(object.get("username").toString());
                user.setUs_correo(object.get("correo").toString());
                rol.setRol_id(Integer.parseInt(object.get("rol_id").toString()));
                rol.setRol_nombre(object.get("rol_nombre").toString());
                user.setRol(rol);
                userListJSON.add(user);
                i++;
            }
            
        } 
        catch (IOException | ParseException ex) 
        {
            Logger.getLogger(AppProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    public void showEmploy()
    {
        System.out.println("\nNo\tNombres\tApellios\tDUI\tNIT\tTelefono\tSueldo\tDireccion\tEstado\tDepartamento");
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
                
                
                System.out.println(i+1 + "\t" +
                        nombre + "\t" + apellido + "\t" + employeListJSON.get(i).getEmp_dui() + "\t" +
                        employeListJSON.get(i).getEmp_nit() + "\t" + employe.getEmp_telefono() + "\t" + 
                        "$ " +employeListJSON.get(i).getEmp_sueldo() + "\t" + direccion + "\t" + estado +
                        "\t" + employeListJSON.get(i).getDepartament().getDep_nombre());
            }
    }
    
    public static void showUser()
    {
        System.out.println("\nNo\tNombre\tCorreo\tRol");
        for(int i = 0; i < userListJSON.size(); i++)
        {
            System.out.println(i+1 + "\t" + userListJSON.get(i).getUs_usuario() + "\t" + 
                    userListJSON.get(i).getUs_correo() + "\t" + userListJSON.get(i).getRol().getRol_nombre());
        }
    }
    
   
    
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
