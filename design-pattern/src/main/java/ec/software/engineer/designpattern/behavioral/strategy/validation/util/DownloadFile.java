package ec.software.engineer.designpattern.behavioral.strategy.validation.util;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import ec.software.engineer.designpattern.behavioral.strategy.validation.dto.ValidationStandardFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class DownloadFile {

  public static String getFileBody(String path) throws IOException {
   // File csvFile = new File("/Users/ezamora/Downloads/cvsExampleTabFull.txt");
    File csvFile = new File(path);
    byte[] bytes = Files.readAllBytes(csvFile.toPath());
    String body = new String(bytes, "UTF-8");
    return body;
  }




  public static List<ValidationStandardFile> getFileTransform(String fileBody) throws IOException {

    CsvMapper csvMapper = new CsvMapper();

    CsvSchema csvSchema = csvMapper
        .typedSchemaFor(ValidationStandardFile.class)
        .withHeader()
        .withColumnSeparator('\t')
        .withColumnReordering(true);


    MappingIterator<ValidationStandardFile> standardFile = csvMapper
       .readerWithTypedSchemaFor(ValidationStandardFile.class)
        .with(csvSchema)
        .readValues(fileBody);
    return standardFile.readAll();
  }

}
