package ec.software.engineer.solid.principle.openclosed.city;

public class Principal {

  public static void main(String[] args) {
    System.out.println("Executing !!...");
    BuenosAiresProcess buenosAiresProcess =  new BuenosAiresProcess();
    LimaProcess limaProcess= new LimaProcess();
    buenosAiresProcess.execute();
    limaProcess.execute();
  }
}
