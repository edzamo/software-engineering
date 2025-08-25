package ec.software.engineer.designpattern.behavioral.strategy.jerney.csv;

import java.text.SimpleDateFormat;
import java.util.Map;

public class CSVType1Validator implements CSVRowValidationRule {
  private SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

  @Override
  public boolean validateRow(Map<Integer, String> row) {
    //String id = row.get(0);

    String id = row.get(1);
    String startDateStr = row.get(2);
    String endDateStr = row.get(3);
    String amount = row.get(4);

    if (id == null || id.trim() == "") {
      return false;
    }
/*
    try {
      df.setLenient(false);

      Date startDate = df.parse(startDateStr);
      Date endDate = df.parse(endDateStr);

      if (endDate.before(startDate)) {
        return false;
      }
    } catch (ParseException e) {
      return false;
    }*/
/*
    if (Double.parseDouble(amount) < 0) {
      return false;
    }*/

    return true;
  }
}
