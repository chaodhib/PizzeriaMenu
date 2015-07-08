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
public abstract class Pizza extends Item {
    private PizzaSize size;

    public PizzaSize getSize() {
        return size;
    }

    public void setSize(PizzaSize size) {
        this.size = size;
    }
    
    public enum PizzaSize{
        NORMAL("Normaal"), BIG("Groot");
        
        private String nameDisplay;
        PizzaSize(String nameDisplay){
            this.nameDisplay=nameDisplay;
        }

        public String getNameDisplay() {
            return nameDisplay;
        }
    }
}


