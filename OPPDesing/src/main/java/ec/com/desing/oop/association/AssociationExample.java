package ec.com.desing.oop.association;

public class AssociationExample {
    public static void main(String[] args) {
        Teacher teacher = new Teacher("Mrs. Smith");
        Student student = new Student("John");

        System.out.println(teacher.getName() + " is associated with " + student.getName());
        teacher.teach();
        student.learn();
    }
}
