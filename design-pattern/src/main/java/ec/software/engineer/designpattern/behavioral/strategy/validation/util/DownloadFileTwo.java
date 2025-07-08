package ec.software.engineer.designpattern.behavioral.strategy.validation.util;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import ec.software.engineer.designpattern.behavioral.strategy.validation.dto.Data;
import ec.software.engineer.solid.principle.amigoscode.Main;
import java.io.IOException;
import java.util.List;

public class DownloadFileTwo {

  private static final String data = "0\tFirst name\t10\t1.23\n"
      + "1\tSecond name\t23\t\n"
      + "0\tThird name\t30\t1.87";

  public static List<Data> getFile() throws IOException {
    CsvMapper mapper = new CsvMapper();
    CsvSchema schema = mapper.schemaFor(Data.class)
        .withoutHeader()
        .withColumnSeparator('\t')
        .withLineSeparator("\n");
    MappingIterator<Data> datas = mapper
        .readerWithTypedSchemaFor(Data.class)
        .with(schema)
        .readValues(data);
    return datas.readAll();
  }

  public static void main(String[] args) throws IOException {
    List<Data> dtos = DownloadFileTwo.getFile();
    dtos
        .stream()
        .forEach(System.out::println);
  }
}
