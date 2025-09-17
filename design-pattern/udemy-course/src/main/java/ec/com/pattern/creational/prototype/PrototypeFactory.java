package ec.com.pattern.creational.prototype;

import java.util.HashMap;
import java.util.Map;

public class PrototypeFactory {

    private static  Map<String, PrototypeCard> protoTypes = new HashMap<>();

    public static  class CardType {
        public static final String VISA = "VISA";
        public static final String AMEX = "AMEX";
    }


public static PrototypeCard getInstance(final String type) throws CloneNotSupportedException {
    return protoTypes.get(type).clone();
}
    

public static void loadCards() {

    Visa visa = new Visa();
    visa.setName("VISA");
    protoTypes.put(CardType.VISA, visa);

    Amex amex = new Amex();
    amex.setName("AMEX");
    protoTypes.put(CardType.AMEX, amex);

}

}
