package ec.software.engineer.designpattern.creational.abstractfactory.challenge;

public class CpuLaptop implements Cpu{

  @Override
  public void series(String serie) {
    System.out.println("[CpuLaptop] series");
  }
}
