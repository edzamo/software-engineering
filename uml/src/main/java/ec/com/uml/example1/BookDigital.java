package ec.com.uml;


public class BookDigital extends Book {
 
    private String format;

    public BookDigital(String title, Author author, String format) {
        super(title, author);
        this.format = format;
    }

    @Override
    public void showInfo() {
         System.out.println("📘 Libro Digital: " + getTitle() + 
                           " - Autor: " + getAuthor().getName() +
                           " - Formato: " + format);
    }
    
}
