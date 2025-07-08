package ec.software.engineer.designpattern.behavioral.strategy.jerney.csv;

import java.util.List;
import java.util.Map;

public class CSVValidator {
  private CSVRowValidationRule rule;
  public CSVValidator(CSVRowValidationRule rule) {
    this.rule = rule;
  }

  public boolean validate(List<Map<Integer, String>> csv) {
    for (Map<Integer, String> row : csv) {
      if (! rule.validateRow(row)) {
        return false;
      }
    }

    return true;
  }


}
