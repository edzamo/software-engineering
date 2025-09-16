package ec.com.pattern.creational.abstractfactory.payment;

public interface AbstractPaymentFactory<T> {
    T create(String type);
}
