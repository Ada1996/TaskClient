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

    public Server() throws IOException {
        connection = new Socket(InetAddress.getByName("127.0.0.1"), 180);
    }

    public static void Push(String name) throws IOException {
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.writeUTF(name);
    }

    public static List<Task> Pop() throws IOException, ClassNotFoundException {
        ObjectInputStream input = new ObjectInputStream(connection.getInputStream());
        return (List<Task>) input.readObject();
    }
}
