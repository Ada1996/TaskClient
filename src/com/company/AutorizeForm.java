package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;


/**
 * Created by daryo on 09.02.2017.
 */
public class AutorizeForm extends JFrame {


    static private Socket connection;
    static private ObjectInputStream input;
    static public List<Task> tasks;
    static private DataOutputStream out;


    public AutorizeForm() {
        super("Введите имя пользователя");
        setLayout(null);


        JTextField client = new JTextField("");
        JButton ok = new JButton("OK");
        add(client);
        add(ok);
        client.setBounds(0, 20, 165, 20);
        ok.setBounds(166, 20, 150, 20);

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //имя пользователя
                Main.clientName = client.getText();

                try {
                    connection = new Socket(InetAddress.getByName("127.0.0.1"), 180);
                    out = new DataOutputStream(connection.getOutputStream());
                    input = new ObjectInputStream(connection.getInputStream());

                    out.writeUTF(Main.clientName);
                    tasks = (List<Task>) input.readObject();


                    if (!tasks.isEmpty()) {
                        //запуск таймера

                        java.util.Timer mTimer = new java.util.Timer();
                        MyTimerTask mMyTimerTask = new MyTimerTask();
                        mTimer.schedule(mMyTimerTask, 0);
                        dispose();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Нет задач для данного пользователя!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                } catch (ClassNotFoundException ex) {
                }


            }
        });


    }

}

