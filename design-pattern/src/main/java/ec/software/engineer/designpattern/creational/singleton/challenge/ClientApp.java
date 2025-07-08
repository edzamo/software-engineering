package ec.software.engineer.designpattern.creational.singleton.challenge;

public class ClientApp {

  public static void main(String[] args) {
    ShoppingCar shoppingCar1 = ShoppingCar.getInstance();
    ShoppingCar shoppingCar2 = ShoppingCar.getInstance();


    System.out.println("shoppingCar1:" + shoppingCar1.hashCode());
    System.out.println("shoppingCar2:" + shoppingCar2.hashCode());

    Product pencil = new Product(1, "pencil", 2);
    Product book = new Product(1, "book", 2);
    shoppingCar1.add(pencil);
    shoppingCar1.add(book);

    System.out.println("shoppingCar1.getProducts" + shoppingCar1.getProducts());


  }

}
