package cat.institutmarianao.domain.repository;

import java.util.List;

import cat.institutmarianao.domain.Book;

public interface BookRepository {
    List<Book> getAll();

    void add(Book book);

    boolean update(Book book);

    boolean delete(String isbn);

    Book get(String isbn);

    List<Book> findByTitle(String title);
}
