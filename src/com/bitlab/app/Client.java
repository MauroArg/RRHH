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
                out.println(read.nextLine());
                //log result validation
                logResponse = in.readLine();
                if(logResponse.equals("logSuccessful")){log=false;System.out.println(logResponse);}
                else{System.out.println(logResponse);}
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
