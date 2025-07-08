package ec.software.engineer.designpattern.creational.factory.challenge;

public class AppFactory {

  public static void main(String[] args) {
    createFactory(new RestHttAdapterFactory());
  }

  private static void createFactory(HttpAdapterFactory factory) {
    HttpAdapter adapter = factory.makeAdapter();
    System.out.println("`Http Adapter is: "+ adapter.type());
    adapter.get();
    adapter.post();
    adapter.put();
    adapter.delete();

  }


}
