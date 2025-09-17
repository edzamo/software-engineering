package ec.com.pattern.creational.prototype;

import lombok.Data;

@Data
public class Amex implements PrototypeCard {

    private String name;

    @Override
    public void getCard() {
        System.out.println("Amex:: getCard");
    }

    @Override
    public PrototypeCard clone() throws CloneNotSupportedException {
        System.out.println("Amex:: clone");
        return (Amex) super.clone();
    }
    
}
