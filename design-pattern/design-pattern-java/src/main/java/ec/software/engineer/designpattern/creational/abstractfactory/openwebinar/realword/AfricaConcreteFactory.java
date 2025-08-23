package ec.software.engineer.designpattern.creational.abstractfactory.openwebinar.realword;

/**
 * 
 * ConcreteFactory (AfricaFactory, AmericaFactory) implementa las operaciones
 * para crear objetos de productos concretos.
 */

public class AfricaConcreteFactory implements ContinentFactory {

	@Override
	public HerbivoreAbstractProduct createHerbivore() {
		// TODO Auto-generated method stub
		return new Wildebeest();
	}

	@Override
	public CarnivoreAbstractProduct createCarnivore() {
		// TODO Auto-generated method stub
		return new Lion();
	}

}
