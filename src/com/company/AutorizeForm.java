package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by daryo on 09.02.2017.
 */
public class AutorizeForm extends JFrame {


//    static private Socket connection;
//    static private ObjectInputStream input;
//    static private DataOutputStream out;

    static public List<Task> tasks;



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
                try {

                    Server serv = new Server();
                    serv.Push(client.getText());
                    tasks = serv.Pop();

                    if (!tasks.isEmpty()) {
                        if (tasks.get(0).getClient().equals(""))
                               JOptionPane.showMessageDialog(null, "Данного пользователя не существует!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        else{
                            Main.clientName = client.getText();

                            ClientForm form;
                            form = new ClientForm("Task Manager");
                            form.setVisible(true);
                            form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            form.setSize(650, 400);
                            form.setLocationRelativeTo(null);



                            dispose();
                        }
                    }
                    else
                    {
                        client.setText("");
                        JOptionPane.showMessageDialog(null, "Нет задач для данного пользователя!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }                   
                } catch (IOException ex) {
                } catch (ClassNotFoundException ex) {
                }               
            }
        });


    }

}

