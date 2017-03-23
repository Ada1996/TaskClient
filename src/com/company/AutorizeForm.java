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
                    serv.sendName(client.getText());
                    tasks = serv.getTasks();

                     if (tasks==null)
                     {
                        JOptionPane.showMessageDialog(null, "Данный пользователь не существует", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        client.setText("");
                     }
                     else {
                                Main.clientName = client.getText();
                                ClientForm form;
                                form = new ClientForm("Task Manager");
                                form.setVisible(true);
                                form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                form.setSize(650, 400);
                                form.setLocationRelativeTo(null);
                                dispose();
                     }  
                    Server.close();
                } 
                catch (IOException ex) {System.out.println("Исключение IOException");} 
                catch (ClassNotFoundException ex) {System.out.println("Исключение ClassNotFoundException");}               
            }
        });


    }

}

