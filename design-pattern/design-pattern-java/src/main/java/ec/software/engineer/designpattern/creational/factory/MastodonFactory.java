package ec.software.engineer.designpattern.creational.factory;

public class MastodonFactory implements CarFactory{

  @Override
  public BaseCar makeCar() {
    return new MastodonCar();
  }
}
