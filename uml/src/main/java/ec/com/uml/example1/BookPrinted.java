package ec.com.uml;

public class BookPrinted extends Book {

    private final int page;


    public BookPrinted(String title, Author author, int page) {
        super(title, author);
        this.page = page;
    }

    @Override
    public void showInfo() {
        System.out.println("Title: " + getTitle());
        System.out.println("Author: " + getAuthor().getName());
        System.out.println("ISBN: " + page);
    
    }
    

}