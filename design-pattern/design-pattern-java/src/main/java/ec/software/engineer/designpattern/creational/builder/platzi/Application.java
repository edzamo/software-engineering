package ec.software.engineer.designpattern.creational.builder.platzi;

public class Application {

  public static void main(String[] args) {

    Director director= new Director();
    SedanProductionLine builder= new SedanProductionLine();
    director.createRhino3(builder);
    Car car= builder.build();
    System.out.println("car: " + car.toString());
  }
}
