package org.lzyyauto.controller;

import lombok.RequiredArgsConstructor;
import org.lzyyauto.dto.BookDto;
import org.lzyyauto.dto.ResponseVO;
import org.lzyyauto.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author zingliu
 * @date 2025/5/20
 */

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseVO<BookDto> createBook(@Valid @RequestBody BookDto createBookVo) {
        return new ResponseVO<BookDto>().ok(bookService.createBook(createBookVo));
    }

    @GetMapping
    public ResponseVO<List<BookDto>> getAllBooks() {
        return new ResponseVO<List<BookDto>>().ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseVO<BookDto> getBookById(@PathVariable Long id) {
        return new ResponseVO<BookDto>().ok(bookService.getBookById(id));
    }

    @PutMapping("/{id}")
    public ResponseVO<BookDto> updateBook(@PathVariable Long id, @Valid @RequestBody BookDto updateBookVo) {
        return new ResponseVO<BookDto>().ok(bookService.updateBook(id, updateBookVo));
    }

    @DeleteMapping("/{id}")
    public ResponseVO<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseVO<Void>().ok(null);
    }
}
