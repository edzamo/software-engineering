package ec.com.pattern.behavioral.iteractor;

public interface Iterator {
    boolean hasNext();

    Object next();

    Object currentItem();
}