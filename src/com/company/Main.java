package com.company;

import javax.swing.*;
import java.io.IOException;

public class Main {

    public static String clientName = "";

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        AutorizeForm autorizeForm = new AutorizeForm();
        autorizeForm.setVisible(true);
        autorizeForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        autorizeForm.setSize(330, 100);
        autorizeForm.setLocationRelativeTo(null);
    }
}
