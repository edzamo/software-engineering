package ec.com.pattern.creational.factorymethod.logist;

import ec.com.pattern.creational.factorymethod.logist.enums.TypeTransport;

public class TransportFactory {

    public static Transport buildTransport(TypeTransport typeTransport) {
        switch (typeTransport) {
            case TRUCK:
                return new Truck();
            case SHIP:
                return new Ship();
            default:
                throw new IllegalArgumentException("No such transport");
        }
    }

}