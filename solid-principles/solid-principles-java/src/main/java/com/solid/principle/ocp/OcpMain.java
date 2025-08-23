package com.solid.principle.ocp;

import com.solid.principle.ocp.example1.good.Addition;
import com.solid.principle.ocp.example1.good.Subtraction;

public class OcpMain {

    public static void main() {

        System.out.println("Open/Closed Principle");
        Addition additionCalculatorOperation = new Addition(2.0, 3.0);
        additionCalculatorOperation.perform();
        Subtraction subtractionCalculatorOperation = new Subtraction(5.0, 1.0);
        subtractionCalculatorOperation.perform();

        System.out.println("Addition Operation: " + additionCalculatorOperation.getResult());
        System.out.println("Subtraction Operation: " + subtractionCalculatorOperation.getResult());
    }
}
