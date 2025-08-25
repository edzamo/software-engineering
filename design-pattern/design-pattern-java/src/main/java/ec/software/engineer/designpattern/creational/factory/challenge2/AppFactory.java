package ec.software.engineer.designpattern.creational.factory.challenge2;



public class AppFactory {

  public static void main(String[] args) {
    createFactory(new RestHttpAdapterFactory());
  }

  private static void createFactory(HttpAdapterFactory factory) {
    HttpAdapter adapter = factory.makeAdapter();
    System.out.println("`Http Adapter is: "+ adapter.getType());
    adapter.get();
    adapter.post();
    adapter.put();
    adapter.delete();

  }


}
