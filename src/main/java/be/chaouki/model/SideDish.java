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
public class SideDish extends Item {

    private SideDishType type;

    public SideDish(SideDishType type) {
        this.type = type;
        this.setUnitPrice(type.getPrice());
    }

    public SideDishType getType() {
        return type;
    }

    public void setType(SideDishType type) {
        this.type = type;
    }

    public enum SideDishType {

        CHICKENWINGS(3.8, "Chickenwings"),
        CHICKENNUGGETS(3.8, "Chickennuggets"),
        LOOKBROOD_NATURE(2.2, "Lookbrood natuur"),
        LOOKBROOD_CHEESE(2.6, "Lookbrood kaas"),
        LOOKBROOD_HAM_CHEESE(3.0, "Lookbrood ham & kaas"),
        LOEMPIA_CHICKEN(3.0, "Loempia kip"),
        LOEMPIA_SCAMPI(3.2, "Loempia scampi"),
        LOEMPIA_VEGETARIAN(2.8, "Loempia vegetarisch"),
        SURIMICROQUETTE(3.8, "Surimikroket"),
        FRIES(2.9, "Potatoes");

        private double price;
        private String nameDisplay;

        SideDishType(double price, String nameDisplay) {
            this.price = price;
            this.nameDisplay = nameDisplay;
        }

        public double getPrice() {
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
