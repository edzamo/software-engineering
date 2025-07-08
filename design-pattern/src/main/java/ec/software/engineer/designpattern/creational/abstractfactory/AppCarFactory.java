package ec.software.engineer.designpattern.creational.abstractfactory;

public class AppCarFactory {

  public static void main(String[] args) {
    appCarFactory(createFactory("sedan"));
    appCarFactory(createFactory("sedanxx"));
  }

  private static void appCarFactory(CarAbstractFactory factory) {
    MastodonCar mastodonCar = factory.createMastodon();
    RhinoCar rhinoCar = factory.createRhino();
    mastodonCar.useGps();
    rhinoCar.useGps();
  }

  private static CarAbstractFactory createFactory(String type) {
    if (type.equals("sedan")) {
      return new SedanCarFactory();
    } else {
      return new HatchbackFactory();
    }
  }

}
