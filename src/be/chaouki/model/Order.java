/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.chaouki.model;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author chaouki
 */
public class Order extends  AbstractTableModel {
    
    private final String[] columnNames = {"Artikel", "Aantal", "Memo", "SubTotaal"};
    private List<OrderLine> orderLineList=new ArrayList<OrderLine>();
    private double totalPrice;
    
    public Order(){
//        orderLineList.add(new OrderLine(ClassicPizza.ClassicPizzaType.BOLOGNESE, ClassicPizza.PizzaSize.NORMAL, 2));
        refreshTotalPrice();
    }
    
    public double getTotalPrice() {
        return totalPrice;
    }
    
    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public int getRowCount() {
        return orderLineList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        OrderLine ol=orderLineList.get(rowIndex);
        
        switch(columnIndex){
            case 0: return ol.getItem();
            case 1: return ol.getQuantity();
            case 2: return ol.getMemo();
            case 3: 
                NumberFormat nf=NumberFormat.getCurrencyInstance();
                nf.setMaximumFractionDigits(2);
                return nf.format(ol.getSubTotal());
        }
        
        throw new IllegalArgumentException();
    }

    public void setValueAt(Object value, int row, int col) {
//        if(!(value instanceof String) || col!=2)
//            throw new IllegalArgumentException();
        if(col==1)
        {
            setQtt(row, (int)value);
            fireTableRowsUpdated(row, row);
        }
        if(col==2)
            orderLineList.get(row).setMemo((String)value);
    }
    
    public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col == 1 || col == 2) {
                return true;
            } else {
                return false;
            }
        }

    @Override
    public Class getColumnClass(int c) {
//        return getValueAt(0, c).getClass();
        switch(c){
            case 0: return Item.class;
            case 1: return Integer.class;
            case 2: return String.class;
            case 3: return Double.class;
        }
        throw new IllegalArgumentException();
    }

//    public List<OrderLine> getOrderLineList() {
//        return orderLineList;
//    }

    public void addEmptyLine(){
        orderLineList.add(new OrderLine());
    }
    
    public void removeLine(int rowInd){
        if(rowInd<0 || rowInd>orderLineList.size()-1)
            throw new IllegalArgumentException();
        
        orderLineList.remove(rowInd);
        totalPrice=0.0;
    }
    
    private void refreshTotalPrice() {
        double newTotalPrice=0;
        for(OrderLine ol: orderLineList){
            newTotalPrice+=ol.getSubTotal();
        }
        totalPrice=newTotalPrice;
    }
    
    public void reset(){
        orderLineList.clear();
        totalPrice=0.0;
    }
    
    public void setQtt(int row, int newQtt){
        orderLineList.get(row).setQuantity(newQtt);
        refreshTotalPrice();
    }
    
    public int getQtt(int row){
        return orderLineList.get(row).getQuantity();
    }

    public void setItem(int row, Item item){
        orderLineList.get(row).setItem(item);
        refreshTotalPrice();
    }
}
