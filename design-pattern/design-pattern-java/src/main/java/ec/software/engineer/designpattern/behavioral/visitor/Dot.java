package ec.software.engineer.designpattern.behavioral.visitor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Dot implements Shape {

  private int id;
  private int x;
  private int y;





  @Override
  public void move(int x, int y) {
    // move shape
  }

  @Override
  public void draw() {
    // draw shape
  }

  @Override
  public String accept(Visitor visitor) {
    return visitor.visitDot(this);
  }



}
