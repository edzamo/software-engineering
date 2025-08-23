package ec.software.engineer.designpattern.creational.factory;

public class RhinoCar implements BaseCar {

  @Override
  public void showCost() {
    System.out.println("Rhino car cost: 1000.00$");
  }
}
