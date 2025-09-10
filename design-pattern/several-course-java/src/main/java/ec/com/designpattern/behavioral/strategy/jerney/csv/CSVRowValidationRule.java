package ec.com.designpattern.behavioral.strategy.jerney.csv;

import java.util.Map;

public interface CSVRowValidationRule {
  public boolean validateRow(Map<Integer, String> row);
}
