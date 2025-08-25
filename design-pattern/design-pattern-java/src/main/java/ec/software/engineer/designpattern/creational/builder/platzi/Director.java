package ec.software.engineer.designpattern.creational.builder.platzi;


import ec.software.engineer.designpattern.creational.builder.platzi.enums.AvailableColors;
import ec.software.engineer.designpattern.creational.builder.platzi.enums.CarCatalogs;
import ec.software.engineer.designpattern.creational.builder.platzi.enums.EditionsTypes;

public class Director {
  public void createRhino3(CarProductionBuilder builder) {
    builder.setAirBags(3);
    builder.setColor(AvailableColors.BLUE);
    builder.setEdition(EditionsTypes.SIGNATURE);
    builder.setModel(CarCatalogs.RHINO);

  }

  public void createRhinoNormal(CarProductionBuilder builder) {
    builder.setAirBags(3);
    builder.setColor(AvailableColors.RED);
    builder.setEdition(EditionsTypes.DEFAULT);
    builder.setModel(CarCatalogs.MASTODON);

  }


}
