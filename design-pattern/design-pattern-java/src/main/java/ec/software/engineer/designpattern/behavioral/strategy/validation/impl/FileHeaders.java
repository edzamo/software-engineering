package ec.software.engineer.designpattern.behavioral.strategy.validation.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import ec.software.engineer.designpattern.behavioral.strategy.validation.SendValidation;

import ec.software.engineer.designpattern.behavioral.strategy.validation.ValidationFile;
import ec.software.engineer.designpattern.behavioral.strategy.validation.dto.FileProcessingResult;
import ec.software.engineer.designpattern.behavioral.strategy.validation.dto.PaymentFile;
import ec.software.engineer.designpattern.behavioral.strategy.validation.exception.CodeFile;
import java.io.IOException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@Setter
@Slf4j
public class FileHeaders implements ValidationFile<FileProcessingResult, FileProcessingResult> {



  @Override
  public FileProcessingResult validate(FileProcessingResult fileProcessingLast) throws IOException {
   return validationHeader(fileProcessingLast);
  }


  private static FileProcessingResult validationHeader(FileProcessingResult fileProcessingLast)
      throws IOException {
    try {
      CsvMapper csvMapper = new CsvMapper();
      CsvSchema csvSchema = csvMapper
          .typedSchemaFor(PaymentFile.class)
          .withHeader()
          .withColumnSeparator('\t')
          .withStrictHeaders(true);// orden estricto
      MappingIterator<PaymentFile> paymentFileMapper = csvMapper
          .readerFor(PaymentFile.class)
          .with(csvSchema)
          .readValues(fileProcessingLast.getOriginalFile());
      return SendValidation.createInfMessage(fileProcessingLast, paymentFileMapper.readAll(),
          Boolean.TRUE,
          CodeFile.CODE_VALIDATION_HEADER_OK);
    } catch (JsonProcessingException e) {
      log.info("|==>🧭 Send to Redis Fail process FileHeaders: {} ", e.getMessage());
      SendValidation.createKillMessage(fileProcessingLast, Boolean.FALSE,
          e.getMessage(), CodeFile.CODE_VALIDATION_HEADER_ERROR,
          CodeFile.VALIDATION_HEADER_ERROR_DETAIL);
      return null;
    }
  }
}



