package ec.software.engineer.architecture.hexagonal.example.domainmodel;


import ec.software.engineer.architecture.hexagonal.example.domainmodel.external.console.Main;

public class Program {
  public static void main(String[] args) {
    Main console = new Main();
    console.start();
  }
}
