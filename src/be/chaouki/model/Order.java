/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.chaouki.model;

import be.chaouki.model.Drink.DrinkSize;
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
    private double totalPriceBefore; // before discount
    private double discount;
    
    public Order(){
//        orderLineList.add(new OrderLine(ClassicPizza.ClassicPizzaType.BOLOGNESE, ClassicPizza.PizzaSize.NORMAL, 2));
        refreshTotalPrice();
    }
    
    public double getTotalPrice() {
        return totalPriceBefore-discount;
    }

    public double getTotalPriceBefore() {
        return totalPriceBefore;
    }

    public double getDiscount() {
        return discount;
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

    @Override
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
    
    @Override
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
        
        refreshTotalPrice();
    }
    
    private void refreshTotalPrice() {
        double newTotalPrice=0;
        for(OrderLine ol: orderLineList){
            newTotalPrice+=ol.getSubTotal();
        }
        totalPriceBefore=newTotalPrice;
        
        refreshDiscount();
    }
    
    public void reset(){
        orderLineList.clear();
        totalPriceBefore=0.0;
        discount=0.0;
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

    /** 
     *  Only one discount is applicable
     */
    private void refreshDiscount() {
        discount=0;
        
        // ------------------------------------------------------------------
        // Discount1: TWO euro discount if TWO normal pizzas bought
        // ------------------------------------------------------------------
        final double DISCOUNT1=2.0;
        int normalPizzaCount=0;
        for(OrderLine ol : orderLineList){
            if(ol.getItem() instanceof Pizza){
                Pizza p = (Pizza) ol.getItem();
                if(p.getSize().equals(Pizza.PizzaSize.NORMAL))
                    normalPizzaCount+=ol.getQuantity();
            }
            if(normalPizzaCount>=2)
                break;
        }
        if(normalPizzaCount>=2 && DISCOUNT1>discount)
                discount=DISCOUNT1;
        
        // ------------------------------------------------------------------
        // Discount2: ONE bottle is offered if TWO big pizzas bought
        // ------------------------------------------------------------------
        // look for 2 big pizzas
        int bigPizzaCount=0;
        for(OrderLine ol : orderLineList){
            if(ol.getItem() instanceof Pizza){
                Pizza p = (Pizza) ol.getItem();
                if(p.getSize().equals(Pizza.PizzaSize.BIG))
                    bigPizzaCount+=ol.getQuantity();
            }
            if(bigPizzaCount>=2)
                break;
        }
        // if 2 big pizzas are found, look for the bottle which has the highest unit price
        if(bigPizzaCount>=2){
            double highestBottlePrice=0;
            for(OrderLine ol : orderLineList){
                if(ol.getItem() instanceof Drink){
                    Drink drink = (Drink) ol.getItem();
                    if(drink.getSize().equals(DrinkSize.BOTTLE) && drink.getUnitPrice()>highestBottlePrice)
                        highestBottlePrice=drink.getUnitPrice();
                }
            }
            
            final double DISCOUNT2=highestBottlePrice;
            
            // Apply Discount2 if its better than the previous discount
            if(discount<DISCOUNT2)
                discount=DISCOUNT2;
        }
        
        // ------------------------------------------------------------------
        // Discount3: ONE normal pizza + ONE side dish + ONE drink for 11.9 EUR
        // ------------------------------------------------------------------
        double highestNormalPizzaPrice=0;
        double highestSideDishPrice=0;
        double highestDrinkPrice=0;
        boolean normalPizzaFound=false;
        boolean sideDishFound=false;
        boolean drinkFound=false;
        
        // search for the normal pizza with the highest unit price
        for(OrderLine ol : orderLineList){
            if(ol.getItem() instanceof Pizza){
                normalPizzaFound=true;
                Pizza p = (Pizza) ol.getItem();
                if(p.getSize().equals(Pizza.PizzaSize.NORMAL) && p.getUnitPrice()>highestNormalPizzaPrice)
                    highestNormalPizzaPrice=p.getUnitPrice();
            }
        }
        
        // search for the side-dish with the highest unit price
        for(OrderLine ol : orderLineList){
            if(ol.getItem() instanceof SideDish){
                sideDishFound=true;
                SideDish s = (SideDish) ol.getItem();
                if(s.getUnitPrice()>highestSideDishPrice)
                    highestSideDishPrice=s.getUnitPrice();
            }
        }
        
        // search for the can with the highest unit price
        for(OrderLine ol : orderLineList){
            if(ol.getItem() instanceof Drink){
                drinkFound=true;
                Drink s = (Drink) ol.getItem();
                if(s.getSize().equals(DrinkSize.CAN) && s.getUnitPrice()>highestDrinkPrice)
                    highestDrinkPrice=s.getUnitPrice();
            }
        }
        
        if(normalPizzaFound && sideDishFound && drinkFound){ // look if discount applicable
            final double DISCOUNT3=highestNormalPizzaPrice+highestSideDishPrice+highestDrinkPrice-11.9;
            
            if(discount<DISCOUNT3) // look if its the highest discount available
                discount=DISCOUNT3;
        }
    }
}
