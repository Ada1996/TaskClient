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
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

/**
 * Created by daryo on 27.12.2016.
 */
public class MyTimerTask extends TimerTask {


    static private Socket connection;
    static private ObjectInputStream input;
    static public List<Task> tasks;
    static private DataOutputStream out;
    private TaskTable tTable;
    private JTable textTable;


//РАБОТА С ТАБЛИЦЕЙ
    public void buildTable() {
        textTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumn column = null;
        int prefWidth = 0;
        JTableHeader th = textTable.getTableHeader();
        for (int i = 0; i < textTable.getColumnCount(); i++) {
            column = textTable.getColumnModel().getColumn(i);
            int prefWidthMax = 0;
            for (int j = 0; j < textTable.getRowCount(); j++) {
                String s = textTable.getModel().getValueAt(j, i).toString();
                prefWidth =
                        Math.round(
                                (float) th.getFontMetrics(
                                        th.getFont()).getStringBounds(s,
                                        th.getGraphics()
                                ).getWidth()
                        );
                if (prefWidth > prefWidthMax) prefWidthMax = prefWidth;
            }
            column.setPreferredWidth(prefWidthMax + 68);
        }
    }

    //ВЫВОД ВСЕХ ЗАДАЧ НА ЭКРАН
    public void outputTasks(List<Task> journ) throws IOException, ClassNotFoundException {
        tTable.deleteTasks();
        tTable.addTasks(journ);
        System.out.println("получ: "+ journ.get(0).getName());
        textTable.updateUI();      
    }

    @Override
    public void run() {
        boolean check = false;
         
        for (; ; ) {
            try {
               
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
                String formattedDate = df.format(calendar.getTime());


                connection = new Socket(InetAddress.getByName("127.0.0.1"), 180);
                out = new DataOutputStream(connection.getOutputStream());
                input = new ObjectInputStream(connection.getInputStream());

                out.writeUTF("22");
                tasks = (List<Task>)input.readObject();
                System.out.println("получили: "+ tasks.get(0).getName());
                    
                if (!check){
                    System.out.println("hello ");
                    ClientForm form;
        try {
            form = new ClientForm("Task Manager");
             form.setVisible(true);
        form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        form.setSize(650, 400);
        form.setLocationRelativeTo(null);
        } catch (IOException ex) {
            Logger.getLogger(MyTimerTask.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MyTimerTask.class.getName()).log(Level.SEVERE, null, ex);
        }
        check=true;
                }
                 
//        tTable = new TaskTable();
//        textTable = new JTable(tTable);     
//        outputTasks(tasks);
//        buildTable();
        
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
