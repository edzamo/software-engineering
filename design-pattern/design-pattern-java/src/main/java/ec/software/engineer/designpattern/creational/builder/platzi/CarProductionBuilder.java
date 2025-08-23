package ec.software.engineer.designpattern.creational.builder.platzi;

import ec.software.engineer.designpattern.creational.builder.platzi.enums.AvailableColors;
import ec.software.engineer.designpattern.creational.builder.platzi.enums.CarCatalogs;
import ec.software.engineer.designpattern.creational.builder.platzi.enums.EditionsTypes;

public interface CarProductionBuilder {

  void setAirBags(int howMany);
  void setColor(AvailableColors color);
  void setEdition(EditionsTypes edition);
  void setModel(CarCatalogs catalog);
  void resetProductionLine ();

}
