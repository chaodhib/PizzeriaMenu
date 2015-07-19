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
public class Pasta extends Item {
    private PastaType type;
    
    public Pasta(PastaType type){
        this.type=type;
        this.setUnitPrice(type.getPrice());
    }

    public PastaType getType() {
        return type;
    }

    public void setType(PastaType type) {
        this.type = type;
    }
    
    public enum PastaType{
       BOLOGNESE(8.5, "Bolognese");
        
        private double price;
        private String nameDisplay;
        PastaType(double price, String nameDisplay){
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