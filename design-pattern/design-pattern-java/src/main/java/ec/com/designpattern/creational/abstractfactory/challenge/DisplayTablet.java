package ec.com.designpattern.creational.abstractfactory.challenge;

public class DisplayTablet implements Display{


  @Override
  public void resolution(String serie) {
    System.out.println("[DisplayTablet] resolution");
  }
}
