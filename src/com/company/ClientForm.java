package com.company;

//import static com.company.MyTimerTask.tasks;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
 * Created by daryo on 02.02.2017.
 */
public class ClientForm extends JFrame {


    private JScrollPane scroll;
    private TaskTable tTable;
    private JTable textTable;

    static private Socket connection;
    static private ObjectOutputStream output;
    static private ObjectInputStream input;


    //КОНСТРУКТОР
    public ClientForm(String s) throws IOException, ClassNotFoundException {
        super(s);


        //ВНЕШНИЙ ВИД
        tTable = new TaskTable();
        textTable = new JTable(tTable);
        outputTasks(AutorizeForm.tasks);
        setLayout(new BorderLayout());

        scroll = new JScrollPane(textTable);
        scroll.setPreferredSize(new Dimension(650, 400));
        add(scroll, BorderLayout.WEST);
        buildTable();

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.lightGray);
        JButton changeClient = new JButton("Сменить пользователя");
        JLabel message = new JLabel("");
        JLabel client = new JLabel("  Вы вошли как: " + Main.clientName + "  ");
        menuBar.add(client);
        menuBar.add(changeClient);
        menuBar.add(message);
        setJMenuBar(menuBar);


        //ЗАПУСК ТАЙМЕРА
        java.util.Timer mTimer = new java.util.Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    //ПОДКЛЮЧЕНИЕ К СЕРВЕРУ, ПОЛУЧЕНИЕ СПИСКА ЗАДАЧ
                    Server serv = new Server();
                    serv.push(Main.clientName);
                    List<Task> tasks = serv.pop();

                    if (tasks != null) {

                        //ТЕКУЩАЯ ДАТА
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
                        String formattedDate = df.format(calendar.getTime());

                        outputTasks(tasks);

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
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Данный пользователь не существует", "Ошибка", JOptionPane.ERROR_MESSAGE);

                        AutorizeForm autorizeForm = new AutorizeForm();
                        autorizeForm.setVisible(true);
                        autorizeForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        autorizeForm.setSize(330, 100);
                        autorizeForm.setLocationRelativeTo(null);


                        mTimer.cancel();
                        dispose();
                    }


                } catch (UnknownHostException e) {
                } catch (Exception e) {
                }

            }
        }, 0, 60000);

        //СМЕНИТЬ ПОЛЬЗОВАТЕЛЯ

        changeClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AutorizeForm autorizeForm = new AutorizeForm();
                autorizeForm.setVisible(true);
                autorizeForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                autorizeForm.setSize(330, 100);
                autorizeForm.setLocationRelativeTo(null);


                mTimer.cancel();
                dispose();
            }
        });

    }

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
    public void outputTasks(List<Task> tasks) throws IOException, ClassNotFoundException {
        tTable.deleteTasks();
        tTable.addTasks(tasks);
        textTable.updateUI();
    }


}

