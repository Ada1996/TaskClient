package com.company;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

/**
 * Created by daryo on 10.03.2017.
 */
public class Server {

    public static Socket connection;
    public static DataOutputStream out;
    public static ObjectInputStream input;

    public Server() throws IOException {
        connection = new Socket(InetAddress.getByName("127.0.0.1"), 180);
        out = new DataOutputStream(connection.getOutputStream());
        input = new ObjectInputStream(connection.getInputStream());
    }

    public static void sendName(String name) throws IOException {       
        out.writeUTF(name);
    }

    public static List<Task> getTasks() throws IOException, ClassNotFoundException {       
        return (List<Task>) input.readObject();
    }
    
    public static void close() throws IOException{
        out.close();
        input.close();
    }
}
