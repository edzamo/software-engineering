package ec.software.engineer.designpattern.behavioral.strategy.validation.dto;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"id", "name", "age", "favouriteFruit", "amount"})
public class ComplexUserDto {
  enum Fruit {
    APPLE, BANANA, ORANGE
  }

  private Long id;
  private String name;
  private Integer age;
  private Fruit favouriteFruit;
  private BigDecimal money;

}
