package ec.software.engineer.designpattern.creational.abstractfactory;

public class MastodonSedanCar implements MastodonCar{

  @Override
  public void useGps() {
    System.out.println("[Sedan] MastodonSedanCar  ");
  }
}
