package ec.software.engineer.designpattern.creational.singleton.challenge;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class Product {

  private Integer id;
  private String name;
  private Integer cost;
}
