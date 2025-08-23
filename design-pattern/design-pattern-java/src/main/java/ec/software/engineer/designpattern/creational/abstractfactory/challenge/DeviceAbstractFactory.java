package ec.software.engineer.designpattern.creational.abstractfactory.challenge;

public interface DeviceAbstractFactory {

  public Cpu createCpu();
  public Memory createMemory();
  public Display createDisplay();

}
