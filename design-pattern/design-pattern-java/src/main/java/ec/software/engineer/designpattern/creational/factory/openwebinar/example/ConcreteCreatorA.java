package ec.software.engineer.designpattern.creational.factory.openwebinar.example;

public class ConcreteCreatorA implements Creator{

	@Override
	public Product factoryMethod() {
		// TODO Auto-generated method stub
		return new ConcreteProdcutA();
	}

}
