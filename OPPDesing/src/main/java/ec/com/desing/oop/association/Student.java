package ec.com.desing.oop.association;

public class Student {
    private String name;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void learn() {
        System.out.println(name + " is learning.");
    }
}
