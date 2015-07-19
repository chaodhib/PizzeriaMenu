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
public class ClassicPizza extends Pizza {
    private ClassicPizzaType type;
    
    public ClassicPizza(ClassicPizzaType type, PizzaSize size){
        this.type=type;
        this.setSize(size);
        if(size==PizzaSize.NORMAL)
            this.setUnitPrice(type.getNormalPrice());
        if(size==PizzaSize.BIG)
            this.setUnitPrice(type.getBigPrice());
    }
    
    public enum ClassicPizzaType{
       MARGARITA(7.9, 9.9, "Margarita"),
       PROSCIUTTO(8.5, 11.5, "Prosciutto"),
       PEPPERONI_CHEESE(8.5, 12.9, "Pepperoni Cheese"),
       FUNGHI(8.5, 12.5, "Funghi"),
       BOLOGNESE(9.9, 12.9, "Bolognese"),
       HAWAI(9.9, 12.9, "Hawaï"),
       QUATTRO_STAGIONE(9.9, 12.9, "Quattro Stagione"),
       SWEET_CHICKEN(9.9, 12.9, "Sweet Chicken"),
       SPICY_POMODORI(9.9, 12.9, "Spicy Pomodori"),
       VEGETARIANO(9.9, 12.9, "Vegetariano"),
       MEXICAN(9.9, 12.9, "Mexican"),
       MOZZARELLA(9.9, 12.9, "Mozzarella"),
       TONNO(10.2, 13.5, "Tonno"),
       FRUTTI_DI_MARE(10.5, 13.5, "Frutti Di Mare"),
       SWEET_SCAMPI(10.5, 13.5, "Sweet Scampi"),
       QUATTRO_FROMAGGI(10.5, 13.5, "Quattro Fromaggi"),
       MEAT_LOVERS(10.5, 14.5, "Meat lovers"),
       DELICIOUS(10.5, 14.5, "Delicious"),
       ROYAL(11.0, 15.0, "Royal");
        
        private double normalPrice, bigPrice; // prices for a normal and a large pizza
        private String nameDisplay;
        ClassicPizzaType(double normalPrice, double bigPrice, String nameDisplay){
            this.normalPrice=normalPrice;
            this.bigPrice=bigPrice;
            this.nameDisplay=nameDisplay;
        }
        
        public double getNormalPrice(){
            return normalPrice;
        }
        
        public double getBigPrice(){
            return bigPrice;
        }

        public String getNameDisplay() {
            return nameDisplay;
        }
    }

    public ClassicPizzaType getType() {
        return type;
    }

    public void setType(ClassicPizzaType type) {
        this.type = type;
    }
    
    @Override
    public String toString() {
        return this.getType().getNameDisplay()+" "+this.getSize().getNameDisplay();
    }
}
