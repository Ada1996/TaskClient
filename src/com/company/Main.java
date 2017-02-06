package com.company;

import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ClientForm form = new ClientForm("Task Manager");
        form.setVisible(true);
        form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        form.setSize(650, 400);
        form.setLocationRelativeTo(null);

        java.util.Timer mTimer;
        MyTimerTask mMyTimerTask;

        mTimer = new java.util.Timer();
        mMyTimerTask = new MyTimerTask();
        // start MyTimerTask thread
        mTimer.schedule(mMyTimerTask, 0);

    }
}
