/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.chaouki.model;

/**
 *
 * @author chaouki
 */
public class OrderLine {
    
    private Item item;
    private int quantity;
    private String memo;
    private double subTotal;
    
    public OrderLine(){
        item=null;
        quantity=1;
        memo="";
        subTotal=0.0;
    }
    
    public OrderLine(Enum en){
        
    }
    
    public OrderLine(ClassicPizza.ClassicPizzaType pizzaType, Pizza.PizzaSize size, int quantity){
        this.item=new ClassicPizza(pizzaType, size);
        this.quantity=quantity;
        this.memo="";
        this.subTotal=this.item.getUnitPrice()*this.quantity;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
        this.subTotal=this.item.getUnitPrice()*this.quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if(item==null){
            this.quantity = quantity;
            this.subTotal=0.0;
        } else{
            this.quantity = quantity;
            this.subTotal=this.item.getUnitPrice()*this.quantity;
        }
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public double getSubTotal() {
        return subTotal;
    }
    
}
