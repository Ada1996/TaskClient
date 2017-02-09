package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

/**
 * Created by daryo on 27.12.2016.
 */
public class MyTimerTask extends TimerTask {


    static private Socket connection;
    static private ObjectInputStream input;
    static public List<Task> tasks;
    static private DataOutputStream out;
    private TaskTable tTable;
    private JTable textTable;


    @Override
    public void run() {
        boolean check = false;
         
        for (; ; ) {
            try {
               
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
                String formattedDate = df.format(calendar.getTime());


                connection = new Socket(InetAddress.getByName("127.0.0.1"), 180);
                out = new DataOutputStream(connection.getOutputStream());
                input = new ObjectInputStream(connection.getInputStream());

                out.writeUTF("44");
                tasks = (List<Task>)input.readObject();              
                    
                if (!check){
                    System.out.println("hello ");
                    ClientForm form;
        try {
            form = new ClientForm("Task Manager");
             form.setVisible(true);
        form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        form.setSize(650, 400);
        form.setLocationRelativeTo(null);
        } catch (IOException ex) {
            Logger.getLogger(MyTimerTask.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MyTimerTask.class.getName()).log(Level.SEVERE, null, ex);
        }
        check=true;
                }
                       
                for (Task x : tasks) {
                    if (x.getDate().equals(formattedDate)) {
                        Toolkit.getDefaultToolkit().beep();
                        JOptionPane.showMessageDialog(null, "Название: " + x.getName(), "Вам сообщение!", JOptionPane.INFORMATION_MESSAGE);
                        System.out.println("дата "+ x.getDate());
                    }
                }
            } catch (UnknownHostException e) {
            } catch (Exception e) {
            }

            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }



//    //ОТПРАВКА ДАННЫХ
//    private static void sendData(Object obj) {
//        try {
//            out.flush();
//            out.writeData(obj);
//        } catch (IOException e) {
//        }
//    }
}
