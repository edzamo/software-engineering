package ec.software.engineer.designpattern.creational.factory.challenge;

public interface HttpAdapter {

  public void get();

  public void post();

  public void put();

  public void delete();

  public String type();
}
