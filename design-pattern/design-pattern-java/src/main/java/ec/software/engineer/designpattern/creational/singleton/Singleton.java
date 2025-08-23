package ec.software.engineer.designpattern.creational.singleton;

public class Singleton {

  static Singleton instance = null;


  private Singleton() {
  }

  static Singleton getInstance() {

    if (Singleton.instance == null) {
      Singleton.instance = new Singleton();

    }
    return Singleton.instance;
  }


  public static void main(String[] args) {

    Singleton singleton1 = Singleton.getInstance();
    Singleton singleton2 = Singleton.getInstance();
    Singleton singleton = new Singleton();
    System.out.println("version 01" + singleton1);
    System.out.println("version 02" + singleton2);
    System.out.println("ccc" + singleton);

  }

}


