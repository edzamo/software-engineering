package ec.software.engineer.designpattern.creational.builder.openwebinar.example;

public interface Builder {

	abstract void buildPartA();

	abstract void buildPartB();

	abstract Product getResult();

}
