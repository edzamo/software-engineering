package ec.com.designpattern.creational.factory;

public class RhinoFactory implements CarFactory{

  @Override
  public BaseCar makeCar() {
    return new RhinoCar();
  }
}
