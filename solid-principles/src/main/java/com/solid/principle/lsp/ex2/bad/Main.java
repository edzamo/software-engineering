package com.solid.principle.lsp.ex2.bad;

public class Main {

    public static void main(String[] args) {
        Bird duck = new Duck();
        duck.fly();

        Bird penguin = new Penguin();

        /*
        El penguin no puede volar pero se ve obligado a implementar esta acción la cual lanza una excepción
         */
        penguin.fly();
    }
}
