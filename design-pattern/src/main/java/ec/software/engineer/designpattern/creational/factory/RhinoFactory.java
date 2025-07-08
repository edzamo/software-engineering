package ec.software.engineer.designpattern.creational.factory;

public class RhinoFactory implements CarFactory{

  @Override
  public BaseCar makeCar() {
    return new RhinoCar();
  }
}
