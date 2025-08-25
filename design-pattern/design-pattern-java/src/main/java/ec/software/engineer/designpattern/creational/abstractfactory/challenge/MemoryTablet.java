package ec.software.engineer.designpattern.creational.abstractfactory.challenge;

public class MemoryTablet implements Memory{


  @Override
  public void capacityGb(int number) {
    System.out.println("[MemoryTablet] capacityGb");
  }
}
