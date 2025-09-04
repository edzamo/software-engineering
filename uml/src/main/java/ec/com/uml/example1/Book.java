package ec.com.uml;

abstract class Book {

    private final String title;
    private final Author author;

    public Book(String title, Author author) {
        this.title = title;
        this.author = author;
    }

    public abstract void showInfo();

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }
}