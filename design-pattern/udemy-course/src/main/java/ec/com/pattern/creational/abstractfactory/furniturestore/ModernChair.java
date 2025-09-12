package ec.com.pattern.creational.abstractfactory.furniturestore;

public class ModernChair implements Chair {
    @Override
    public boolean hasLargeLegs() {
        return false;
    }

    @Override
    public String sitOn() {
        return "Sitting on a Modern Chair";
    }   
}
