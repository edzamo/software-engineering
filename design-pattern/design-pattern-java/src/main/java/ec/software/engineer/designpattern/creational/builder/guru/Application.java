package ec.software.engineer.designpattern.creational.builder.guru;

public class Application {


  public static void main(String[] args) {
    DirectorDealership dealership = new DirectorDealership();
    // The client wants a simple and small car
    CarBuilder builder = new CarBuilder();
    dealership.createSmallCar(builder);
    Car car = builder.getCar();
    System.out.println("My :" + car.toString() + " car");
  }
}
