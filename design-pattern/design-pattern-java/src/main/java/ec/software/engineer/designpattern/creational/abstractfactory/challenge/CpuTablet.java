package ec.software.engineer.designpattern.creational.abstractfactory.challenge;

public class CpuTablet implements Cpu{

  @Override
  public void series(String serie) {
    System.out.println("[CpuTablet] series");
  }
}
