package ec.com.pattern.creational.factorymethod.cevichemanabita;

public class Main {
    public static void main(String[] args) {
        // We create our restaurant
        Restaurant restaurant = new ManabitaRestaurant();

        // A customer orders a fish ceviche
        restaurant.orderCeviche(CevicheType.FISH);

        System.out.println("\n========================================\n");

        // Another customer orders a mixed ceviche
        restaurant.orderCeviche(CevicheType.MIXED);
    }
}