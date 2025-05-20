package org.lzyyauto.service.impl;

import org.lzyyauto.dto.BookDto;
import org.lzyyauto.entity.Book;
import org.lzyyauto.factory.BookFactory;
import org.lzyyauto.repository.BookRepository;
import org.lzyyauto.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author zingliu
 * @date 2025/5/20
 */
@Service
public class BookServiceImpl implements BookService {

    //TODO 单例模式
    @Autowired
    private BookRepository bookRepository;


    @Override
    public BookDto createBook(BookDto createBookVo) {
        if (bookRepository.existsByIsbn(createBookVo.getIsbn())) {
            throw new IllegalArgumentException("ISBN已存在");
        }

        Book book = BookFactory.createBook(new Random().nextInt(4), createBookVo.getTitle(), createBookVo.getAuthor(), createBookVo.getIsbn(), createBookVo.getPublicationYear());

        return convertToDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto getBookById(Long id) {
        return bookRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new IllegalArgumentException("图书不存在: " + id));
    }

    @Override
    public BookDto updateBook(Long id, BookDto updateBookVo) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("图书不存在: " + id));

        if (!book.getIsbn().equals(updateBookVo.getIsbn())
                && bookRepository.existsByIsbn(updateBookVo.getIsbn())) {
            throw new IllegalArgumentException("ISBN已存在");
        }

        book.setTitle(updateBookVo.getTitle());
        book.setAuthor(updateBookVo.getAuthor());
        book.setIsbn(updateBookVo.getIsbn());
        book.setPublicationYear(updateBookVo.getPublicationYear());

        return convertToDto(bookRepository.save(book));
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new IllegalArgumentException("图书不存在: " + id);
        }
        bookRepository.deleteById(id);

    }


    private BookDto convertToDto(Book book) {
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setIsbn(book.getIsbn());
        dto.setPublicationYear(book.getPublicationYear());
        dto.setDescription(book.getDescription());
        return dto;
    }
}
