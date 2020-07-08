package com.bitlab.app;
import com.bitlab.process.ProcessDepartament;
import com.bitlab.entity.Departament;
import com.bitlab.entity.User;
import com.bitlab.process.ProcessEmploye;
import com.bitlab.process.ProcessPayroll;
import com.bitlab.process.ProcessRol;
import com.bitlab.process.ProcessUser;
import com.bitlab.utility.Encryption;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author Mauricio Argumedo
 * class: Client
 * date: 2020-07-04
 */
public class Client {
    //-
    //CONSTANTS
    private static final int PORT = 8080;
    private static final String IP = "localhost";
    //private static final String IP = "35.202.115.165";
    //-
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
            initClient();
    }
    
    //Initialize all de functinalities of the system
    public static void initClient(){
        Socket clientSocket;
        Scanner read = new Scanner(System.in);
        //
        try {
            //Creating the socket to connect
            clientSocket = new Socket(IP,PORT);
            //-
            //Sets Objects to the clients output
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
            //-
            //Sets the object to read the responses
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            //LOGIN PROCESS
            loginProcess(in, out, read);
            //loginProcess(in, out, read);
            
            //Close all kind of  connection to the socket
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }
    
    //Start the process to login into the system and determinates the rol of the user
    public static void loginProcess(BufferedReader in, PrintWriter out, Scanner read){
        String serverResponse = "";//Server petitions
        String logResponse = "";// log result
        Encryption enc = new Encryption();// Instance to encrypt passwords;
        boolean log = true;// Flag to stay in the system
        try {
            //log validation
            while(log){
                //username petition
                serverResponse = in.readLine();
                System.out.print(serverResponse);
                out.println(read.nextLine());
                //password petition
                serverResponse = in.readLine();
                System.out.print(serverResponse);
                out.println(enc.encrypt(read.nextLine()));
                //log result validation
                logResponse = in.readLine();
                if(logResponse.equals("logSuccessful"))
                {
                    //Allow to enter in the system
                    log=false;
                    System.out.println("Ingreso Correctamente");
                    try 
                    {
                        Thread.sleep(1000);
                    } 
                    catch (InterruptedException ex) 
                    {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else
                {
                    System.out.println("Usuario o contraseña incorrectos");
                    try 
                    {
                        Thread.sleep(1000);
                    } 
                    catch (InterruptedException ex) 
                    {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            log = true;
            while(log)
            {
                System.out.print("Ingrese el correo asociado a su usuario: ");
                out.println(read.nextLine());
                
                logResponse = in.readLine();
                if(logResponse.equals("vSuccessgul"))
                {
                    log = false;
                }
                else if(logResponse.equals("wrongEmail"))
                {
                    System.out.println("Este email no corresponde con su usuario...\n");
                    try 
                    {
                        Thread.sleep(1000);
                    } 
                    catch (InterruptedException ex) 
                    {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            log = true;
            while(log)
            {
                System.out.print("Ingrese el codigo enviado a su correo: ");
                out.println(read.nextLine());
                
                logResponse = in.readLine();
                if (logResponse.equals("1")) 
                {
                    log = false;
                    menuAdmin(in, out, read);
                }
                else if(logResponse.equals("2"))
                {
                    log = false;
                    menuRrhh(in, out, read);
                }
                else
                {
                    System.out.println("El codigo ingresado es incorrecto");
                    try 
                    {
                        Thread.sleep(1000);
                    } 
                    catch (InterruptedException ex) 
                    {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
            }
        } catch (IOException ex) 
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Start the system into Admin user state 
    public static void menuAdmin(BufferedReader in, PrintWriter out, Scanner read)
    {
        String serverResponse = "";//Server petitions
        String logResponse = "";// log result
        String option;//Menu option selected
        String option2;//Menu option selected 2
        JSONParser parser = new JSONParser();//JSON Parser
        Departament departament = new Departament();
        ArrayList<Departament> departamentListJSON = new ArrayList();
        boolean log = true;//Flag
        boolean log2 = true;//Flag2
        
        log = true;
        while(log)
        {
            //Administrator menu
            StringBuilder menu = new StringBuilder();
            menu.append("Bienvenido. ¿Qué desea ver/realizar?\n");
            menu.append("[1] Gestionar departamentos.\n");
            menu.append("[2] Gestionar usuarios.\n");
            menu.append("[3] Gestionar roles.\n");
            menu.append("[4] Salir.\n");
            menu.append("Por favor escoga una respuesta: ");
            System.out.print(menu);
            option = read.nextLine();
            
            //Verify if the option  selected is a number
            if (isNumeric(option)) 
            {
                switch(option)
                {
                    case "1":
                        log2 = true;
                        while(log2)
                        {
                         out.println("gestDepartament");
                         ProcessDepartament.getDep(in, out,read);
                         System.out.print("[1] Listar departamento \n[2] Agregar departamento\n[3]Modificar departamento\n[4] Salir\nEliga una opcion: ");
                         option2 = read.nextLine();
                         switch (option2)
                         {
                             case "1":
                                 out.println("list");
                                 ProcessDepartament.showDepartament();
                                 break;
                             case "2":
                                 out.println("create");
                                 ProcessDepartament.createDep(in, out, read);
                                 break;
                             case "3":
                                 out.println("update");
                                 ProcessDepartament.updateDep(in, out, read);
                                 break;
                             case "4":
                                 log2 = false;
                                 out.println("exit");
                                 break;
                             default:
                                 System.out.println("Por favor ingrese un valor valido.");
                                 break;
                         }
                        }
                        break;
                    case "2":
                        log2 = true;
                        while(log2)
                        {
                            out.println("gestUser");
                            ProcessUser.getUser(in, out, read);
                            ProcessRol.getRol(in, out, read);
                            System.out.print("[1] Listar usuarios \n[2] Agregar usuario \n[3] Modificar usuario \n[4] Eliminar usuario \n[5] Salir\nEligar una opcion: ");
                            option2 = read.nextLine();
                            switch(option2)
                            {
                                case "1":
                                    out.println("list");
                                    ProcessUser.showUser();
                                    break;
                                case "2":
                                    out.println("create");
                                    ProcessUser.createUser(in, out, read);
                                    break;
                                case "3":
                                    out.println("update");
                                    ProcessUser.updateUser(in, out, read);
                                    break;
                                case "4":
                                    out.println("delete");
                                    ProcessUser.deleteUser(in, out, read);
                                    break;
                                case "5":
                                    log2 = false;
                                    out.println("exit");
                                    break;
                                default:
                                    System.out.println("Ingrese una opcion valida");
                            }
                        }
                        break;
                    case "3":
                        log2 = true;
                        while(log2)
                        {
                            out.println("gestRol");
                            ProcessRol.getRol(in, out, read);
                            System.out.print("[1] Listar Roles \n[2] Agregar Rol \n[3] Modificar Rol \n[4] Salir\nEliga una opcion: ");
                            option2 = read.nextLine();
                            switch(option2)
                            {
                                case "1":
                                    out.println("list");
                                    ProcessRol.showRol();
                                    break;
                                case "2":
                                    out.println("create");
                                    ProcessRol.createRol(in, out, read);
                                    break;
                                case "3":
                                    out.println("update");
                                    ProcessRol.updateRol(in, out, read);
                                    break;
                                case "4":
                                    log2 = false;
                                    out.println("exit");
                                    break;
                                default:
                                    System.out.println("Ingrese una opcion valida");
                                    break;
                            }
                        }
                        break;
                    case "4":
                        if (exit(read)) 
                        {
                            log = false;
                            out.println("exit");
                        }
                        break;
                }
            }
            else
            {
                System.out.println("Por favor ingrese un valor numérico.");
            }
        }
            
            
        
    }
    
    //Start the system into RRHH user state 
    public static void menuRrhh(BufferedReader in, PrintWriter out, Scanner read)
    {
        String serverResponse = "";//Server petitions
        String logResponse = "";// log result
        String option;//Menu option selected
        String option2;//Menu option selected
        boolean log = true;
        boolean log2 = true;
        
        while(log)
        {
            //RRHH menu
            StringBuilder menu = new StringBuilder();
            menu.append("Bienvenido. ¿Qué desea ver/realizar?\n");
            menu.append("[1] Gestionar empleados.\n");
            menu.append("[2] Ver pagos generados.\n");
            menu.append("[3] Salir.\n");
            menu.append("Por favor escoga una respuesta: ");
            System.out.println(menu);
            
            option = read.nextLine();
            //Verify if the option  selected is a number
            if (isNumeric(option)) 
            {
                switch(option)
                {
                    case "1":
                        log2 = true;
                        while(log2)
                        {
                            out.println("gestEmploye");
                            ProcessEmploye.getEmploye(in, out, read);
                            ProcessDepartament.getDep(in, out, read);
                            System.out.print("[1] Listar empleados\n[2] Agregar empleado\n[3] Modificar empleado \n[4] Desactiva empleado por despido \n[5] Salir\nEliga una opcion");
                            option2 = read.nextLine();
                            switch (option2) {
                                case "1":
                                    out.println("list");
                                    ProcessEmploye.showEmploy();
                                    break;
                                case "2":
                                    out.println("create");
                                    ProcessEmploye.createEmploy(in, out, read);
                                    break;
                                case "3":
                                    out.println("update");
                                    ProcessEmploye.updateEmploy(in, out, read);
                                    break;
                                case "4":
                                    out.println("disable");
                                    ProcessEmploye.deleteEmploy(in, out, read);
                                    break;
                                case "5":
                                    out.println("exit");
                                    log2 = false;
                                    break;
                            }
                        }
                        break;
                    case "2":
                        log2 = true;
                        out.println("gestPayroll");
                        ProcessPayroll.getPayroll(in, out, read);
                        ProcessPayroll.showPayrollWithMenu(in, out, read);
                        break;
                    case "3":
                        if (exit(read)) 
                        {
                            log = false;
                            out.println("exit");
                        }
                        break;
                }
            }
            else
            {
                System.out.println("Por favor ingrese un valor numérico.");
            }
        }
    }
    
    //Validacion de si el valor ingresado es numerico
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
    
    //Validate the exit of the system
    public static boolean exit(Scanner read)
    {
        String option;//Menu option selected
        boolean exit = false;//Validation for exit
        boolean log = true;//Flag
        
        StringBuilder confirm = new StringBuilder();
        confirm.append("¿Esta seguro que desea salir?\n");
        confirm.append("[1] Salir\n");
        confirm.append("[2] Cancelar\n");
        while(log)
        {
            System.out.println(confirm);
            option = read.nextLine();
            switch(option)
            {
                case "1":
                    exit = true;
                    log = false;
                    break;
                case "2":
                    exit = false;
                    log = false;
                    break;
                default:
                    System.out.println("Por favor ingrese un valor valido");
                    break;
            }
          
        }
        return exit;
    }
}
