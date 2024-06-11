package net.javaguides.lms;
public class Book {
    private int id;
    private String title;
    private String author;
    private boolean isBorrowed;
    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isBorrowed = false;
    }
    // Getters and Setters
        @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isBorrowed=" + isBorrowed +
                '}';
    }
}
package net.javaguides.lms;
import java.util.ArrayList;
import java.util.List;
public class LibraryManager {
    private List<Book> books = new ArrayList<>();
    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book added successfully!");
    }
    public void updateBook(int id, String title, String author) {
        books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .ifPresent(book -> {
                    book.setTitle(title);
                    book.setAuthor(author);
                    System.out.println("Book updated successfully!");
                });
    }
    public void deleteBook(int id) {
        if (books.removeIf(book -> book.getId() == id)) {
            System.out.println("Book deleted successfully!");
        } else {
            System.out.println("Book not found!");
        }
    }
    public void listBooks() {
        books.forEach(System.out::println);
    }
    public void searchBooks(String query) {
        List<Book> foundBooks = books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                        book.getAuthor().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());

        if (foundBooks.isEmpty()) {
            System.out.println("No matching books found.");
        } else {
            foundBooks.forEach(System.out::println);
        }
    }
}
package net.javaguides.lms;
public class Main {
    public static void main(String[] args) {
        LibraryManager libraryManager = new LibraryManager();
        // Example usage:
        Book book1 = new Book(1, "The Great Gatsby", "F. Scott Fitzgerald");
        Book book2 = new Book(2, "To Kill a Mockingbird", "Harper Lee");
        libraryManager.addBook(book1);
        libraryManager.addBook(book2);
        libraryManager.listBooks();
        // Update book
        libraryManager.updateBook(1, "The Great Gatsby (Revised)", "F. Scott Fitzgerald");
        // Search for books
        libraryManager.searchBooks("Mockingbird");
    }
}
