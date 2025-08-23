package ec.software.engineer.designpattern.creational.abstractfactory;

public class SedanCarFactory implements CarAbstractFactory{

  @Override
  public MastodonCar createMastodon() {
    return new MastodonSedanCar();
  }

  @Override
  public RhinoCar createRhino() {
    return new RhinoSedanCar();
  }
}
