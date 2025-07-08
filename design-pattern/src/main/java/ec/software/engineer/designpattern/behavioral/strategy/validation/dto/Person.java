package ec.software.engineer.designpattern.behavioral.strategy.validation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

  public String name;
  public String surname;
  public int shoesize;
  public String gender;
}
