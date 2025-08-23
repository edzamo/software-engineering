package com.solid.principle.lsp;

import com.solid.principle.lsp.example1.good.CombustionCar;
import com.solid.principle.lsp.example1.good.ElectricCar;
import com.solid.principle.lsp.example1.good.Vehicle;

public class LspMain {

    public static void main() {

        System.out.println("Liskov Substitution Principle");
        String carType = "electric";

        // este codigo seria el ideal a ejecutar
        Vehicle combustion = new CombustionCar();
        Vehicle electric = new ElectricCar();
        combustion.accelerate();
        electric.accelerate(); // NO ESTARÍA COMPROBANDO LA BATERÍA Y FALLARÍA

    }
    
}
