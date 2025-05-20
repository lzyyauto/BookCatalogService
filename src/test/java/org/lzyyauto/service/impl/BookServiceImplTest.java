package org.lzyyauto.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lzyyauto.dto.BookDto;
import org.lzyyauto.entity.Book;
import org.lzyyauto.factory.BookFactory;
import org.lzyyauto.repository.BookRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookServiceImpl;

    private BookDto bookDto;
    private Book book;

    @BeforeEach
    void setUp() {
        bookDto = new BookDto();
        bookDto.setTitle("测试书籍");
        bookDto.setAuthor("测试作者");
        bookDto.setIsbn("123-4567890123");
        bookDto.setPublicationYear(2023);

        book = Book.builder()
                .id(1L)
                .title(bookDto.getTitle())
                .author(bookDto.getAuthor())
                .isbn(bookDto.getIsbn())
                .publicationYear(bookDto.getPublicationYear())
                .description("未分类")
                .build();
    }

    @Test
    void testCreateBook_Success() {
        when(bookRepository.existsByIsbn(anyString())).thenReturn(false);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookDto createdBook = bookServiceImpl.createBook(bookDto);

        assertNotNull(createdBook);
        assertEquals(bookDto.getTitle(), createdBook.getTitle());
        verify(bookRepository, times(1)).existsByIsbn(bookDto.getIsbn());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void testCreateBook_IsbnExists() {
        when(bookRepository.existsByIsbn(anyString())).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            bookServiceImpl.createBook(bookDto);
        });

        assertEquals("ISBN已存在", exception.getMessage());
        verify(bookRepository, times(1)).existsByIsbn(bookDto.getIsbn());
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void testGetBookById_Success() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        BookDto foundBook = bookServiceImpl.getBookById(1L);

        assertNotNull(foundBook);
        assertEquals(book.getTitle(), foundBook.getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void testGetBookById_NotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            bookServiceImpl.getBookById(1L);
        });

        assertEquals("图书不存在: 1", exception.getMessage());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateBook_NotFound() {
        BookDto updatedDto = new BookDto();
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            bookServiceImpl.updateBook(1L, updatedDto);
        });

        assertEquals("图书不存在: 1", exception.getMessage());
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void testDeleteBook_Success() {
        when(bookRepository.existsById(1L)).thenReturn(true);
        doNothing().when(bookRepository).deleteById(1L);

        bookServiceImpl.deleteBook(1L);

        verify(bookRepository, times(1)).existsById(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteBook_NotFound() {
        when(bookRepository.existsById(1L)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            bookServiceImpl.deleteBook(1L);
        });

        assertEquals("图书不存在: 1", exception.getMessage());
        verify(bookRepository, times(1)).existsById(1L);
        verify(bookRepository, never()).deleteById(1L);
    }
}