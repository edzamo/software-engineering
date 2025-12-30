package ec.com.desing.oop;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase principal de ejemplo
 */
@Getter
@Setter
public class Main {
    private String name;
    
    public Main(String name) {
        this.name = name;
    }
    
    public static void main(String[] args) {
        Main main = new Main("OPPDesing");
        System.out.println("Hello from " + main.getName());
    }
}

