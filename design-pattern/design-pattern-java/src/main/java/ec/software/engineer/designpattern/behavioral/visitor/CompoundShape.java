package ec.software.engineer.designpattern.behavioral.visitor;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;


public class CompoundShape implements Shape {

  @Getter
  public int id;
  public List<Shape> children = new ArrayList<Shape>();

  public CompoundShape(int id) {
    this.id = id;
  }

  public void add(Shape shape) {
    children.add(shape);
  }


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
    return visitor.visitCompoundGraphic(this);
  }
}
