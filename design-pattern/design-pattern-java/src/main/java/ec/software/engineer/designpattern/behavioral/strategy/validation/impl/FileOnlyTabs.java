package ec.software.engineer.designpattern.behavioral.strategy.validation.impl;

import ec.software.engineer.designpattern.behavioral.strategy.validation.SendValidation;
import ec.software.engineer.designpattern.behavioral.strategy.validation.ValidationFile;
import ec.software.engineer.designpattern.behavioral.strategy.validation.dto.FileProcessingResult;
import ec.software.engineer.designpattern.behavioral.strategy.validation.dto.Person;
import ec.software.engineer.designpattern.behavioral.strategy.validation.dto.ValidationResult;
import ec.software.engineer.designpattern.behavioral.strategy.validation.dto.enums.MessageTypeEnum;
import ec.software.engineer.designpattern.behavioral.strategy.validation.exception.CodeFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.Message;


@Slf4j
public class FileOnlyTabs implements ValidationFile<FileProcessingResult, FileProcessingResult> {

  private List<Message> messages;

  @Override
  public FileProcessingResult validate(FileProcessingResult fileProcessingResult) {
    return validateFileTabs(fileProcessingResult);

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
