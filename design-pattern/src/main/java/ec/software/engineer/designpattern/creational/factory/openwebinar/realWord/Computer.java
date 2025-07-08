package ec.software.engineer.designpattern.creational.factory.openwebinar.realWord;

public abstract class Computer {

	public abstract String getRam();

	public abstract String getHdd();

	public abstract String getCpu();

	@Override
	public String toString() {
		return "Computer [getRam()=" + getRam() + ", getHdd()=" + getHdd() + ", getCpu()=" + getCpu() + "]";
	}

}
