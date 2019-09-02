package com.enjoy.netty.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerSocket {

    public static void main(String[] args) throws IOException {
        java.net.ServerSocket serverSocket= new java.net.ServerSocket(1);
        Socket clientSocket = serverSocket.accept();
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
        String request, response;
        while((request = in.readLine()) != null){
            if ("Done".equals(request)) {
                break;
            }
        }
        response = processRequest(request);
        out.println(response);
    }

    private static String processRequest(String request) {
        return "processRequest";
    }
}
