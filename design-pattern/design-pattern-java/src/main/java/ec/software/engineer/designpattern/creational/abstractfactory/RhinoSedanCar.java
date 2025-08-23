package ec.software.engineer.designpattern.creational.abstractfactory;

public class RhinoSedanCar implements RhinoCar{

  @Override
  public void useGps() {
    System.out.println("[Sedan] RhinoSedanCar ");
  }
}
