package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by daryo on 09.02.2017.
 */
public class AutorizeForm extends JFrame {
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

                //запуск таймера
                java.util.Timer mTimer = new java.util.Timer();
                MyTimerTask mMyTimerTask = new MyTimerTask();
                mTimer.schedule(mMyTimerTask, 0);

                dispose();
            }
        });


    }

}

