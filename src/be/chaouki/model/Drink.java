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
    
    public Drink(DrinkType type){
        this.type=type;
        this.setUnitPrice(type.getPrice());
    }

    public DrinkType getType() {
        return type;
    }

    public void setType(DrinkType type) {
        this.type = type;
    }
    
    public enum DrinkType{
       COCACOLA_CAN(1.5, "Coca Cola Blik"),
       COCACOLA_BOTTLE(3.0, "Coca Cola Fles"),
       
       COCACOLA_LIGHT_CAN(1.5, "Coca Cola Light Blik"),
       COCACOLA_LIGHT_BOTTLE(3.0, "Coca Cola Light Fles"),
       
       FANTA_CAN(1.5, "Fanta Blik"),
       FANTA_BOTTLE(3.0, "Fanta Fles"),
       
       SPRITE_CAN(1.5, "Sprite Blik"),
       SPRITE_BOTTLE(3.0, "Sprite Fles"),
       
       ICETEA_CAN(1.5, "Ice tea Blik"),
       ICETEA_BOTTLE(3.0, "Ice tea Fles"),
       
       CHAUDFONTAINE_CAN(1.5, "Chaudfontaine plat Blik"),
       CHAUDFONTAINE_BOTTLE(3.0, "Chaudfontaine plat Fles"),
       
       CHAUDFONTAINE_SPARKLING_CAN(1.5, "Chaudfontaine bruis Blik"),
       CHAUDFONTAINE_SPARKLING_BOTTLE(3.0, "Chaudfontaine bruis Fles"),
       
       TROPICO_CAN(1.5, "Tropico"),
       MINUTEMAID_MULTI_CAN(1.5, "Minute Maid Multi"),
       SHAKURA_CAN(2.0, "Shakura");
        
        private double price;
        private String nameDisplay;
        DrinkType(double price, String nameDisplay){
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