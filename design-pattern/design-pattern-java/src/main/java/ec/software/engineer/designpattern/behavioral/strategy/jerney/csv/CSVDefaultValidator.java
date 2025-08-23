package ec.software.engineer.designpattern.behavioral.strategy.jerney.csv;

import java.util.Map;

public class CSVDefaultValidator implements  CSVRowValidationRule{

  @Override
  public boolean validateRow(Map<Integer, String> row) {
    return row.size() == 5;
  }
}
