package ec.software.engineer.designpattern.creational.factory.challenge2;




public class RestHttpAdapterFactory implements HttpAdapterFactory {

  @Override
  public HttpAdapter makeAdapter() {
    return new RestHttAdapter();
  }
}
