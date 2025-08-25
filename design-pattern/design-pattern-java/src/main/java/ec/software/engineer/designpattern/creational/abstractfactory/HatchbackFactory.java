package ec.software.engineer.designpattern.creational.abstractfactory;

public class HatchbackFactory implements CarAbstractFactory{

  @Override
  public MastodonCar createMastodon() {
    return new MastodonHatchbackCar();
  }

  @Override
  public RhinoCar createRhino() {
    return new RhinoHatchbackCar();
  }
}
