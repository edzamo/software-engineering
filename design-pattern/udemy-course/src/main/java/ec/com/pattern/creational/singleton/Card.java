package ec.com.pattern.creational.singleton;

import lombok.Data;

@Data
public class Card {

    private static Card INSTANCE;
    private String name;

    private Card() {
    }

    public static Card getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new Card();
        }
        return INSTANCE;
    }

}
