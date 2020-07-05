package com.bitlab.app;
import com.bitlab.utility.Encryption;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    public static void initClient(){
        Socket clientSocket;
        Scanner read = new Scanner(System.in);
        //
        try {
            //Creating the socket to connect
            clientSocket = new Socket(IP,PORT);
            //-
            //Se preparan los objetos para la salida de flujo de datos del cliente
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
            //-
            //Se prepara el objeto para leer las respuestas
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            //-
            //Setting the email
            //out.println(correo);
            //-
            //Crating menu
            StringBuilder menu = new StringBuilder();
            menu.append("Bienvenido a G-GPU. ¿Qué deseas ver/realizar?\n");
            menu.append("[1] Ver Detalles de estado del servidor.\n");
            menu.append("[2] Enviar por EMAIL detalles del servidor.\n");
            menu.append("[3] Salir.\n");
            menu.append("Por favor escoga una respuesta: ");
            String resp;
            //-
            //LOGIN PROCESS
            loginProcess(in, out, read);
            
            //Server functions
            /*while((resp = in.readLine()) != null){
                if(resp.startsWith("good bye")){
                    break;
                }
                else{
                    if(resp.equalsIgnoreCase("printMenu")){System.out.println(menu);}
                    else{System.out.println("Server say: " + resp);System.out.println(menu);}
                    out.println(read.nextLine());
                }
            }*/
            
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }
    
    public static void loginProcess(BufferedReader in, PrintWriter out, Scanner read){
        String serverResponse = "";//Server petitions
        String logResponse = "";// log result
        Encryption enc = new Encryption();
        boolean log = true;
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
                    log=false;
                    System.out.println("Ingreso Correctamente");
                }
                else
                {
                    System.out.println("Usuario o contraseña incorrectos");
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
                    System.out.println(logResponse);
                }
                else if(logResponse.equals("wrongEmail"))
                {
                    System.out.println("Este email no corresponde con su usuario\n");
                }
            }
            log = true;
            while(log)
            {
                System.out.println("Ingrese el codigo enviado a su correo: ");
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
                }
                
            }
        } catch (IOException ex) 
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void menuAdmin(BufferedReader in, PrintWriter out, Scanner read)
    {
        String serverResponse = "";//Server petitions
        String logResponse = "";// log result
        String option;//Menu option selected
        boolean log = true;
        
        log = true;
        while(log)
        {
            //Administrator menu
            StringBuilder menu = new StringBuilder();
            menu.append("Bienvenido. ¿Qué desea ver/realizar?\n");
            menu.append("[1] Gestionar departamentos.\n");
            menu.append("[2] Gestionar empleados.\n");
            menu.append("[3] Gestionar usuarios.\n");
            menu.append("[4] Gestionar roles.\n");
            menu.append("[5] Salir.\n");
            menu.append("Por favor escoga una respuesta: ");
            System.out.println(menu);
            option = read.nextLine();
            
            //Verify if the option  selected is a number
            if (isNumeric(option)) 
            {
                switch(option)
                {
                    case "1":
                        out.println("gestDepartament");
                        
                        break;
                    case "2":
                        out.println("gestEmploy");
                        
                        break;
                    case "3":
                        out.println("gestUser");
                        
                        break;
                    case "4":
                        out.println("gestRol");
                        
                        break;
                    case "5":
                        if (exit(read)) 
                        {
                            log = false;
                        }
                        else
                        {
                            System.out.println("\n");
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
    
    public static void menuRrhh(BufferedReader in, PrintWriter out, Scanner read)
    {
        String serverResponse = "";//Server petitions
        String logResponse = "";// log result
        String option;//Menu option selected
        boolean log = true;
        
        while(log)
        {
            //RRHH menu
            StringBuilder menu = new StringBuilder();
            menu.append("Bienvenido. ¿Qué desea ver/realizar?\n");
            menu.append("[1] Actualizar datos de empleados.\n");
            menu.append("[2] Contratar empleado.\n");
            menu.append("[3] Ver pagos generados.\n");
            menu.append("[4] Generar pagos en planilla.\n");
            menu.append("[5] Salir.\n");
            menu.append("Por favor escoga una respuesta: ");
            System.out.println(menu);
            
            option = read.nextLine();
            //Verify if the option  selected is a number
            if (isNumeric(option)) 
            {
                switch(option)
                {
                    case "1":
                        out.println("updateEmploy");
                        
                        break;
                    case "2":
                        out.println("createEmploy");
                        
                        break;
                    case "3":
                        out.println("viewPayroll");
                        
                        break;
                    case "4":
                        out.println("createPayroll");
                        
                        break;
                    case "5":
                        if (exit(read)) 
                        {
                            log = false;
                        }
                        else
                        {
                            System.out.println("\n");
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
