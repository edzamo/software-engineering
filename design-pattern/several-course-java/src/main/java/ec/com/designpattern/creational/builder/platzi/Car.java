package ec.com.designpattern.creational.builder.platzi;

import ec.com.designpattern.creational.builder.platzi.enums.AvailableColors;
import ec.com.designpattern.creational.builder.platzi.enums.CarCatalogs;
import ec.com.designpattern.creational.builder.platzi.enums.EditionsTypes;
import lombok.Data;

@Data
public class Car {

  private EditionsTypes edition;
  private String model;
  private int airBags;
  private AvailableColors color;
  private CarCatalogs carCatalogs;

}
