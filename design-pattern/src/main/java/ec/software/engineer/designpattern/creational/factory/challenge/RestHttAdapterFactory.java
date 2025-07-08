package ec.software.engineer.designpattern.creational.factory.challenge;

public class RestHttAdapterFactory implements HttpAdapterFactory{


  @Override
  public HttpAdapter makeAdapter() {
    return new RestHttpAdapter();
  }
}
