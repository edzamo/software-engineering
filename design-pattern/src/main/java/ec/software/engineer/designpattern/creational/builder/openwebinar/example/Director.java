package ec.software.engineer.designpattern.creational.builder.openwebinar.example;

public class Director {
	
	public void contruct(Builder builder) {
		builder.buildPartA();
		builder.buildPartB();
	}

}
