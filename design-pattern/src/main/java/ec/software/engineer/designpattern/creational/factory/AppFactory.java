package ec.software.engineer.designpattern.creational.factory;

public class AppFactory {

  public static void main(String[] args) {
    createFactory(createFactories("mastodon"));
    createFactory(createFactories("xxx"));
  }

  private static void createFactory(CarFactory factory) {
    BaseCar baseCar = factory.makeCar();
    baseCar.showCost();

  }


  private static CarFactory createFactories(String type) {
    if (type.equals("mastodon")) {
      return new MastodonFactory();
    } else {
      return new RhinoFactory();
    }
  }
}
