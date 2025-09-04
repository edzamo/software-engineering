package ec.com.uml;

import java.util.ArrayList;
import java.util.List;

public class Library {
    
private List<Book> books ;


public void addBook(Book book) {
    books = new ArrayList<Book>();
    books.add(book);
}

public void showBooks() {
    for (Book book : books) {
        book.showInfo();
    }
}
}
