package ec.com.pattern.creational.abstractfactory.furniturestore;

public interface AbstractFactory<T> {
    T create(String type);

}
