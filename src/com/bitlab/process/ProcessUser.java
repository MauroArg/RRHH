/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitlab.process;

import com.bitlab.entity.Rol;
import com.bitlab.entity.User;
import com.bitlab.utility.Encryption;
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
 * @author Mauricio
 */
public class ProcessUser 
{
    static ArrayList<User> userListJSON = new ArrayList();//List of user
    static String usr; //Option selected to edit
    
    //Obtains all users
    public static void getUser(BufferedReader in, PrintWriter out, Scanner read)
    {
        String serverResponse = "";//Server petitions
        String logResponse = "";// log result
        JSONParser parser = new JSONParser();//JSON Parser
        User user = new User();//User instance
        Rol rol = new Rol(); //Rol instance
        
        try {
            userListJSON.clear();
            logResponse = in.readLine();
            JSONObject obj = (JSONObject) parser.parse(logResponse);
            JSONArray array = (JSONArray) obj.get("users");
            for (Object item : array) 
            {
                rol = new Rol();
                user = new User();
                JSONObject object = (JSONObject) item;
                user.setUs_id(Integer.parseInt(object.get("id").toString()));
                user.setUs_usuario(object.get("username").toString());
                user.setUs_correo(object.get("correo").toString());
                rol.setRol_id(Integer.parseInt(object.get("rol_id").toString()));
                rol.setRol_nombre(object.get("rol_nombre").toString());
                user.setRol(rol);
                userListJSON.add(user);
            }
        } catch (ParseException | IOException ex) {
            Logger.getLogger(ProcessUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Show all users
    public static void showUser()
    {
        //Create header of the table
        System.out.println("\nNo\tUsuario\tCorreo\t\t\t\tRol");
        //Walk through the array
        for (int i = 0; i < userListJSON.size(); i++) 
        {
            //Print the user in position i
            System.out.println(i + 1 + "\t" + userListJSON.get(i).getUs_usuario() + "\t" + userListJSON.get(i).getUs_correo()
            + "\t" + userListJSON.get(i).getRol().getRol_nombre() + "\n");
        }
    }
    
    //Add a new user
    public static void createUser(BufferedReader in, PrintWriter out, Scanner read)
    {
        //Needed variables
        String response = "";
        String option;
        
        //List of users tyoe
        ArrayList<User> list = new ArrayList();
        
        //Instance objects
        User user = new User();
        Rol rol = new Rol();
        
        //Variables to create the JSON
        JSONObject detailsJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        
        //Request all data to add User
        System.out.print("Ingrese el nombre del usuario: ");
        user.setUs_usuario(read.nextLine());
        System.out.print("Ingrese el correo del usuario: ");
        user.setUs_correo(read.nextLine());
        System.out.print("Ingrese la contraseña del usuario: ");
        user.setUs_contra(Encryption.encrypt(read.nextLine()));
        ProcessRol.showRol();
        System.out.print("Ingrese el numero del rol del usuario: ");
        option = read.nextLine();
        int index = Integer.parseInt(option);
        
        rol = new Rol();
        rol.setRol_id(ProcessRol.rolListJSON.get(index).getRol_id());
        user.setRol(rol);
        
        
        //Add data of the list to a json
        JSONObject json = new JSONObject();
        json.put("username", user.getUs_usuario());
        json.put("password", user.getUs_contra());
        json.put("correo", user.getUs_correo());
        json.put("rol_id", user.getRol().getRol_id());
        
        //Send the json
        detailsJson.put("user", json);
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
    
    //Delete a specified JSON
    public static void deleteUser(BufferedReader in, PrintWriter out, Scanner read)
    {   
        //Needed variables
        int id = 0;
        String very;
        
        //Show the users
        showUser();
        
        boolean log = true;
        boolean flag = true;
        while(flag)
        {
            System.out.print("Ingrese el usuario que desea eliminar ");
            usr = read.nextLine();
            if (isNumeric(usr)) 
            {
                flag = false;
                try 
                {
                    for(int i = 0; i < userListJSON.size(); i++) 
                    {
                        if (i == Integer.parseInt(usr)-1)
                        {
                            id = userListJSON.get(i).getUs_id();
                            System.out.print("¿Esta seguro que desea eliminar el usuario " + userListJSON.get(i).getUs_usuario() + "?"
                                    + "\n [1]Si, borrar \n[2]No,cancelar");
                        }
                    }
                    very = read.nextLine();

                    while(log)
                    {
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
    
    //Update a user info
    public static void updateUser(BufferedReader in, PrintWriter out, Scanner read)
    {
        //Needed variables
        
        int id = 0;
        String nombre = "";
        String correo = "";
        String contra = "";
        int rolId     = 0;
        String rolNombre = "";
        String change = "";
        String campo  = "";
        String option = "";
        
        //Needed variables to JSON
        ArrayList<User> list = new ArrayList();
        JSONObject detailsJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        
        //Instance of Objects
        User user = new User();
        Rol rol = new Rol();
        
        //Show all users
        showUser();
        
        //Request the user to update
        System.out.print("Ingrese el usuario que desea Modificar ");
        usr = read.nextLine();
        boolean log = true;
        boolean flag = true;
        while(flag)
        {
            if (isNumeric(usr)) 
            {
                flag = false;
                for (int i = 0; i < userListJSON.size(); i++) 
                {
                    if (i == Integer.parseInt(usr) - 1) 
                    {
                        id = userListJSON.get(i).getUs_id();
                        nombre = userListJSON.get(i).getUs_usuario();
                        correo = userListJSON.get(i).getUs_correo();
                        contra = userListJSON.get(i).getUs_contra();
                        rolId  = userListJSON.get(i).getRol().getRol_id();
                        rolNombre = userListJSON.get(i).getRol().getRol_nombre();
                    }
                }

                while (log) 
                {
                    System.out.println("Que desea editar del usuario " + nombre);
                    System.out.println("[1] Nombre de usuario\n[2] Correo del usuario\n[3] Contraseña del usuario\n[4] Rol del usuario");
                    campo = read.nextLine();
                    switch(campo)
                    {
                        case "1":
                            log = false;
                            System.out.print("Ingrese el nuevo nombre de usuario: ");
                            nombre = read.nextLine();
                            break;
                        case "2":
                            log = false;
                            System.out.print("Ingrese el nuevo correo: ");
                            correo = read.nextLine();
                            break;
                        case "3":
                            log = false;
                            System.out.println("Ingrese la nueva contraseño");
                            contra = Encryption.encrypt(read.nextLine());
                            break;
                        case "4":
                            log = false;
                            ProcessRol.showRol();
                            System.out.print("Ingrese el numero del nuevo rol: ");
                            option = read.nextLine();
                            for(int i = 0; i < ProcessRol.rolListJSON.size(); i++)
                            {
                                if (Integer.parseInt(option) == ProcessRol.rolListJSON.get(i).getRol_id()) 
                                {
                                    rolId = ProcessRol.rolListJSON.get(i).getRol_id();
                                    rolNombre = ProcessRol.rolListJSON.get(i).getRol_nombre();
                                }
                            }
                            break;
                        default:
                            System.out.println("Por favor ingrese un valor valido");
                            break;
                    }
                }
                user.setUs_id(id);
                user.setUs_correo(correo);
                user.setUs_contra(contra);
                user.setUs_usuario(nombre);
                rol.setRol_id(rolId);
                rol.setRol_nombre(rolNombre);
                user.setRol(rol);
                
                //Add data to a json
                JSONObject json = new JSONObject();
                json.put("username", user.getUs_usuario());
                json.put("password", user.getUs_contra());
                json.put("correo", user.getUs_correo());
                json.put("rol_id", user.getRol().getRol_id());
                //Send json with the data
                detailsJson.put("user", json);
                out.println(detailsJson);
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
