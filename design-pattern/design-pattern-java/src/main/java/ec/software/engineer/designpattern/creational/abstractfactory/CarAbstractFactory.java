package ec.software.engineer.designpattern.creational.abstractfactory;

public interface CarAbstractFactory {

  public MastodonCar createMastodon();
  public RhinoCar createRhino();
}
