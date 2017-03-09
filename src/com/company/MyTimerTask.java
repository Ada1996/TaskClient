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
        for (; ; ) {
            try {

                //ПОДКЛЮЧЕНИЕ К СЕРВЕРУ, ПОЛУЧЕНИЕ СПИСКА ЗАДАЧ
                connection = new Socket(InetAddress.getByName("127.0.0.1"), 180);
                out = new DataOutputStream(connection.getOutputStream());
                input = new ObjectInputStream(connection.getInputStream());
                out.writeUTF(Main.clientName);
                tasks = (List<Task>) input.readObject();              



                //ТЕКУЩАЯ ДАТА
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
                String formattedDate = df.format(calendar.getTime());


                if (!tasks.isEmpty()) {
                    //ВЫЗОВ ФОРМЫ КЛИЕНТА
                    try {
                        ClientForm form;
                        form = new ClientForm("Task Manager");
                        form.setVisible(true);
                        form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        form.setSize(650, 400);
                        form.setLocationRelativeTo(null);

                        //ПОИСК ЗАДАНИЯ НА ТЕКУЩЕЕ ВРЕМЯ
                        for (Task x : tasks) {
                            if (x.getDate().equals(formattedDate)) {
                                Toolkit.getDefaultToolkit().beep();
                                MessageForm form1 = new MessageForm(x);
                                form1.setVisible(true);
                                form1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                form1.setSize(320, 287);
                                form1.setLocationRelativeTo(null);
                            }
                        }
                        Thread.sleep(60000);
                        form.dispose();
                    } catch (IOException ex) {
                        Logger.getLogger(MyTimerTask.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(MyTimerTask.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    this.cancel();
                    System.out.println("Нет задач для данного пользователя!");
                    Main.clientName="";
                    AutorizeForm autorizeForm = new AutorizeForm();
                    autorizeForm.setVisible(true);
                    autorizeForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    autorizeForm.setSize(330, 100);
                    autorizeForm.setLocationRelativeTo(null);
                    //JOptionPane.showMessageDialog(null, "Нет задач для данного пользователя!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }

            } catch (UnknownHostException e) {
            } catch (Exception e) {
            }
        }
    }
}
