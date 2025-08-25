package ec.software.engineer.designpattern.creational.builder.platzi;


import ec.software.engineer.designpattern.creational.builder.platzi.enums.AvailableColors;
import ec.software.engineer.designpattern.creational.builder.platzi.enums.CarCatalogs;
import ec.software.engineer.designpattern.creational.builder.platzi.enums.EditionsTypes;
import lombok.Getter;
import lombok.Setter;

public class SedanProductionLine implements CarProductionBuilder {

  @Getter
  @Setter
  private Car result;

  public SedanProductionLine() {
    this.result = new Car();
  }

  @Override
  public void setAirBags(int howMany) {
    this.result.setAirBags(howMany);

  }

  @Override
  public void setColor(AvailableColors color) {
    this.result.setColor(color);

  }

  @Override
  public void setEdition(EditionsTypes edition) {
    this.result.setEdition(edition);

  }

  @Override
  public void setModel(CarCatalogs catalog) {
    this.result.setModel(catalog.toString());
  }

  @Override
  public void resetProductionLine() {
    //this.sedanCar =
    //    this.modelToCustomizeInLine.equals(CarCatalogs.MASTODON) ? CarCatalogs.MASTODON
    //      : CarCatalogs.RHINO;
  }

  public Car build() {
    return this.result;
  }


}
