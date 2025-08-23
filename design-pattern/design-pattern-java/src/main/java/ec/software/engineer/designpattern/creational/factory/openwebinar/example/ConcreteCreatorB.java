package ec.software.engineer.designpattern.creational.factory.openwebinar.example;

public class ConcreteCreatorB implements Creator{

	@Override
	public Product factoryMethod() {
		// TODO Auto-generated method stub
		return new ConcreteProdcutB();
	}

}
