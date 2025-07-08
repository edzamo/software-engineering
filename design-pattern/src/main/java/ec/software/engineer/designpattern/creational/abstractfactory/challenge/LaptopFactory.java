package ec.software.engineer.designpattern.creational.abstractfactory.challenge;

public class LaptopFactory implements DeviceAbstractFactory{

  @Override
  public Cpu createCpu() {
    return new CpuLaptop();
  }

  @Override
  public Memory createMemory() {
    return new MemoryLaptop();
  }

  @Override
  public Display createDisplay() {
    return new DisplayLaptop();
  }
}
