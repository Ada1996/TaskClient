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

/**
 * Created by daryo on 27.12.2016.
 */
public class MyTimerTask extends TimerTask {


    static private Socket connection;
    static private ObjectInputStream input;
    static public List<Task> tasks;
    static private DataOutputStream out;


    @Override
    public void run() {
        boolean check = false;
        for (; ; ) {
            try {
                //ТЕКУЩАЯ ДАТА
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
                String formattedDate = df.format(calendar.getTime());

                //ПОДКЛЮЧЕНИЕ К СЕРВЕРУ, ПОЛУЧЕНИЕ СПИСКА ЗАДАЧ
                connection = new Socket(InetAddress.getByName("127.0.0.1"), 180);
                out = new DataOutputStream(connection.getOutputStream());
                input = new ObjectInputStream(connection.getInputStream());

                out.writeUTF(Main.clientName);
                tasks = (List<Task>) input.readObject();


                //ВЫЗОВ ФОРМЫ КЛИЕНТА
                if (!check) {
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
                    check = true;
                }
                //ПОИСК ЗАДАНИЯ НА ТЕКУЩЕЕ ВРЕМЯ
                for (Task x : tasks) {
                    if (x.getDate().equals(formattedDate)) {
                        Toolkit.getDefaultToolkit().beep();
                        JOptionPane.showMessageDialog(null, "Название: " + x.getName(), "Вам сообщение!", JOptionPane.INFORMATION_MESSAGE);
                        System.out.println("дата " + x.getDate());
                    }
                }
            } catch (UnknownHostException e) {
            } catch (Exception e) {
            }


            //СОН ПОТОКА НА МИНУТУ
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }
}
