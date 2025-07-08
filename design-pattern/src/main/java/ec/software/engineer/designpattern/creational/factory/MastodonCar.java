package ec.software.engineer.designpattern.creational.factory;

public class MastodonCar implements BaseCar{

  @Override
  public void showCost() {
    System.out.println("Mastodon car cost: 3000.00$");
  }
}
