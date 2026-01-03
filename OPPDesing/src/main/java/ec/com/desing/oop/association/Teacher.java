package ec.com.desing.oop.association;

public class Teacher {
    private String name;

    public Teacher(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void teach() {
        System.out.println(name + " is teaching.");
    }
}
