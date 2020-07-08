/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitlab.process;

import com.bitlab.app.Client;
import com.bitlab.entity.Departament;
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
public class ProcessDepartament 
{
    //Object of departaments
    Departament departament = new Departament();
    static ArrayList<Departament> departamentListJSON = new ArrayList();//List of departaments
    static String depto; //Option selected to edit
    
    //Obtain departament from socket
    public static void getDep(BufferedReader in, PrintWriter out, Scanner read)
    {
        String serverResponse = "";//Server petitions
        String logResponse = "";// log result
        JSONParser parser = new JSONParser();//JSON Parser
        Departament departament = new Departament();
        
        //Obtains the json with the departaments from the socket and parse them into a list
        try
        {
            departamentListJSON.clear();
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
    
    //Show all departaments
    public static void showDepartament()
    {
            System.out.println("\nNo\tNombre");
            for (int i = 0; i < departamentListJSON.size(); i++) 
            {
                System.out.println(i+1 + "\t" + departamentListJSON.get(i).getDep_nombre());
            }
    }
    
    //Add a new departament
    public static void createDep(BufferedReader in, PrintWriter out, Scanner read)
    {
        String response = "";
        
        System.out.print("Ingrese el nombre del departamento: ");
        out.println(read.nextLine());
        
        try 
        {
            response = in.readLine();

            getDep(in, out, read);
            System.out.println(response);
            
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(ProcessDepartament.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Update a selected departament
    public static void updateDep(BufferedReader in, PrintWriter out, Scanner read)
    {
        boolean flag = true;
        ArrayList<Departament> list = new ArrayList();
        JSONObject data = new JSONObject();
        JSONObject dep = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        
        
        
        showDepartament();
        while(flag)
        {
            System.out.print("¿Que departamanto quiere modificar?");
            depto = read.nextLine();
            int id;
            String nombre;
            if (isNumeric(depto) && Integer.parseInt(depto) <= departamentListJSON.size() && Integer.parseInt(depto) > 0) 
            {
                for(int i = 0; i < departamentListJSON.size(); i++)
                {
                    if (i == Integer.parseInt(depto)-1) 
                    {
                        flag = false;
                        id = departamentListJSON.get(i).getDep_id();
                        System.out.print("Ingrese el nuevo nombre: ");
                        nombre = read.nextLine();
                        dep.put("id", id);
                        dep.put("nombre", nombre);
                        data.put("departament", dep);
                        out.println(data.toJSONString());
                    }
                }
            }
            else
            {
                System.out.println("Por favor ingrese un valor valido");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ProcessDepartament.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        try 
        {
            if (in.readLine().equals("exitoso"))
            {
                getDep(in, out, read);
                showDepartament();
            }
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(ProcessDepartament.class.getName()).log(Level.SEVERE, null, ex);
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
