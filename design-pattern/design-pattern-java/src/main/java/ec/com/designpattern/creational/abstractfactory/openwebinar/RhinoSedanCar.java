package ec.com.designpattern.creational.abstractfactory.openwebinar;

public class RhinoSedanCar implements RhinoCar{

  @Override
  public void useGps() {
    System.out.println("[Sedan] RhinoSedanCar ");
  }
}
