package com.solid.principle.lsp;

import com.solid.principle.lsp.example2.good.Duck;
import com.solid.principle.lsp.example2.good.Penguin;

public class LspMain {

    public static void main() {

        System.out.println("Liskov Substitution Principle");

        // Using builders for clean object creation
        Penguin penguin = Penguin.builder()
                .name("Pingu")
                .age(5)
                .color("Black and White")
                .swimmingSpeed(2.5)
                .fishEatenPerDay(30)
                .build();

        Duck duck = Duck.builder()
                .name("Donald")
                .age(3)
                .color("White")
                .build();

        System.out.println(duck);
        System.out.println(penguin);

        // LSP in action: we can substitute Duck for its interface Flying
        System.out.print(duck.getName() + ": ");
        duck.fly();
       
    }


}
