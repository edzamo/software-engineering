package ec.com.designpattern.behavioral.strategy.jerney.example;

public class Exciter implements TransformStrategy {

  @Override
  public String transform(String data) {
    return data + "!!";
  }
}
