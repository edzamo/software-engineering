package ec.com.designpattern.creational.abstractfactory.challenge;

public class MemoryPhone implements Memory{

  @Override
  public void capacityGb(int number) {
    System.out.println("[MemoryPhone] capacityGb");
  }
}
