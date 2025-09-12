package ec.com.pattern.creational.abstractfactory.furniturestore;

public class VictorianChair implements Chair {
    @Override
    public boolean hasLargeLegs() {
        return true;
    }

    @Override
    public String sitOn() {
        return "Sitting on a Victorian Chair";
    }
    
}
