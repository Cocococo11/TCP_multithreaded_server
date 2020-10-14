/***
 * EchoClient
 * Example of a TCP client 
 * Date: 10/01/04
 * Authors:
 */
package stream;

import java.io.*;
import java.net.*;


public class EchoClient {

    /**
     * main method
     * accepts a connection, receives a message from client then sends an echo to the client
     **/
    public static void main(String[] args) throws IOException {

        Socket echoSocket = null;
        ReadThread rt = null;
        PrintStream socOut = null;
        BufferedReader stdIn = null;
        BufferedReader socIn = null;

        if (args.length != 2) {
            System.out.println("Usage: java EchoClient <EchoServer host> <EchoServer port>");
            System.exit(1);
        }

        try {
            // creation socket ==> connexion
            echoSocket = new Socket(args[0], new Integer(args[1]).intValue());
            rt = new ReadThread(echoSocket);
            socOut = new PrintStream(echoSocket.getOutputStream());
            stdIn = new BufferedReader(new InputStreamReader(System.in));
            rt.start();

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host:" + args[0]);
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for " + "the connection to:" + args[0]);
            e.printStackTrace();
            System.exit(1);
            
        }
        try{
            String line;
            while (true) {
                System.out.println("En attente d'un message...");
                line = stdIn.readLine();
                System.out.println("Envoi du message...");
                if (line.equals(".")) break;
                socOut.println(line);
                
            }
            
            socOut.close();
            socIn.close();
            stdIn.close();
            echoSocket.close();
        }catch(Exception e){
            e.printStackTrace();
            System.exit(1);
        }
        
    }

    public static void leerRecibido(String message) {
        System.out.println("Recibido: " + message);
    }
}


