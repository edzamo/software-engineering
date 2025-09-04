package ec.com.uml;

import lombok.Data;

@Data
public class User {
    
    private String name;

    public void borrowBook(Book book) {
        System.out.println("User " + name + " is reading the book: " + book.getTitle() + " by " + book.getAuthor().getName());
    }
}