package ec.software.engineer.designpattern.behavioral.strategy.jerney.example;

import java.util.Map;

public interface CSVRowValidationRule {
  public boolean validateRow(Map<Integer, String> row);
}
