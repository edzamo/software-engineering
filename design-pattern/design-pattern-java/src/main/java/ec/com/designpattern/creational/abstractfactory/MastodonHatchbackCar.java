package ec.com.designpattern.creational.abstractfactory;

public class MastodonHatchbackCar implements MastodonCar{

  @Override
  public void useGps() {
    System.out.println("[Sedan] MastodonHatchbackCar ");
  }
}
