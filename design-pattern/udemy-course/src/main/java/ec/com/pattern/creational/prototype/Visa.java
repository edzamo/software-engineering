package ec.com.pattern.creational.prototype;

import lombok.Data;

@Data
public class Visa implements PrototypeCard {

private String name;

    @Override
    public void getCard() {
        System.out.println("Visa:: getCard");
    }

    @Override
    public PrototypeCard clone() throws CloneNotSupportedException {
        System.out.println("Visa:: clone");
        return (Visa) super.clone();
    }
    
}
