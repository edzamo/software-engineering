package ec.software.engineer.designpattern.behavioral.visitor;

public interface Shape {
  void move(int x, int y);
  void draw();
  String accept(Visitor visitor);
}