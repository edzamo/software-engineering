package ec.software.engineer.designpattern.behavioral.strategy.validation.dto;


import ec.software.engineer.designpattern.behavioral.strategy.validation.dto.enums.MessageTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Generated
public class ValidationResult {

  MessageTypeEnum messageType;
  String path;
  String code;
  Boolean approve;
  String detail;

}