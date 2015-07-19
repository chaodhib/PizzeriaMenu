/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.chaouki.model;

import java.util.Map;

/**
 *
 * @author chaouki
 */
public class CustomPizza extends Pizza {
    private Map<PizzaExtra, Integer> extrasQtt;
    
    public CustomPizza(Map<PizzaExtra, Integer> extrasQtt, PizzaSize size){
        this.extrasQtt=extrasQtt;
        this.setSize(size);
        setUnitPrice(calculatePrice());
    }
    
    @Override
    public String toString() {
        
        String ret="Pizza Custom "+this.getSize();
        for(PizzaExtra extra : extrasQtt.keySet())
            ret+=System.lineSeparator()+extra+" x"+extrasQtt.get(extra);
        
        return ret;
    }

    private double calculatePrice() {
        // the base
        double price=getSize().equals(PizzaSize.NORMAL)? 7.9 : 9.9;
        
        // the extras
        for(PizzaExtra extra : extrasQtt.keySet())
            price+=extrasQtt.get(extra)*extra.getPrice(getSize()); // quantity * extra's price
        
        return price;
    }

    public enum PizzaExtra{
        // Cheese
        GOUDA(1.0, 1.3, "gouda"),
        MOZZARELLA(1.0, 1.3, "mozzarella"),
        FETA(1.0, 1.3, "feta"),
        GORGONZOLAKAAS(1.0, 1.3, "gorgonzolakaas"),
        
        // Meat
        KIP(1.0, 1.3, "kip"),
        HAM(1.0, 1.3, "ham"),
        GEHAKT(1.0, 1.3, "gehakt"),
        PEPPERONI(1.0, 1.3, "pepperoni"),
        SPICY_MEATBALLS(1.0, 1.3, "spicy meatballs"),
        
        // Vegetable
        UI(1.0, 1.3, "ui"),
        PAPRIKA(1.0, 1.3, "paprika"),
        CHAMPIGNONS(1.0, 1.3, "champignons"),
        GROENE_CHILIPEPERS(1.0, 1.3, "groene chilipepers"),
        TOMATEN(1.0, 1.3, "tomaten"),
        OLIJVEN(1.0, 1.3, "olijven"),
        ANANAS(1.0, 1.3, "ananas"),
        
        // Fish
        ZEEVRUCHTEN(1.0, 1.3, "zeevruchten"),
        TONIJN(1.0, 1.3, "tonijn"),
        SCAMPI(1.0, 1.3, "scampi"),
        
        // Sauce
        BOLOGNESESAUS(1.0, 1.3, "bolognesesaus"),
        TOMATENSAUS(1.0, 1.3, "tomatensaus"),
        LOOKSAUS(1.0, 1.3, "looksaus"),
        ANDALOUSE(1.0, 1.3, "andalouse"),
        BBQ_SAUS(1.0, 1.3, "bbq - saus"),
        ZOETZURE_SAUS(1.0, 1.3, "zoetzure saus");
        
        private double priceNormal;
        private double priceBig;
        private String nameDisplay;
        
        PizzaExtra(double priceNormal, double priceBig, String nameDisplay){
            this.priceNormal=priceNormal;
            this.priceBig=priceBig;
            this.nameDisplay=nameDisplay;
        }

        public double getPriceNormal() {
            return priceNormal;
        }

        public double getPriceBig() {
            return priceBig;
        }

        public String getNameDisplay() {
            return nameDisplay;
        }
        
        public double getPrice(PizzaSize size){
            if(size.equals(PizzaSize.NORMAL))
                return priceNormal;
            else if(size.equals(PizzaSize.BIG))
                return priceBig;
            
            throw new IllegalArgumentException();
        }
        
        @Override
        public String toString(){
            return nameDisplay;
        }
    }
}
