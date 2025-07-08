package ec.software.engineer.designpattern.creational.builder.guru;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Car {

  private  CarType carType;
  private  Integer seats;
  private  int bigWheels;
  private  Engine  engine;
  private  Boolean leatherSeats;
  private  Integer batteries;
  private  Boolean convertible;
  private  Boolean electricCar;


}
