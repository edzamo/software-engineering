package ec.com.pattern.behavioral.command.cook;

import java.util.ArrayList;
import java.util.List;

/**
 * Invoker: Takes the order and places it in a queue.
 */
public class WaiterInvoker {
    private final List<Order> orderQueue = new ArrayList<>();

    public void takeOrder(Order order) {
        System.out.println("Waiter: Taking order and adding it to the queue.");
        orderQueue.add(order);
    }

    public void placeOrders() {
        System.out.println("\nWaiter: Placing all orders in the kitchen.");
        for (Order order : orderQueue) {
            order.execute();
        }
        orderQueue.clear();
    }
}