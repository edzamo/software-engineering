package ec.com.designpattern.creational.abstractfactory.challenge;

public class MemoryTablet implements Memory{


  @Override
  public void capacityGb(int number) {
    System.out.println("[MemoryTablet] capacityGb");
  }
}
