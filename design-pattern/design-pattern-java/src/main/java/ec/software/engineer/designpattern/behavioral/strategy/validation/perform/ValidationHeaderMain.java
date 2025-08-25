package ec.software.engineer.designpattern.behavioral.strategy.validation.perform;

import ec.software.engineer.designpattern.behavioral.strategy.validation.dto.FileProcessingResult;
import ec.software.engineer.designpattern.behavioral.strategy.validation.impl.FileHeaders;
import ec.software.engineer.designpattern.behavioral.strategy.validation.util.DownloadFile;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ValidationHeaderMain {

  public static void main(String[] args) throws IOException {
    String fileBody = DownloadFile.getFileBody(
        "/Users/ezamora/Downloads/fileExample/file-example-07-03-2024.txt");
    FileHeaders headers = new FileHeaders();
    FileProcessingResult fileProcessingResult = new FileProcessingResult();
    fileProcessingResult.setOriginalFile(fileBody);
    var result = headers.validate(fileProcessingResult);
    System.out.println("==>" + result.getPaymentFileList().toString());
  }


}
