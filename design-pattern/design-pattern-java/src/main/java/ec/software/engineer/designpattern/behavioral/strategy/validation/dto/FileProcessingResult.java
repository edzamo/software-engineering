package ec.software.engineer.designpattern.behavioral.strategy.validation.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Generated
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileProcessingResult {

  String fileName;
  String originalFile;
  List<PaymentFile> paymentFileList;
  ValidationResult result;
  List<ValidationResult> logs;

}
