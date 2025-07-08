package ec.software.engineer.designpattern.creational.factory.challenge;


public class RestHttpAdapter implements HttpAdapter {


  private static final  String TYPE=" REST";




  @Override
  public void get() {
    System.out.println(this.type() + " Get method ");
  }

  @Override
  public void post() {
    System.out.println(this.type() + " Post method ");
  }

  @Override
  public void put() {
    System.out.println(this.type() + " Put method ");
  }

  @Override
  public void delete() {
    System.out.println(this.type() + " Delete method ");
  }

  @Override
  public String type() {
    return TYPE;
  }


}
