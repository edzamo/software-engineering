package ec.software.engineer.designpattern.behavioral.strategy.validation;

import ec.software.engineer.designpattern.behavioral.strategy.validation.dto.FileProcessingResult;
import ec.software.engineer.designpattern.behavioral.strategy.validation.dto.PaymentFile;
import ec.software.engineer.designpattern.behavioral.strategy.validation.dto.ValidationResult;
import ec.software.engineer.designpattern.behavioral.strategy.validation.dto.enums.MessageTypeEnum;
import java.util.ArrayList;
import java.util.List;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;


@UtilityClass
@Slf4j
public class SendValidation {

  public static FileProcessingResult createInfMessage(FileProcessingResult last,
      Boolean approve, String code) {
    List<ValidationResult> addLogs = new ArrayList<>();
    if (last.getResult() != null) {
      last.getLogs().add(last.getResult());
      addLogs.addAll(last.getLogs());
    }
    log.info("`==========> error:` file<{}>-status.{} ", last.getFileName(), last.getResult());
    //send Redis

    return FileProcessingResult.builder()
        .fileName(last.getFileName())
        .originalFile(last.getOriginalFile())
        .logs(addLogs)
        .paymentFileList(last.getPaymentFileList())
        .result(ValidationResult.builder()
            .messageType(MessageTypeEnum.INF)
            .approve(approve)
            .code(code)
            .build())
        .build();
  }

  public static FileProcessingResult createInfMessage(FileProcessingResult last,
      List<PaymentFile> paymentFiles, Boolean approve, String code) {
    List<ValidationResult> addLogs = new ArrayList<>();
    if (last.getResult() != null) {
      addLogs.add(last.getResult());
    }
    log.info("`==========> error:` file<{}>-status.{} ", last.getFileName(), last.getResult());
    //send Redis

    return FileProcessingResult.builder()
        .fileName(last.getFileName())
        .originalFile(last.getOriginalFile())
        .logs(addLogs)
        .paymentFileList(paymentFiles)
        .result(ValidationResult.builder()
            .messageType(MessageTypeEnum.INF)
            .approve(approve)
            .code(code)
            .build())
        .build();
  }

  public static void createKillMessage(FileProcessingResult fileProcessingLast,
      Boolean approve, String path, String code, String detail) {
    //enviar a REdis
    fileProcessingLast.setResult(ValidationResult.builder()
        .messageType(MessageTypeEnum.STOP)
        .approve(approve)
        .path(path)
        .code(code)
        .detail(detail)
        .build());
  }

}
