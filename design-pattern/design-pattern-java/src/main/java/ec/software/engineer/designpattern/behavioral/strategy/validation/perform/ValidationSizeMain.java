package ec.software.engineer.designpattern.behavioral.strategy.validation.perform;

import ec.software.engineer.designpattern.behavioral.strategy.validation.dto.FileProcessingResult;
import ec.software.engineer.designpattern.behavioral.strategy.validation.impl.FileRowsExist;
import ec.software.engineer.designpattern.behavioral.strategy.validation.util.DownloadFile;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ValidationSizeMain {

  public static void main(String[] args) throws IOException {
    String fileBody = DownloadFile.getFileBody(
        "/Users/ezamora/Downloads/fileExample/file-example-07-03-2024_bad_size.txt");
    FileRowsExist rowsExist = new FileRowsExist();
    FileProcessingResult fileProcessingResult = new FileProcessingResult();
    fileProcessingResult.setOriginalFile(fileBody);
    var result = rowsExist.validate(fileProcessingResult);
    System.out.println("==>" + result.getPaymentFileList().toString());
  }


}
