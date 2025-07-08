package ec.software.engineer.designpattern.creational.abstractfactory.challenge;

public class PhoneFactory implements DeviceAbstractFactory{

  @Override
  public Cpu createCpu() {
    return new CpuPhone();
  }

  @Override
  public Memory createMemory() {
    return new MemoryPhone();
  }

  @Override
  public Display createDisplay() {
    return new DisplayPhone();
  }
}
