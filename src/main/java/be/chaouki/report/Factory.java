/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.chaouki.report;

import be.chaouki.model.ClassicPizza.ClassicPizzaType;
import be.chaouki.model.Order;
import be.chaouki.model.OrderLine;
import be.chaouki.model.Item;
import be.chaouki.model.Pizza.PizzaSize;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Factory ued by the reporting API. It needs the static factory method
 * which return the Collection of a standard Java class (Integer, String, ...).
 * @author chaouki
 */
public class Factory {
    
    // taking all the data from the model and putting it in a DTO
    public static List<OrderLineDTO> load(){
        Order tableModel=be.chaouki.model.Order.getInstance();
//    	Order tableModel= new Order();
//        tableModel.getOrderLineList().add(new OrderLine(ClassicPizzaType.FRUTTI_DI_MARE, PizzaSize.BIG, 3));
//        tableModel.getOrderLineList().add(new OrderLine(ClassicPizzaType.BOLOGNESE, PizzaSize.NORMAL, 1));
//        tableModel.getOrderLineList().add(new OrderLine(ClassicPizzaType.FUNGHI, PizzaSize.NORMAL, 1));
        
        List<OrderLineDTO> olDTOs=new ArrayList<>();
        for(OrderLine ol : tableModel.getOrderLineList()){
            if(ol.getItem() == null) // do not print the blank order lines
                continue;
            
            OrderLineDTO olDTO=new OrderLineDTO();
            
            olDTO.setItem(ol.getItem().toString());
            olDTO.setMemo(ol.getMemo());
            olDTO.setQuantity(Integer.toString(ol.getQuantity()));
            NumberFormat nf=NumberFormat.getCurrencyInstance();
            nf.setMaximumFractionDigits(2);
            olDTO.setSubTotal(nf.format(ol.getSubTotal()));
            
            olDTOs.add(olDTO);
        }
        
//        // /DEBUG
//        OrderLineDTO olDTO1=new OrderLineDTO();
//        olDTO1.setItem("i1");
//        olDTO1.setMemo("m1");
//        olDTO1.setQuantity("1");
//        olDTO1.setSubTotal("s1");
//        olDTOs.add(olDTO1);
//        OrderLineDTO olDTO2=new OrderLineDTO();
//        olDTO2.setItem("i2");
//        olDTO2.setMemo("m2");
//        olDTO2.setQuantity("2");
//        olDTO2.setSubTotal("s2");
//        olDTOs.add(olDTO2);
//        // DEBUG/
        
        return olDTOs;
    }
}
