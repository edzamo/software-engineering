package ec.software.engineer.designpattern.creational.abstractfactory.challenge;

public class DisplayLaptop implements Display{

  @Override
  public void resolution(String serie) {
    System.out.println("[DisplayLaptop] resolution");
  }
}
