/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitlab.process;

import com.bitlab.entity.Rol;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.System.Logger;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Mauricio
 */
public class ProcessRol 
{
   
    public static ArrayList<Rol> rolListJSON = new ArrayList();//List of rols
    public static String depto; //Option selected to edit
    
    //Obtain all rols from socket
    public static void getRol(BufferedReader in, PrintWriter out, Scanner read)
    {
        String serverResponse = "";//Server petitions
        String logResponse = "";// log result
        JSONParser parser = new JSONParser();//JSON Parser
        Rol rol = new Rol();
        try 
        {
            rolListJSON.clear();
            logResponse = in.readLine();
            JSONObject obj = (JSONObject) parser.parse(logResponse);
            JSONArray array = (JSONArray) obj.get("rols");
            for (Object item : array) 
            {
                rol = new Rol();
                JSONObject object = (JSONObject) item;
                rol.setRol_id(Integer.parseInt(object.get("id").toString()));
                rol.setRol_nombre(object.get("nombre").toString());
                rolListJSON.add(rol);
            }
        } 
        catch (IOException | ParseException ex) 
        {
            java.util.logging.Logger.getLogger(ProcessRol.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Show all rols
    public static void showRol()
    {
        System.out.println("\nNo\tRol");
        for (int i = 0; i < rolListJSON.size(); i++) 
        {
            System.out.println(i + 1 + "\t" + rolListJSON.get(i).getRol_nombre());
        }
    }
    //Create a new rol
    public static void createRol(BufferedReader in, PrintWriter out, Scanner read)
    {
        String response = "";
            
            
        System.out.println("Ingrese el nombre del departamento: ");
        out.println(read.nextLine());
            
        try 
        {
            response = in.readLine();

            getRol(in, out, read);
            System.out.println(response);
        } 
        catch (IOException ex) 
        {
            java.util.logging.Logger.getLogger(ProcessRol.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void updateRol(BufferedReader in, PrintWriter out, Scanner read)
    {
        ArrayList<Rol> list = new ArrayList();
        JSONObject data = new JSONObject();
        JSONObject rol = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        
        
        
        showRol();
        System.out.println("¿Que departamanto quiere modificar?");
        depto = read.nextLine();
        int id;
        String nombre;
        if (isNumeric(depto)) 
        {
            for(int i = 0; i < rolListJSON.size(); i++)
            {
                if (i == Integer.parseInt(depto)-1) 
                {
                    id = rolListJSON.get(i).getRol_id();
                    System.out.println("Ingrese el nuevo nombre: ");
                    nombre = read.nextLine();
                    
                    rol.put("id", id);
                    rol.put("nombre", nombre);
                    data.put("departament", rol);
                    out.println(data.toJSONString());
                }
            }
        }
        
        try 
        {
            if (in.readLine().equals("exitoso"))
            {
                getRol(in, out, read);
                showRol();
            }
        } 
        catch (IOException ex) 
        {
            java.util.logging.Logger.getLogger(ProcessRol.class.getName()).log(Level.SEVERE, null, ex);
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
