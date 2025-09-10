package ec.com.designpattern.creational.abstractfactory.openwebinar;

public class MastodonSedanCar implements MastodonCar{

  @Override
  public void useGps() {
    System.out.println("[Sedan] MastodonSedanCar  ");
  }
}
