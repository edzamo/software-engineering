package ec.software.engineer.designpattern.creational.factory.challenge2;


public class RestHttAdapter extends HttpAdapter{


  RestHttAdapter() {
    super("REST");
  }

  @Override
  void get() {
    System.out.println(this.getType().concat("Get Method"));
  }

  @Override
  void post() {
    System.out.println(this.getType().concat("Post Method"));
  }

  @Override
  void put() {
    System.out.println(this.getType().concat("Put Method"));
  }

  @Override
  void delete() {
    System.out.println(this.getType().concat("Delete Method"));
  }
}
