package com.company;

import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        

        java.util.Timer mTimer;
        MyTimerTask mMyTimerTask;

        mTimer = new java.util.Timer();
        mMyTimerTask = new MyTimerTask();
        // start MyTimerTask thread
        mTimer.schedule(mMyTimerTask, 0);

    }
}
