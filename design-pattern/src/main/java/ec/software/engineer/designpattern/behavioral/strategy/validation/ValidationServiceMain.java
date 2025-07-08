package ec.software.engineer.designpattern.behavioral.strategy.validation;

import ec.software.engineer.designpattern.behavioral.strategy.validation.dto.ValidationResult;
import ec.software.engineer.designpattern.behavioral.strategy.validation.exception.ValidationFileException;
import ec.software.engineer.designpattern.behavioral.strategy.validation.impl.FileOnlyTabs;
import ec.software.engineer.designpattern.behavioral.strategy.validation.impl.FileHeaders;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ValidationServiceMain {

  public static void main(String[] args) throws ValidationFileException, IOException {

   // DownloadFile.getFileBody("");
    FileOnlyTabs record = new FileOnlyTabs();
    FileHeaders headerRecord = new FileHeaders();
    List<ValidationResult> resultList = new ArrayList<>();
    ValidationResult validationResultTo;

    //validationResultTo = record.validate(person);
   // resultList.add(validationResultTo);

    System.out.println("Validation Done :" + resultList.toString());

   // List<ValidationStandardFile> lisFile=  DownloadFile.getFile();
    //lisFile.forEach(System.out::println);

   // List<Data> lisFileTwo=  DownloadFileTwo.getFile();
   // lisFileTwo.forEach(System.out::println);
  }
}
