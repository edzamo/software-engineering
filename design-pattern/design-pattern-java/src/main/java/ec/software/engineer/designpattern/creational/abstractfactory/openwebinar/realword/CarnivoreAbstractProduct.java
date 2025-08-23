package ec.software.engineer.designpattern.creational.abstractfactory.openwebinar.realword;

/**
 * 
 * AbstractProduct (Herbivore, Carnivore) declara una interfaz para un tipo de
 * objeto de producto
 */

public interface CarnivoreAbstractProduct {

	public abstract void eat(HerbivoreAbstractProduct herbivore);
}
