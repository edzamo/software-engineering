package ec.com.pattern.creational.abstractfactory.payment;

public interface AbstracFactory<T> {
    T create(String type);
}
