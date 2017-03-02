package com.company;

import javax.swing.*;

/**
 * Created by daryo on 16.02.2017.
 */
public class MessageForm extends JFrame {
    public MessageForm(Task x) {
        super("Текущее задание");
        setLayout(null);


        //ИМЯ ЗАДАНИЯ
        JLabel lname = new JLabel("Имя задания:");
        JTextArea name = new JTextArea(x.getName());
        name.setEditable(false);
        add(lname);
        add(name);
        lname.setBounds(0, 25, 100, 20);
        name.setBounds(100, 25, 194, 20);

        //ОПИСАНИЕ
        JLabel ldescription = new JLabel("Описание:");
        JTextArea description = new JTextArea(x.getDescription());
        description.setEditable(false);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        add(ldescription);
        add(description);
        ldescription.setBounds(0, 50, 100, 20);
        description.setBounds(100, 50, 194, 120);

        //КОНТАКТЫ
        JLabel lcontacts = new JLabel("Контакты:");
        JTextArea contacts = new JTextArea(x.getContacts());
        contacts.setEditable(false);
        add(lcontacts);
        add(contacts);
        lcontacts.setBounds(0, 175, 100, 20);
        contacts.setBounds(100, 175, 194, 20);

        //ДАТА
        JLabel ldate = new JLabel("Дата (mm/dd/yyyy hh:mm):");
        JTextArea date = new JTextArea(x.getDate());
        date.setEditable(false);
        add(ldate);
        add(date);
        ldate.setBounds(0, 200, 200, 20);
        date.setBounds(152, 200, 142, 20);


    }


}
