package com.company;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

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
    public void outputTasks(String pathCatalog) throws IOException, ClassNotFoundException {

        tTable.deleteTasks();
        tTable.addTasks(MyTimerTask.tasks);
        textTable.updateUI();

    }


    public ClientForm(String s) throws IOException, ClassNotFoundException {
        super(s);


        //ВНЕШНИЙ ВИД
        setLayout(new BorderLayout());
        scroll = new JScrollPane(textTable);
        scroll.setPreferredSize(new Dimension(650, 400));
        add(scroll, BorderLayout.WEST);
    }
}

