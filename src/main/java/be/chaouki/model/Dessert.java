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
public class Dessert extends Item {
    private DessertType type;
    
    public Dessert(DessertType type){
        this.type=type;
        this.setUnitPrice(type.getPrice());
    }

    public DessertType getType() {
        return type;
    }

    public void setType(DessertType type) {
        this.type = type;
    }
    
    public enum DessertType{
       TIRAMISU_SPECULOOS(3.0, "Tiramisu speculoos");
        
        private double price;
        private String nameDisplay;
        DessertType(double price, String nameDisplay){
            this.price=price;
            this.nameDisplay=nameDisplay;
        }
        
        public double getPrice(){
            return price;
        }
        
        public String getNameDisplay() {
            return nameDisplay;
        }
    }
    
    @Override
    public String toString() {
        return this.getType().getNameDisplay();
    }
}