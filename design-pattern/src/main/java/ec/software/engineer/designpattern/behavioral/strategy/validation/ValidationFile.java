package ec.software.engineer.designpattern.behavioral.strategy.validation;


import java.io.IOException;

public interface ValidationFile<T, R> {

  R validate(T objectToValidate) throws IOException;


}
