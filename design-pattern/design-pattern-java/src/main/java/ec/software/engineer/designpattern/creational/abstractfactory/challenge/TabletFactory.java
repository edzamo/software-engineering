package ec.software.engineer.designpattern.creational.abstractfactory.challenge;

public class TabletFactory implements DeviceAbstractFactory{

  @Override
  public Cpu createCpu() {
    return new CpuTablet();
  }

  @Override
  public Memory createMemory() {
    return new MemoryTablet();
  }

  @Override
  public Display createDisplay() {
    return new DisplayTablet();
  }
}
