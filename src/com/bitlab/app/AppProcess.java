/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitlab.app;

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
 * @author Maury
 */
public class AppProcess {
    
    Departament departament = new Departament();
    
    
    public static void getDep(BufferedReader in, PrintWriter out, Scanner read)
    {
        String serverResponse = "";//Server petitions
        String logResponse = "";// log result
        String option;//Menu option selected
        JSONParser parser = new JSONParser();//JSON Parser
        Departament departament = new Departament();
        ArrayList<Departament> departamentListJSON = new ArrayList();
        try
        {
            logResponse = in.readLine();
            JSONObject obj = (JSONObject) parser.parse(logResponse);
            JSONArray array = (JSONArray) obj.get("departament");
            for (Object item : array) {
                departament = new Departament();
                JSONObject object = (JSONObject) item;
                departament.setDep_id(Integer.parseInt(object.get("id").toString()));
                departament.setDep_nombre(object.get("nombre").toString());
                departamentListJSON.add(departament);
            }
            System.out.println("\nId\tNombre");
            for (int i = 0; i < departamentListJSON.size(); i++) {
                System.out.println(departamentListJSON.get(i).getDep_id() + "\t" + departamentListJSON.get(i).getDep_nombre());
            }

        } catch (ParseException | IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
