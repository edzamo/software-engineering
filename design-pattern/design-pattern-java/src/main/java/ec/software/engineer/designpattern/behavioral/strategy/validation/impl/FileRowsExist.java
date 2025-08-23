package ec.software.engineer.designpattern.behavioral.strategy.validation.impl;


import ec.software.engineer.designpattern.behavioral.strategy.validation.SendValidation;
import ec.software.engineer.designpattern.behavioral.strategy.validation.ValidationFile;
import ec.software.engineer.designpattern.behavioral.strategy.validation.dto.FileProcessingResult;
import ec.software.engineer.designpattern.behavioral.strategy.validation.exception.CodeFile;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Setter
@Slf4j
public class FileRowsExist implements ValidationFile<FileProcessingResult, FileProcessingResult> {


  @Override
  public FileProcessingResult validate(FileProcessingResult fileProcessingLast) {

      log.info("|==> ➡️   started: FileRowsExist ");
      return validationRowsExist(fileProcessingLast);

  }

  private static FileProcessingResult validationRowsExist(FileProcessingResult fileProcessingLast) {
    if (fileProcessingLast.getPaymentFileList().size() >= 2) {
      return SendValidation.createInfMessage(fileProcessingLast, Boolean.TRUE,
          CodeFile.CODE_VALIDATION_ROWS_EXIT_OK);
    } else {
      log.info("|==>🧭 Send to Redis Fail process FileRowsExist ");
      SendValidation.createKillMessage(fileProcessingLast, Boolean.FALSE,
          CodeFile.VALIDATION_ROWS_EXIT_ERROR_DETAIL, CodeFile.CODE_VALIDATION_ROWS_EXIT_ERROR,
          CodeFile.VALIDATION_ROWS_EXIT_ERROR_DETAIL);
      return null;
    }
  }

}
