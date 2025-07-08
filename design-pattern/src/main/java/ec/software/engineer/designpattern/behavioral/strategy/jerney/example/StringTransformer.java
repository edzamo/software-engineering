package ec.software.engineer.designpattern.behavioral.strategy.jerney.example;

public class StringTransformer {

  private String str;
  private TransformStrategy transformer;

  // Takes in a Concrete Strategy
  public StringTransformer(String str, TransformStrategy transformer) {
    this.str = str;
    this.transformer = transformer;
  }

  // Context in which the Concrete Strategy can execute
  public String execute() {
    // Calls the Concrete Strategy
    return this.transformer.transform(str);
  }
}
