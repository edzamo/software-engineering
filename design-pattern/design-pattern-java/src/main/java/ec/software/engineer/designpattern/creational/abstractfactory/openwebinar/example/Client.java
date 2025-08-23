package ec.software.engineer.designpattern.creational.abstractfactory.openwebinar.example;

public class Client {

	private AbstractProductA abstractProductA;
	private AbstractProductB abstractProductB;

	public Client(AbstractFactory abstractFactory) {

		abstractProductA = abstractFactory.createProductoA();
		abstractProductB = abstractFactory.createProductoB();
	}
	
	public void run( ) {
		
		abstractProductB.interact(abstractProductA);
		
	}

}
