package ec.software.engineer.designpattern.behavioral.strategy.validation.dto.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageTypeEnum {
	INF("INFORMATION"), ERR("ERROR"), ADV("WARNING"), STOP("STOP PROCESS");
	private String name;

}
