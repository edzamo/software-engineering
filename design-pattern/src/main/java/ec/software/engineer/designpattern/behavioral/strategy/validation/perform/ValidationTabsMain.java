package ec.software.engineer.designpattern.behavioral.strategy.validation.perform;

import ec.software.engineer.designpattern.behavioral.strategy.validation.SendValidation;
import ec.software.engineer.designpattern.behavioral.strategy.validation.dto.FileProcessingResult;
import ec.software.engineer.designpattern.behavioral.strategy.validation.exception.CodeFile;
import ec.software.engineer.designpattern.behavioral.strategy.validation.util.DownloadFile;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ValidationTabsMain {

  public static void main(String[] args) throws IOException {
    String fileBody = DownloadFile.getFileBody(
        "/Users/ezamora/Downloads/fileExample/Ejemplo1.txt");

    FileProcessingResult fileProcessingResult= new FileProcessingResult();
    fileProcessingResult.setOriginalFile(fileBody);

    FileProcessingResult messageTo = validateFileTabs(fileProcessingResult);
    System.out.println("==>" + messageTo.toString());
  }


  public static FileProcessingResult validateFileTabs(FileProcessingResult fileProcessingResult) {
    String file = fileProcessingResult.getOriginalFile();
    String[] lineas = file.split("\n");
    String logValidation = Arrays.stream(lineas)
        .filter(linea -> !validarLinea(linea))
        .map(linea -> String.valueOf((Arrays.asList(lineas).indexOf(linea) + 1)))
        .collect(Collectors.joining(","));
    return Optional.of(logValidation)
        .filter(String::isEmpty)
        .map(
            validation -> SendValidation.createInfMessage(fileProcessingResult, Boolean.TRUE,
                CodeFile.CODE_VALIDATION_TABS_OK))
        .orElseGet(() -> {
          log.info("|==>🧭 Send to Redis Fail process FileOnlyTabs: {} ", logValidation);
          SendValidation.createKillMessage(fileProcessingResult, Boolean.FALSE,
              logValidation, CodeFile.CODE_VALIDATION_TABS_ERROR,
              CodeFile.VALIDATION_TABS_ERROR_DETAIL);
          return null;
        });
  }

  private static boolean validarLinea(String linea) {
    String[] campos = linea.split("\t");
    return Arrays.stream(campos).noneMatch(String::isEmpty);
  }

}
