package ec.software.engineer.designpattern.creational.abstractfactory.challenge;

public class AppDeviceAbstractFactory {

  public static void main(String[] args) {
    createFactory(createAbstractFactory("laptop"));
  }

  private static void createFactory(DeviceAbstractFactory factory) {
    Cpu cpu = factory.createCpu();
    Display display = factory.createDisplay();
    Memory memory = factory.createMemory();
    cpu.series("xxx-001");
    display.resolution("100x5660");
    memory.capacityGb(100);
  }

  private static DeviceAbstractFactory createAbstractFactory(String type) {
    if (type.equals("phone")) {
      return new PhoneFactory();
    } else if (type.equals("tablet")) {
      return new TabletFactory();
    } else {
      return new LaptopFactory();
    }
  }
}
