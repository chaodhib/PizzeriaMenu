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
public class Drink extends Item {
    private DrinkType type;
    private DrinkSize size;
    
    public Drink(DrinkType type, DrinkSize size){
        
        // those types are not available in bottle
        if(type.equals(DrinkType.TROPICO) || type.equals(DrinkType.MINUTEMAID_MULTI) || type.equals(DrinkType.SHAKURA))
            if(size.equals(DrinkSize.BOTTLE))
                    throw new IllegalArgumentException();
        
        this.type=type;
        this.size=size;
        this.setUnitPrice(size.equals(DrinkSize.CAN)?type.getPriceCan():type.getPriceBottle());
    }

    public DrinkType getType() {
        return type;
    }

    public void setType(DrinkType type) {
        this.type = type;
    }

    public DrinkSize getSize() {
        return size;
    }

    public void setSize(DrinkSize size) {
        this.size = size;
    }
    
    
    
    public enum DrinkType{
       COCACOLA(1.5, 3.0, "Coca Cola"),
       COCACOLA_LIGHT(1.5, 3.0, "Coca Cola Light"),
       FANTA(1.5, 3.0, "Fanta"),
       SPRITE(1.5, 3.0, "Sprite"),
       ICETEA(1.5, 3.0, "Ice tea"),
       CHAUDFONTAINE(1.5, 3.0, "Chaudfontaine plat"),
       CHAUDFONTAINE_SPARKLING(1.5, 3.0, "Chaudfontaine bruis"),
       
       // no bottle
       TROPICO(1.5, 0.0, "Tropico"),
       MINUTEMAID_MULTI(1.5, 0.0, "Minute Maid Multi"),
       SHAKURA(2.0, 0.0, "Shakura");
        
        private double priceCan;
        private double priceBottle;
        private String nameDisplay;
        DrinkType(double priceCan, double priceBottle, String nameDisplay){
            this.priceCan=priceCan;
            this.priceBottle=priceBottle;
            this.nameDisplay=nameDisplay;
        }

        public double getPriceCan() {
            return priceCan;
        }

        public double getPriceBottle() {
            return priceBottle;
        }

        public String getNameDisplay() {
            return nameDisplay;
        }
    }
    
    public enum DrinkSize{
        CAN("Blik"),
        BOTTLE("Fles");
        
        DrinkSize(String nameDisplay){
            this.nameDisplay=nameDisplay;
        }
        
        
        private String nameDisplay;
        
        public String getNameDisplay() {
            return nameDisplay;
        }
    }
    
    @Override
    public String toString() {
        return this.getType().getNameDisplay() + " " + this.getSize().getNameDisplay();
    }
}