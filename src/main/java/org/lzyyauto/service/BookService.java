package org.lzyyauto.service;

import org.lzyyauto.dto.BookDto;
import org.lzyyauto.entity.Book;

import java.util.List;

/**
 * @author zingliu
 * @date 2025/5/20
 */
public interface BookService {
    BookDto createBook(BookDto createBookVo);

    List<BookDto> getAllBooks();

    BookDto getBookById(Long id);

    BookDto updateBook(Long id, BookDto updateBookVo);

    void deleteBook(Long id);
}
