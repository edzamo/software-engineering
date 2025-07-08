package ec.software.engineer.designpattern.creational.builder.guru;

import lombok.Getter;
import lombok.Setter;


public class CarBuilder implements Builder {

  @Getter
  @Setter
  private Car createCar;

  public CarBuilder() {
    this.createCar = new Car();
  }

  @Override
  public void setCarType(CarType type) {
    this.getCreateCar().setCarType(type);
  }

  @Override
  public void setSeats(int seats) {
    this.getCreateCar().setSeats(seats);
  }

  @Override
  public void setEngine(Engine engine) {
    this.getCreateCar().setEngine(engine);
  }

  @Override
  public void setWheels(int wheels) {
    this.getCreateCar().setBigWheels(wheels);
  }

  @Override
  public void setLeatherSeats(Boolean leatherSeats) {
    this.getCreateCar().setLeatherSeats(leatherSeats);
  }

  @Override
  public void setBatteries(int batteries) {
    this.getCreateCar().setBatteries(batteries);
  }

  @Override
  public void setConvertible(Boolean convertible) {
    this.getCreateCar().setConvertible(convertible);
  }

  @Override
  public void setElectricCar(Boolean electricCar) {
    this.getCreateCar().setElectricCar(electricCar);
  }

  public Car getCar() {
    return new Car(this.getCreateCar().getCarType(), this.getCreateCar().getSeats(),
        this.getCreateCar().getBigWheels(), this.getCreateCar().getEngine(),
        this.getCreateCar().getLeatherSeats(), this.getCreateCar().getBigWheels(),
        this.getCreateCar().getConvertible(), this.getCreateCar().getElectricCar());
  }
}
