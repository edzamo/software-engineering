package ec.com.designpattern.creational.abstractfactory;

public class RhinoHatchbackCar implements RhinoCar{

  @Override
  public void useGps() {
    System.out.println("[Sedan] RhinoHatchbackCar ");
  }
}
