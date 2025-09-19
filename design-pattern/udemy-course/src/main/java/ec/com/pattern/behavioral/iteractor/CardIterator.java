package ec.com.pattern.behavioral.iteractor;

public class CardIterator implements Iterator {

    private Card[] cards;
    private int position;

    public CardIterator(Card[] cards) {
        this.cards = cards;
        this.position = 0;
    }

    @Override
    public boolean hasNext() {
        return position < cards.length && cards[position] != null;
    }

    @Override
    public Object next() {
        return cards[position++];
    }

    @Override
    public Object currentItem() {
        return cards[position];
    }
    
}
