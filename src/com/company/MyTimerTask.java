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
        for (; ; ) {
            try {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
                String formattedDate = df.format(calendar.getTime());


                connection = new Socket(InetAddress.getByName("127.0.0.1"), 180);
                out = new DataOutputStream(connection.getOutputStream());
                input = new ObjectInputStream(connection.getInputStream());

                out.writeUTF("Даша");
                tasks = (List<Task>)input.readObject();
                System.out.println("получили: "+ tasks.get(0).getName());

                for (Task x : tasks) {
                    if (x.getDate().equals(formattedDate)) {
                        Toolkit.getDefaultToolkit().beep();
                        JOptionPane.showMessageDialog(null, "Название: " + x.getName(), "Вам сообщение!", JOptionPane.INFORMATION_MESSAGE);
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
