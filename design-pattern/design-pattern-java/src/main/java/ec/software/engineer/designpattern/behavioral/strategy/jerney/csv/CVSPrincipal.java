package ec.software.engineer.designpattern.behavioral.strategy.jerney.csv;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CVSPrincipal {

  public static void main(String[] args) {
    // Creates same CSV we referenced earlier
    List<Map<Integer, String>> csv = createCsv();

    // Validate CSV
    CSVRowValidationRule validationRule = new CSVType1Validator();
    CSVValidator validator = new CSVValidator(validationRule);

    boolean pass = validator.validate(csv);

    if (pass) {
      System.out.println("CSV Passed Validation");
    } else {
      System.out.println("CSV Failed Validation");
    }
  }

  private static List<Map<Integer, String>> createCsv() {
    Map<Integer, String> mapa1 = new HashMap<Integer, String>();
    mapa1.put(1, "12, 12/12/2011, 01/01/2012, 512.13");
    mapa1.put(2, "13, 06/14/2011, 01/01/2015, 12.67");
    mapa1.put(3, "14, 07/01/2011, 01/01/2012, 2.65");
    mapa1.put(4, "15, 02/17/2011, 01/01/2015, 90.22");

    return Arrays.asList(mapa1);

  }
}
