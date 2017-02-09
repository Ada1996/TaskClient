/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Настя
 */
public class TaskTable extends AbstractTableModel{
    private int column=5;
    private ArrayList<String []> data;
    DefaultTableModel model = new DefaultTableModel();
    public TaskTable() {
        data = new ArrayList<String []>();
        for (int i=0; i<data.size(); i++)
            data.add(new String[getColumnCount()]);
    }
    @Override
    public int getRowCount() {
        return data.size(); 
    }

    @Override
    public int getColumnCount() {
        return column;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object []rows = data.get(rowIndex);         
        return rows[columnIndex];
    }
    
   public void updateValue(Object newValue, int rowIndex, int columnIndex){
        Object[] row = data.get(rowIndex);
        row[columnIndex] = newValue;
   }
   @Override
    public String getColumnName(int columnIndex){
        switch(columnIndex){
            case 0: return "Клиент";
            case 1: return "Название";
            case 2: return "Описание";
            case 3: return "Дата";
            case 4: return "Контакты";
        }
        return "";
    }   
    public void addData(String[] row){
        String []rowTable = new String[getColumnCount()];
        rowTable=row;
        data.add(rowTable);
    }
    public void addTasks(List<Task> tasks){
        for(Task x : tasks) {
            String[] row = {
                    x.getClient(),
                    x.getName(),
                    x.getDescription(),
                    x.getDate(),
                    x.getContacts(),
            };
            System.out.println("таблица: "+ x.getName());
            addData(row);
        }
    }
    public void deleteTasks (){
        data = new ArrayList<>();
    }
}
