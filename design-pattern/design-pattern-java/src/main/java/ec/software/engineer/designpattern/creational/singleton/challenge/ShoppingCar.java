package ec.software.engineer.designpattern.creational.singleton.challenge;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;


@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ShoppingCar {


  private static ShoppingCar instance = null;
  private List<Product> products= new ArrayList<>();




  static ShoppingCar getInstance() {
    if (ShoppingCar.instance == null) {
      ShoppingCar.instance = new ShoppingCar();
    }
    return ShoppingCar.instance;
  }

  public void add(Product product) {
    this.products.add(product);

  }

  public void delete(Product product) {
    this.products.remove(product);
  }

public List<Product> getProducts(){
    return this.products;
}

}
