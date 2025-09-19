package ec.com.pattern.behavioral.iteractor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardList implements List {

    private Card[] cards;

    @Override
    public Iterator iterator() {
        return new CardIterator(cards);
    }

    
    
}
